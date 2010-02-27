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
    public partial class Search1 : System.Web.UI.UserControl
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack || Session["LanguageChanged"].Equals(true))
            {
                Session["LanguageChanged"] = false;
                string language = "" + Session["Language"];
                lblFilter.Text = Language.getstring("Filter", language);
                
                string[] s = ConfigurationManager.AppSettings["Filters"].Split(';');
                for (int i = 0; i < s.Length / 2; i++)
                {
                    ListItem li = new ListItem(Language.getstring(s[i * 2],language), s[i * 2]);
                    li.Attributes.Add("type", s[i * 2 + 1]);
                    ddlFilters.Items.Add(li);
                }                
            }
        }

        protected void btnAdd_Click(object sender, EventArgs e)
        {
            tblSearchTable.Rows.Clear();
            ListItem li = ddlFilters.SelectedItem;
            
        }
       
    }
}