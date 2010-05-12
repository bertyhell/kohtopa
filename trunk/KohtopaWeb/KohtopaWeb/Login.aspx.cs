using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;

namespace KohtopaWeb
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                login.UserNameLabelText = Language.getstring("Username", (string)Session["Language"]);
                login.PasswordLabelText = Language.getstring("Password", (string)Session["Language"]);
                login.LoginButtonText = Language.getstring("Login", "EN");
                login.UserNameRequiredErrorMessage = Language.getstring("RequiredField", (string)Session["Language"]);
                login.PasswordRequiredErrorMessage = Language.getstring("RequiredField", (string)Session["Language"]);
                login.FailureText = Language.getstring("UsernamePasswordWrong", (string)Session["Language"]);

                if (!User.Identity.IsAuthenticated)
                {
                    Session["user"] = null;
                }
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        protected void login_Authenticate(object sender, AuthenticateEventArgs e)
        {
            try
            {
                Person p = DataConnector.getPerson(login.UserName);
                if (p != null && p.Password.Equals(login.Password))
                {
                    Session["user"] = p;

                    e.Authenticated = true;
                }
                else
                {
                    e.Authenticated = false;

                }
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }


    }
}
