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
            m.RecipientId = user.Rentable.Owner.PersonId;
            m.SenderId = user.PersonId;
            m.Subject = txtSubject.Text;
            m.Text = txtMessage.Text;
            bool succeeded = m.sendMessage();
            if (succeeded)
            {
                try
                {
                    MailMessage mail = new MailMessage();
                    mail.From = user.Email;
                    mail.To = user.Rentable.Owner.Email;
                    mail.Subject = txtSubject.Text;
                    mail.Body = txtMessage.Text;                    
                    SmtpMail.Send(mail);
                }
                catch(Exception exc)
                {
                    int i = 0;
                }
            }
        }
    }
}
