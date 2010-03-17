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
            lblUsername.Text = Language.getstring("Username", (string)Session["Language"]);
            lblPassword.Text = Language.getstring("Password", (string)Session["Language"]);
            btnLogin.Text = Language.getstring("Login", "EN");
            rfvUsername.ErrorMessage = Language.getstring("RequiredField", (string)Session["Language"]);
            rfvPassword.ErrorMessage = Language.getstring("RequiredField", (string)Session["Language"]);
        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            if (DataConnector.isValidPerson(txtUsername.Text, txtPassword.Text))
            {
                Session["username"] = txtUsername.Text;
                FormsAuthentication.RedirectFromLoginPage("Renter", false);
                lblError.Visible = false;
            }
            else
            {
                lblError.Visible = true;
                lblError.Text = Language.getstring("UsernamePasswordWrong","" + Session["Language"]);
            }
        }
    }
}
