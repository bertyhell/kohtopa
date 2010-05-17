using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using System.Data.OleDb;
using System.Collections.Generic;
using System.Globalization;

namespace KohtopaWeb
{
    public class DataConnector
    {
        //a class to add, update and retrieve data from te database.

        //login data for the database, data that is variable is stored in the web.config file.
        private static string usernameDB = ConfigurationManager.AppSettings["dbUsername"];
        private static string password = ConfigurationManager.AppSettings["dbPassword"];
        private static string databaseName = ConfigurationManager.AppSettings["dbName"];
        private static string connectionString = "Provider=OraOLEDB.Oracle;Data Source=localhost:1521/" + databaseName + ";User Id=" + usernameDB + ";Password=" + password + ";";        

        //data to define the different types of rentables in the database.
        public static string rentableTypes = ConfigurationManager.AppSettings["RentableTypes"];

        //sql statements to add, retrieve or update data from the database.
        private static string getRentablesSQL = "select r.rentableid, r.buildingid, r.ownerid, r.type, r.area,r.window_area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode,a.country ,coalesce(max(c.contract_end),sysdate) as free "
                                                    + "from rentables r "
                                                    + "join buildings b on b.buildingid = r.buildingid "
                                                    + "join addresses a on b.addressid = a.addressid "
                                                    + "join contracts c on c.rentableid = r.rentableid "
                                                    + "group by r.rentableid, r.buildingid, r.ownerid, r.type, r.area,r.window_area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode, a.country";        
        private static string getPersonIdSQL = "select personId from persons where username = ? and password = ?";
        private static string getPasswordSQL = "select password from persons where username = ?";
        private static string getPersonByUsernameSQL = "select * from persons where userName = ?";
        private static string getPersonByIdSQL = "select * from persons where personId = ?";
        private static string getAddressSQL = "select * from addresses where addressId = ?";
        private static string getRentableByIdSQL = "select r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price, r.floor,r.description,coalesce(max(c.contract_end),sysdate) as free "
                                                    + "from rentables r "
                                                    + "join contracts c on c.rentableid = r.rentableid "
                                                    + "where r.rentableid = ? "
                                                    + "group by r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price,r.description, r.floor";
        private static string getCurrentRentableIdSQL = "select rentableId as rentableId from contracts where renterid = ? and contract_start < ? and contract_end > ?";
        private static string getMessagesByIdSQL = "select * from messages where recipientId = ?";
        private static string getLongLatByRentableIdSQL = "select b.longitude, b.latitude from rentables r join buildings b on b.buildingid = r.buildingid where r.rentableid = ?";
        private static string getBuildingByIdSQL = "select * from buildings where buildingId = ?";

        private static string getBuildingPictureIDsByIdSQL = "select pictureID from pictures where type_floor = -3 and rentable_building_id = ?";
        private static string getRentablePictureIDsByIdSQL = "select pictureID from pictures where type_floor = -1 and rentable_building_id = ?";
        private static string getPictureByIdSQL = "select picture from pictures where pictureid = ?";

        private static string updateMessageSQL = "update messages set message_read = '1' where date_sent between ? and ? and subject = ? and recipientid = ?";
        
        private static string addMessageSQL = "insert into messages values(?,?,?,?,?,?)";

        private static string selectTasks = "select distinct description,start_time,c.rentableid " +
                                    "from tasks t " +
                                    "join contracts c on t.rentableid = c.rentableid and c.renterid = ?" +
                                    "order by start_time asc" ;
        private static string selectConsumptions =
            "select distinct gas,water,electricity, date_consumption from consumption c " +
            "join contracts co on co.rentableid = c.rentableid " +
            "where co.renterid = ? " +
            "order by date_consumption desc";
        private static string selectInvoices = "select invoiceid, invoicedate, paid, invoice_xml, send from invoices i "+
            "join contracts c on c.renterid = ? and c.contractid = i.contractid where send <> 0 order by paid asc";

        private static string insertConsumptions = "insert into consumption values(?,?,?,?,?,?)";

        public static string selectFloorPlan = "select XML from floors where buildingid = ? and floor = ?";

        // a sql statement who calculates the distance between 2 given points.
        public static string distanceFilterSQL = "where (ACOS(SIN(latitude*3.14159265/180) * SIN( ? *3.14159265/180) + COS(latitude * 3.14159265/180) * COS( ? * 3.14159265/180) * COS((longitude - ? )*3.14159265/180))/ 3.14159265 * 180) * 111.18957696 <= ?";      

        //a function to return an instance of a connection with the database.
        private static OleDbConnection getConnection(){
            return new OleDbConnection(connectionString);            
        }

        //a function to insert a consumption in the database.
        public static void insertConsumption(Consumption c)
        {
            //CONSUMPTIONID  RENTABLEID  GAS  WATER  ELECTRICITY  DATE_CONSUMPTION 
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = insertConsumptions;

            OleDbParameter p = new OleDbParameter("CONSUMPTIONID",c.ConsumptionId);
            command.Parameters.Add(p);

            p = new OleDbParameter("RENTABLEID", c.RentableId);
            command.Parameters.Add(p);


            p = new OleDbParameter("GAS", c.Gas);
            command.Parameters.Add(p);

            p = new OleDbParameter("WATER", c.Water);
            command.Parameters.Add(p);

            p = new OleDbParameter("ELECTRICITY", c.Electricity);
            command.Parameters.Add(p);

            p = new OleDbParameter("DATE_CONSUMPTION", c.Date);
            command.Parameters.Add(p);

            conn.Open();
            command.ExecuteNonQuery();
            conn.Close();
        }

        // a function to retrieve all the invoices from a renter from the database.
        public static DataTable getInvoices(int id)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = selectInvoices;

            OleDbParameter p = new OleDbParameter("renterid", id);
            command.Parameters.Add(p);

            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();

            da.MissingSchemaAction = MissingSchemaAction.AddWithKey;
            da.Fill(ds);

            return ds.Tables[0];
        }

        // a function to retrieve all the consumptions from a renter from the database.
        public static DataTable getConsumptions(int id)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = selectConsumptions;

            OleDbParameter p = new OleDbParameter("renterid", id);
            command.Parameters.Add(p);

            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();

            da.MissingSchemaAction = MissingSchemaAction.AddWithKey;
            da.Fill(ds);

            return ds.Tables[0];
        }

        //a function to retrieve all the messages from a person from the database.
        public static List<Message> getMessages(int id)
        {
            List<Message> messages = new List<Message>();

            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getMessagesByIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.OleDbType = OleDbType.Integer;
            p.Value = id;
            command.Parameters.Add(p);
            conn.Open();
            OleDbDataReader r = command.ExecuteReader();
            while (r.Read())
            {
                Message m = new Message();
                m.Subject = r.GetString(2);
                m.DateSent = r.GetDateTime(3);
                m.Read = r.GetString(4).Equals("1");
                m.Text = r.GetString(5);

                messages.Add(m);
            }
            conn.Close();

            messages.Sort(delegate(Message m1, Message m2) {
                if (m2.Read && !m1.Read)
                {
                    return -1;
                }
                else if(m1.Read)
                {
                    return 1;
                } 
                return m2.DateSent.CompareTo(m1.DateSent); 
            });
            return messages;

        }

        //a function to retreive an xml file from a floorplan from the database.
        public static byte[] getFloorPlan(int buildingID, int floor)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = selectFloorPlan;

            
            OleDbParameter p = new OleDbParameter("buildingID", buildingID);
            command.Parameters.Add(p);

            p = new OleDbParameter("floor", floor);
            command.Parameters.Add(p);

            conn.Open();
            OleDbDataReader reader = command.ExecuteReader();

            byte[] data = null;
            if (reader.Read())
            {
                data = (byte[])reader[0];
            }

            reader.Close();
            conn.Close();
            return data;

        }

        //a function to retrieve a picture from the database.
        public static byte[] getPicture(int ImageId){
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();            
            //command.CommandText = "select picture from pictures where pictureId = " + ImageId;
            command.CommandText = getPictureByIdSQL;

            OleDbParameter p = new OleDbParameter("imageId", ImageId);
            command.Parameters.Add(p);

            command.Connection = conn;
            conn.Open();    
            OleDbDataReader reader = command.ExecuteReader();
            byte[] data = null;
            while(reader.Read()){
                data = (byte[])reader[0];
            }            
            reader.Close();
            conn.Close();
            return data;
        }

        //a function to retreive all the tasks from a given person.
        public static DataTable getTasks(int userId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = selectTasks;

            OleDbParameter p = new OleDbParameter("renterid", userId);
            command.Parameters.Add(p);

            /*p = new OleDbParameter("start", start);
            command.Parameters.Add(p);

            p = new OleDbParameter("end", end);
            command.Parameters.Add(p);*/


            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            
            da.MissingSchemaAction = MissingSchemaAction.AddWithKey;
            da.Fill(ds);

            return ds.Tables[0];
        }

        //a function to retreive all the rentables from the database.
        public static DataTable getRentables(string order)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();            
            OleDbDataAdapter da = new OleDbDataAdapter(getRentablesSQL + " " + order, conn);
            DataSet ds = new DataSet();
            da.Fill(ds);            
            return ds.Tables[0];
        }

        //a function to retreive all the rentables in a given distance from an point.
        public static DataTable getRentables(double longitude, double latitude, double distance, string order)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getRentablesSQL.Insert(getRentablesSQL.IndexOf(" group by")," " + distanceFilterSQL) + " " + order;
            OleDbParameter p = new OleDbParameter();
            p.Value = latitude;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = latitude;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = longitude;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = distance;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return ds.Tables[0];
        }

        //a function to test if a username en password match in the database.
        public static bool isValidPerson(string username,string password)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getPasswordSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = username;
            command.Parameters.Add(p);            
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                return dt.Rows[0].ItemArray[0].ToString().Equals(password);
            }
            else
            {
                return false;
            }            
        }

        //a function to retrieve the personid from a person from its username and password.
        public static int getPersonId(string username, string password)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getPersonIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = username;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = password;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                return Int32.Parse(dt.Rows[0].ItemArray[0].ToString());
            }
            return 1;
        }

        //a function to fill an instance of the Person class when given the username.
        public static Person getPerson(string userName)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getPersonByUsernameSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = userName;
            command.Parameters.Add(p);            
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count > 0)
            {
                Person person = new Person();
                DataRow dr = dt.Rows[0];
                person.Address = getAddress(Int32.Parse("" + dr["addressId"]));
                person.Cellphone = "" + dr["CellPhone"];
                person.Email = "" + dr["Email"];
                person.FirstName = "" + dr["First_Name"];
                person.Name = "" + dr["Name"];
                person.Password = "" + dr["Password"];
                person.PersonId = Int32.Parse("" + dr["personId"]);
                person.RoleId = "" + dr["RoleId"];
                person.Telephone = "" + dr["Telephone"];
                person.Username = userName;
                person.Rentable = getCurrentRentable(person.PersonId);
                return person;
            }
            return null;
        }

        // a function to fill an instance of the Persons class when given the id.
        public static Person getPerson(int personId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getPersonByIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = personId;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                Person person = new Person();
                DataRow dr = dt.Rows[0];
                person.Address = getAddress(Int32.Parse("" + dr["addressId"]));
                person.Cellphone = "" + dr["CellPhone"];
                person.Email = "" + dr["Email"];
                person.FirstName = "" + dr["First_Name"];
                person.Name = "" + dr["Name"];
                person.Password = "" + dr["Password"];
                person.PersonId = Int32.Parse(("" + dr["personId"]));
                person.RoleId = "" + dr["RoleId"];
                person.Telephone = "" + dr["Telephone"];
                person.Username = "" + dr["userName"];
                person.Rentable = getCurrentRentable(personId);
                return person;
            }
            return null;
        }

        // a function to fill an instance of the Building class when given the id.
        public static Building getBuilding(int buildingId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getBuildingByIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = buildingId;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                DataRow dr = dt.Rows[0];
                Building b = new Building();
                b.Id = buildingId;
                try
                {
                    b.Latitude = Double.Parse("" + dr["latitude"]);
                }
                catch
                {
                    b.Latitude = 0;
                }
                try
                {
                    b.Longitude = Double.Parse("" + dr["longitude"]);
                }
                catch
                {
                    b.Longitude = 0;
                }
                b.Address = getAddress(Int32.Parse("" + dr["addressId"]));
                return b;
            }
            return null;
        }

        //a function to fill an instance of the Address class when given the id.
        public static Address getAddress(int addressId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getAddressSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = addressId;
            command.Parameters.Add(p);            
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();            
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                DataRow dr = dt.Rows[0];
                Address a = new Address();
                a.AddressId = addressId;
                a.City = "" + dr["City"];
                a.Country = "" + dr["Country"];
                a.Street = "" + dr["Street"];
                a.StreetNumber = "" + dr["Street_number"];
                a.Zipcode = "" + dr["ZipCode"];
                return a;
            }
            return null;
        }              

        // a function to fill an instance of the Rentable class when given the id
        public static Rentable getRentable(int rentableId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getRentableByIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = rentableId;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count == 1)
            {
                DataRow dr = dt.Rows[0];
                Rentable r = new Rentable();
                r.Area = Double.Parse("" + dr["Area"]);
                r.Building = getBuilding(Int32.Parse("" + dr["BuildingId"]));                
                r.Cable = ("" + dr["Cable"]).Equals("1");
                r.Internet = ("" + dr["Internet"]).Equals("1");
                r.Description = ("" + dr["Description"]);
                r.Floor = Int32.Parse("" + dr["Floor"]);
                r.OutletCount = Int32.Parse("" + dr["Outlet_Count"]);
                r.Owner = getPerson(Int32.Parse("" + dr["OwnerId"]));
                r.Price = Double.Parse("" + dr["Price"]);
                r.RentableId = rentableId;
                r.FreeFrom = (DateTime)dr["Free"];
                try
                {
                    r.Rented = ("" + dr["Rented"]).Equals("1");
                }
                catch
                {
                    r.Rented = false;
                }
                r.Type = Int32.Parse("" + dr["type"]);
                try
                {
                    r.WindowArea = Double.Parse("" + dr["Window_Area"]);
                }
                catch
                {
                    r.WindowArea = 0;
                }
                try
                {
                    r.WindowDirection = "" + dr["Window_Direction"];
                }
                catch
                {
                    r.WindowDirection = "";
                }
                return r;
            }
            return null;
        }

        //a function to fill an instance of the Rentable class  when given the id of the renter.
        public static Rentable getCurrentRentable(int renterId)
        {
            try
            {
                int rentableId = getCurrentRentableId(renterId);
                return getRentable(rentableId);
            }
            catch
            {
                return null;
            }
        }

        //a function to retrieve the id from a rentable when given the id of the renter.
        private static int getCurrentRentableId(int renterId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getCurrentRentableIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = renterId;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = DateTime.Now;
            command.Parameters.Add(p);
            p = new OleDbParameter();
            p.Value = DateTime.Now;
            command.Parameters.Add(p);
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count > 0)
            {
                return Int32.Parse("" + dt.Rows[0][0]);
            }
            throw new Exception("can't find rentableId");
        }

        //a function to mark a message as read when given the message and the person.
        public static void setRead(Message message, Person person)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand c = conn.CreateCommand();
            c.CommandText = updateMessageSQL;
            //"update messages set message_read = 1 where Date_Sent = ? and subject = ? and recipientid = ?";
            OleDbParameter p = new OleDbParameter();
            p.ParameterName = "begin";
            p.Value = message.DateSent.AddSeconds(-1);
            p.OleDbType = OleDbType.DBTimeStamp;
            c.Parameters.Add(p);

            p = new OleDbParameter();
            p.ParameterName = "end";
            p.Value = message.DateSent.AddSeconds(1);
            p.OleDbType = OleDbType.DBTimeStamp;
            c.Parameters.Add(p);

            p = new OleDbParameter();
            p.ParameterName = "subject";
            p.Value = message.Subject;
            p.OleDbType = OleDbType.VarChar;
            c.Parameters.Add(p);

            p = new OleDbParameter();
            p.ParameterName = "recipientid";
            p.Value = person.PersonId;
            p.OleDbType = OleDbType.Integer;
            c.Parameters.Add(p);

            conn.Open();
            int i = c.ExecuteNonQuery();
            conn.Close();
        }

        //a function to add a message to the database, this function does not commit but returns the
        //transaction object.
        public static OleDbTransaction sendMessage(Message message)
        {            
            OleDbConnection conn = getConnection();
            OleDbTransaction transaction = null;                  
            bool succeeded = false;
            try
            {
                OleDbCommand command = conn.CreateCommand();
                command.CommandText = addMessageSQL;
                OleDbParameter p = new OleDbParameter();
                p.Value = message.Sender.PersonId;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.Recipient.PersonId;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.Subject;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.DateSent;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = 0;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.Text;
                command.Parameters.Add(p);                
                conn.Open();
                transaction = conn.BeginTransaction();
                command.Transaction = transaction;
                command.ExecuteNonQuery();
                succeeded = true;
            }
            catch
            {
                transaction.Rollback();
                conn.Close();                
            }            
            if (succeeded)
            {
                return transaction;
            }
            else
            {
                return null;
            }            
        }

        //a class to represent the order values that can be used in getRentables.
        public static class RentableOrder
        {
            public static string TYPE = "order by r.type";            
            public static string AREA = "order by r.area";            
            public static string PRICE = "order by r.price";            
            public static string FREE = "order by free";            
            public static string INTERNET = "order by r.internet";
            public static string CABLE = "order by r.cable";
            public static string FLOOR = "order by r.floor";
            public static string OUTLET_COUNT = "order by r.outlet_count";
        }

        // a function to retreive the position of a rentable when given the id.
        public static Pair getLongLat(int rentableId)
        {
            Pair pair = null;
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getLongLatByRentableIdSQL;
            OleDbParameter p = new OleDbParameter();
            p.Value = rentableId;
            command.Parameters.Add(p);            
            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();
            da.Fill(ds);
            DataTable dt = ds.Tables[0];
            if (dt.Rows.Count > 0)
            {
                try{
                    double longtitude = Double.Parse("" + dt.Rows[0],CultureInfo.InvariantCulture);
                    double latitude =   Double.Parse("" + dt.Rows[1],CultureInfo.InvariantCulture);
                    pair = new Pair(longtitude,latitude);  
                }catch{}
            }
            return pair;
        }

        //a function to retreive all te pictures from a building when given the id.
        public static DataTable getBuildingPictureIds(int BuildingId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getBuildingPictureIDsByIdSQL;

            OleDbParameter p = new OleDbParameter();
            p.Value = BuildingId;
            command.Parameters.Add(p);

            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();

            da.Fill(ds);
            return ds.Tables[0];
        }

        //a function to retreive all te pictures from a rentable when given the id.
        public static DataTable getRentablePictureIds(int BuildingId)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = getBuildingPictureIDsByIdSQL;

            OleDbParameter p = new OleDbParameter();
            p.Value = BuildingId;
            command.Parameters.Add(p);

            OleDbDataAdapter da = new OleDbDataAdapter(command);
            DataSet ds = new DataSet();

            da.Fill(ds);
            return ds.Tables[0];
        }
    }
}
