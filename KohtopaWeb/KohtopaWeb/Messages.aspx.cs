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
using System.Web.Mail;

namespace KohtopaWeb
{
    public partial class Messages : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack || Session["LanguageChanged"].Equals(true))
            {
                Session["LanguageChanged"] = false;
                string language = "" + Session["Language"];
                lblSubject.Text = Language.getstring("Subject", language);
                lblMessage.Text = Language.getstring("Message", language);
                btnSend.Text = Language.getstring("Send",language);
            }
        }

        protected void btnSend_Click(object sender, EventArgs e)
        {
            Person user = (Person)Session["user"];
            Message m = new Message();
            m.DateSend = DateTime.Now;
            m.Recipient = user.Rentable.Owner;
            m.Sender = user;
            m.Subject = txtSubject.Text;
            m.Text = txtMessage.Text;
            bool succeeded = m.sendMessage();
            if (succeeded)
            {
                lblSucceeded.Text = Language.getstring("MessageSendSucceeded", "" + Session["Language"]);
                lblSucceeded.ForeColor = System.Drawing.Color.Black;
                txtMessage.Text = "";
                txtSubject.Text = "";
                txtSubject.Focus();                
                lblSucceeded.Visible = true;
            }
            else
            {
                lblSucceeded.Text = Language.getstring("MessageSendFailed", "" + Session["Language"]);
                lblSucceeded.ForeColor = System.Drawing.Color.Red;
                lblSucceeded.Visible = true;
            }
        }
    }
}
