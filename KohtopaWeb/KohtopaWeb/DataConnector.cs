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
        private static string password = "e=mc**2";
        private static string databaseName = "XE";        
        private static string connectionString = "Provider=OraOLEDB.Oracle;Data Source=localhost:1521/" + databaseName + ";User Id=" + username + ";Password=" + password + ";";        
        public static string rentableTypes = "Room;Appartment;House";

        private static string getRentablesSQL = "select r.rentableid, r.buildingid, r.ownerid, r.type, r.area, r.window_direction, r.area, r.internet, r.cable, r.outlet_count,r.price, r.floor,b.addressid, b.latitude, b.longitude, a.street, a.street_number, a.city, a.zipcode, a.street from rentables r join buildings b on b.buildingid = r.buildingid join addresses a on b.addressid = a.addressid";
        private static string getPersonIdSQL = "select personId from persons where username = ? and password = ?";
        private static string getPasswordSQL = "select password from persons where username = ?";
        private static string getRentableFromPersonId = "select rentableId from contract where renterid = ? and ? > contract_start and ? < contract_end";
        

        private static string addMessageSQL = "insert into messages values(?,?,?,?)";        

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

        public static int getRentableId(int renterid)
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
    }
}
