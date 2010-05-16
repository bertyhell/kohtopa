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
            try
            {
                if (!IsPostBack || (bool)Session["LanguageChanged"])
                {
                    Session["LanguageChanged"] = false;
                    string language = "" + Session["Language"];
                    lblSubject.Text = Language.getstring("Subject", language);
                    lblMessage.Text = Language.getstring("Message", language);
                    btnSend.Text = Language.getstring("Send", language);
                    lblError.Text = Language.getstring("NoContract", language);
                    Person user = (Person)Session["user"];
                    if (user != null && user.RoleId != "user")
                    {
                        sendMessageTable.Visible = false;                        
                    }
                    if (user != null && user.Rentable == null) //if the user does not have a contract he can not send any messages.
                    {
                        sendMessageTable.Visible = false;
                        lblError.Visible = true;
                    }
                }
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        //a function to send the message, this is adding it to de database and sending a mail to the owner.
        //if one of the 2 things fail notting is added in the database.
        protected void btnSend_Click(object sender, EventArgs e)
        {
            try
            {
                Person user = (Person)Session["user"];
                Message m = new Message();
                m.DateSent = DateTime.Now;
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
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }
    }
}
