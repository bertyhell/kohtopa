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
        private static string usernameDB = "system";
        private static string password = "admin";
        private static string databaseName = "XE";        
        private static string connectionString = "Provider=OraOLEDB.Oracle;Data Source=localhost:1521/" + databaseName + ";User Id=" + usernameDB + ";Password=" + password + ";";        
        public static string rentableTypes = "Room;Appartment;House";        
        private static string getRentablesSQL = "select r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode,a.country ,max(c.contract_end) as free "
                                                    + "from rentables r "
                                                    + "join buildings b on b.buildingid = r.buildingid "
                                                    + "join addresses a on b.addressid = a.addressid "
                                                    + "join contracts c on c.rentableid = r.rentableid "
                                                    + "group by r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_direction, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode, a.country";        
        private static string getPersonIdSQL = "select personId from persons where username = ? and password = ?";
        private static string getPasswordSQL = "select password from persons where username = ?";
        private static string getPersonByUsernameSQL = "select * from persons where userName = ?";
        private static string getPersonByIdSQL = "select * from persons where personId = ?";
        private static string getAddressSQL = "select * from addresses where addressId = ?";
        private static string getRentableByIdSQL = "select * from rentables where rentableId = ?";
        private static string getCurrentRentableIdSQL = "select rentableId as rentableId from contract where renterid = ? and contract_start < ? and contract_end > ?";
        private static string getMessagesByIdSQL = "select * from messages where recipientId = ?";
        private static string getLongLatByRentableIdSQL = "select b.longitude, b.latitude from rentables r join buildings b on b.buildingid = r.buildingid where r.rentableid = ?";
        private static string getBuildingByIdSQL = "select * from buildings where buildingId = ?";


        private static string updateMessageSQL = "update messages set message_read = '1' where date_sent = ? and subject = ? and recipientid = ?";
        
        private static string addMessageSQL = "insert into messages values(?,?,?,?,?,?)";

        private static string selectTasks = "select distinct description,start_time,c.rentableid " +
                                    "from tasks t " +
                                    "join contracts c on t.rentableid = c.rentableid and c.renterid = ?" +
                                    /*"where start_time between ? and ? */"order by start_time desc" ; 


        public static string distanceFilterSQL = "where (ACOS(SIN(latitude*3.14159265/180) * SIN( ? *3.14159265/180) + COS(latitude * 3.14159265/180) * COS( ? * 3.14159265/180) * COS((longitude - ? )*3.14159265/180))/ 3.14159265 * 180) * 111.18957696 <= ?";

        private static OleDbConnection getConnection(){
            return new OleDbConnection(connectionString);            
        }

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

        

        //todo parameters
        public static byte[] getPicture(int ImageId){
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();
            command.CommandText = "select data from pictures where pictureId = " + ImageId;
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

        public static DataTable getRentables(string order)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();            
            OleDbDataAdapter da = new OleDbDataAdapter(getRentablesSQL + " " + order, conn);
            DataSet ds = new DataSet();
            da.Fill(ds);            
            return ds.Tables[0];
        }

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
        public static void setRead(Message message, Person person)
        {
            OleDbConnection conn = getConnection();
            OleDbCommand c = conn.CreateCommand();
            c.CommandText = updateMessageSQL;
            //"update messages set message_read = 1 where Date_Sent = ? and subject = ? and recipientid = ?";
            OleDbParameter p = new OleDbParameter();
            p.ParameterName = "date_sent";
            p.Value = message.DateSent;
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
    }
}
