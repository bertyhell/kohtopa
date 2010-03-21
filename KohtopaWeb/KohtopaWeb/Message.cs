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

namespace KohtopaWeb
{
    public class Message
    {        

        private int senderId, recipientId;
        private string subject, text;
        private DateTime dateSend;

        public Message()
        {
            subject = "";
            text = "";            
        }

        public int RecipientId
        {
            get { return recipientId; }
            set { recipientId = value; }
        }

        public int SenderId
        {
            get { return senderId; }
            set { senderId = value; }
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
                DataConnector.sendMessage(this);
                return true;
            }
            catch
            {
                return false;
            }
        }
    }
}
