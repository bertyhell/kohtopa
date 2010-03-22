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
using System.Data.OleDb;
using System.Web.Mail;
namespace KohtopaWeb
{
    public class Message
    {        

        private Person sender, recipient;
        private string subject, text;
        private DateTime dateSend;        

        public Message()
        {
            subject = "";
            text = "";            
        }

        public Person Recipient
        {
            get { return recipient; }
            set { recipient = value; }
        }

        public Person Sender
        {
            get { return sender; }
            set { sender = value; }
        }        

        public string Text
        {
            get { return text; }
            set { text = value; }
        }

        public string Subject
        {
            get { return subject; }
            set { subject = value; }
        }
        

        public DateTime DateSend
        {
            get { return dateSend; }
            set { dateSend = value; }
        }

        public bool sendMessage()
        {
            try
            {
                OleDbTransaction transaction = DataConnector.sendMessage(this);
                if (transaction != null)
                {
                    try
                    {                        
                        MailMessage mail = new MailMessage();
                        mail.From = sender.Email;
                        mail.To = recipient.Email;
                        mail.Subject = subject;
                        mail.Body = text;
                        SmtpMail.Send(mail);
                        try
                        {
                            transaction.Commit();                            
                            return true;
                        }
                        catch
                        {
                            return false;
                        }
                    }
                    catch
                    {
                        try
                        {
                            transaction.Rollback();
                            transaction.Connection.Close();
                        }
                        catch { }
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            catch
            {
                return false;
            }
        }               
    }
}
