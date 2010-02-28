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

                
                DataTable dtFilters = new DataTable();                
                dtFilters.Columns.Add("id");
                dtFilters.Columns.Add("text");
                dtFilters.Columns.Add("type");
                DataColumn[] key = new DataColumn[1];
                key[0] = dtFilters.Columns[0];
                dtFilters.PrimaryKey = key ;
                string[] s = ConfigurationManager.AppSettings["Filters"].Split(';');
                for (int i = 0; i < s.Length / 2; i++)
                {
                    DataRow dr = dtFilters.NewRow();
                    dr["id"] = s[i * 2];
                    dr["text"] = Language.getstring(s[i * 2], language);
                    dr["type"] = s[i * 2 + 1];
                    dtFilters.Rows.Add(dr);                    
                }
                ViewState["filters"] = dtFilters;
                ddlFilters.DataSource = dtFilters;
                ddlFilters.DataTextField = "text";
                ddlFilters.DataValueField = "id";
                ddlFilters.DataBind();                                

                lblMin.Text = Language.getstring("Min", language);
                rfvMin.ErrorMessage = Language.getstring("RequiredField", language);
                rvMin.ErrorMessage = Language.getstring("RequiredPositiveNumber", language);
                lblMax.Text = Language.getstring("Max", language);                
                rfvMax.ErrorMessage = Language.getstring("RequiredField", language);                
                rvMax.ErrorMessage = Language.getstring("RequiredPositiveNumber", language);
                cvBetween.ErrorMessage = Language.getstring("RequiredGreaterThanMin", language);
                btnBetween.Text = Language.getstring("Add", language);

                lblRequired.Text = Language.getstring("Required", language);
                btnRequired.Text = Language.getstring("Add", language);

                ddlFilters_Selected_Index_Changed(null, null);
            }
        }        

        protected void ddlFilters_Selected_Index_Changed(object sender, EventArgs e)
        {
            DataTable dtFilters = (DataTable)ViewState["filters"];
            setSearchTables("" + dtFilters.Rows.Find(ddlFilters.SelectedValue)["type"]);
        }

        private void setSearchTables(string type)
        {
            tblBetween.Visible = type.Equals("Between");
            tblRequired.Visible = type.Equals("Required");
        }

        protected void btnBetween_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();
            DataRow dr = searchTable.NewRow();
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Between";
            dr["value1"] = Double.Parse(txtMin.Text);
            dr["value2"] = Double.Parse(txtMax.Text);
            searchTable.Rows.Add(dr);
        }

        protected void btnRequired_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();
            DataRow dr = searchTable.NewRow();
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Required";
            dr["value1"] = null;
            dr["value2"] = null;
            searchTable.Rows.Add(dr);
        }

        private DataTable getSearchTable()
        {
            DataTable searchTable = Session["searchTable"];
            if (searchTable == null)
            {
                searchTable = new DataTable();
                searchTable.Columns.Add("data");
                searchTable.Columns.Add("operation");
                searchTable.Columns.Add("value1");
                searchTable.Columns.Add("value2");
                Session["searchTable"] = searchTable;
            }
            return searchTable;
        }
    }
}