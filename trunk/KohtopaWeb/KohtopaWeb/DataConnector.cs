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
        private static string connectionString = "Provider=OraOLEDB.Oracle;Data Source=localhost:1521/XE;User Id=system;Password=admin;";        
                        
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
    }
}
