using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.SessionState;
using System.Xml.Linq;
using System.Web.Mail;

namespace KohtopaWeb
{
    public class Global : System.Web.HttpApplication
    {

        protected void Application_Start(object sender, EventArgs e)
        {
            try
            {
                Language.read(Server);                
                SmtpMail.SmtpServer = ConfigurationManager.AppSettings["smtpServer"];
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        protected void Session_Start(object sender, EventArgs e)            
        {
            try
            {
                Session["language"] = ConfigurationManager.AppSettings["DefaultLanguage"];
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        protected void Application_BeginRequest(object sender, EventArgs e)
        {

        }

        protected void Application_AuthenticateRequest(object sender, EventArgs e)
        {

        }

        protected void Application_Error(object sender, EventArgs e)
        {

        }

        protected void Session_End(object sender, EventArgs e)
        {

        }

        protected void Application_End(object sender, EventArgs e)
        {

        }
    }
}