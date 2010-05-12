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
                reloadMessages();
            }
        }

        private void reloadMessages()
        {
            try
            {
                Person user = (Person)Session["user"];
                List<Message> m = DataConnector.getMessages(user.PersonId);
                Session["messages"] = m;
                messages.DataSource = m;
                messages.DataBind();
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }

        protected void SelectMessage(object sender, GridViewCommandEventArgs e)
        {
            try
            {
                //tmp.Text = e.CommandSource.ToString();
                int index = int.Parse(e.CommandArgument.ToString()) + messages.PageIndex * messages.PageSize;
                Message m = ((List<Message>)Session["messages"])[index];
                Person user = (Person)Session["user"];
                DataConnector.setRead(m, user);
                message.Text = m.Text;

                reloadMessages();
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }

        }

        protected void indexChange(object sender, GridViewPageEventArgs e)
        {
            try
            {
                messages.PageIndex = e.NewPageIndex;
                messages.DataSource = (List<Message>)Session["messages"];
                messages.DataBind();
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }

        }
    }

    
}
