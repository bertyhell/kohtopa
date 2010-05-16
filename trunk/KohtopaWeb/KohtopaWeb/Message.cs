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
using System.Text.RegularExpressions;
namespace KohtopaWeb
{
    public class Message
    {
        //a class to represent a message from the database

        private Person sender, recipient;
        private string subject, text;
        private bool read;
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
            set {
                text = value;
                text = Regex.Replace(text, @"[ \t]+", " ");
            }
        }

        public string Subject
        {
            get { return subject; }
            set { subject = value; }
        }

        public long DateTicks
        {
            get { return DateSent.Ticks;  }
        }

        public DateTime DateSent
        {
            get { return dateSend; }
            set { dateSend = value; }
        }

        public bool Read
        {
            get { return read; }
            set { read = value; }
        }

        public string State
        {
            get { return read?"read":"unread"; }
         }

        public override string ToString()
        {
            if (!read) return "(NEW) " + subject;
            
            return subject;
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
                    catch(Exception exc)
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
