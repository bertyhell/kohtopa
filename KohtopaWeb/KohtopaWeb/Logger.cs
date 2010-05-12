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
using System.IO;

namespace KohtopaWeb
{
    public class Logger
    {
        private static object lockObject = new object();
        public static void log(HttpServerUtility server,string message)
        {
            lock (lockObject)
            {
                string path = server.MapPath("Logger/" + DateTime.Now.ToString("dd_MM_yyyy") + ".log");
                File.AppendAllText(path, DateTime.Now.ToString("HH:mm:ss") + " : " + message + Environment.NewLine);
            }
        }
    }
}
