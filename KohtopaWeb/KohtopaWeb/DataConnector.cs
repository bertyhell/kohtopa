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
namespace KohtopaWeb
{
    public class DataConnector
    {
        private static string username = "system";
        private static string password = "admin";
        private static string databaseName = "XE";        
        private static string connectionString = "Provider=OraOLEDB.Oracle;Data Source=localhost:1521/" + databaseName + ";User Id=" + username + ";Password=" + password + ";";        
        public static string rentableTypes = "Room;Appartment;House";

        private static string getRentablesSQL = "select r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_direction, r.area, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode, a.street from rentables r join buildings b on b.buildingid = r.buildingid join addresses a on b.addressid = a.addressid";
        private static string getPersonIdSQL = "select personId from persons where username = ? and password = ?";
        private static string getPasswordSQL = "select password from persons where username = ?";
        private static string getPersonByUsernameSQL = "select * from persons where userName = ?";
        private static string getPersonByIdSQL = "select * from persons where personId = ?";
        private static string getAddressSQL = "select * from addresses where addressId = ?";
        private static string getRentableByIdSQL = "select * from rentables where rentableId = ?";
        private static string getCurrentRentableIdSQL = "select rentableId as rentableId from contract where renterid = ? and contract_start < ? and contract_end > ?";                

        private static string addMessageSQL = "insert into messages values(?,?,?,?,?,?)";        

        private static OleDbConnection getConnection(){
            return new OleDbConnection(connectionString);            
        }
        
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

        public static DataTable getRentables()
        {
            OleDbConnection conn = getConnection();
            OleDbCommand command = conn.CreateCommand();            
            OleDbDataAdapter da = new OleDbDataAdapter(getRentablesSQL, conn);
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
                person.PersonId = Int32.Parse("" + dr["personId"]);
                person.RoleId = "" + dr["RoleId"];
                person.Telephone = "" + dr["Telephone"];
                person.Username = username;
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
                r.BuildingId = Int32.Parse("" + dr["BuildingId"]);
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

        public static bool sendMessage(Message message)
        {
            OleDbConnection conn = getConnection();
            bool succeeded = false;
            try
            {
                OleDbCommand command = conn.CreateCommand();
                command.CommandText = addMessageSQL;
                OleDbParameter p = new OleDbParameter();
                p.Value = message.SenderId;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.RecipientId;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.Subject;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.DateSend;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = 0;
                command.Parameters.Add(p);
                p = new OleDbParameter();
                p.Value = message.Text;
                command.Parameters.Add(p);
                conn.Open();
                command.ExecuteNonQuery();
                succeeded = true;
            }
            finally
            {
                conn.Close();                
            }
            return succeeded;
        }
    }
}
