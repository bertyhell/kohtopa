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
            lblLogin.Text = Language.getString("Username", "EN");
            lblPasswd.Text = Language.getString("Password", "EN");
            btnLogin.Text = Language.getString("Login", "EN");
        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            if (txtLogin.Text.Equals(txtPasswd.Text))
            {
                FormsAuthentication.RedirectFromLoginPage("Renter", false);
            }
        }
    }
}
