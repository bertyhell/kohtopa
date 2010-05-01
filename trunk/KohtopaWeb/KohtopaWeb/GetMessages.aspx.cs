using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Drawing;

namespace KohtopaWeb
{
    public partial class GetMessages : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                Person user = (Person)Session["user"];
                //List<Message> l = DataConnector.getMessages(user.PersonId);
                
                MessageList.DataSource = DataConnector.getMessages(user.PersonId);
                MessageList.DataBind();
                
            }
        }

        protected void select_message(object sender, EventArgs e)
        {
            if (MessageList.SelectedIndex != -1)
            {
                Person user = (Person)Session["user"];
                List<Message> l = DataConnector.getMessages(user.PersonId);
                message.Text = l[MessageList.SelectedIndex].Text;
                DataConnector.setRead(l[MessageList.SelectedIndex],user);
                MessageList.DataSource = DataConnector.getMessages(user.PersonId);
                MessageList.DataBind();
            }
        }
    }

    
}
