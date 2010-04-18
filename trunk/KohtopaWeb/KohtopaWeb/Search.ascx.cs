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
using System.Globalization;

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

                ViewState["rentableOrder"] = DataConnector.RentableOrder.PRICE;
                updateFilterView();

                DataTable dtFilters = new DataTable();
                dtFilters.Columns.Add("id");
                dtFilters.Columns.Add("text");
                dtFilters.Columns.Add("type");
                DataColumn[] key = new DataColumn[1];
                key[0] = dtFilters.Columns[0];
                dtFilters.PrimaryKey = key;
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

                btnRequired.Text = Language.getstring("Required", language);
                btnReject.Text = Language.getstring("Reject", language);

                lblContains.Text = Language.getstring("Contains", language);
                btnContains.Text = Language.getstring("Add", language);

                lblStreet.Text = Language.getstring("Street", language);
                lblStreetNumber.Text = Language.getstring("StreetNumber", language);
                lblCity.Text = Language.getstring("City", language);
                lblCountry.Text = Language.getstring("Country", language);
                lblMaxDistance.Text = Language.getstring("MaxDistanceKM", language);
                rfvMaxDistance.ErrorMessage = Language.getstring("RequiredField", language);
                rfvStreet.ErrorMessage = Language.getstring("RequiredField", language);                
                rvMaxDistance.ErrorMessage = Language.getstring("NumberBetween0_100", language);
                btnMaxDistance.Text = Language.getstring("Add", language);

                ddlFilters_Selected_Index_Changed(null, null);
                
            }
            else
            {
                updateFilterView();
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
            tblContains.Visible = type.Equals("Contains");
            tblDistance.Visible = type.Equals("Distance");
        }

        protected void btnBetween_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();
            DataView dv = new DataView(searchTable);
            dv.RowFilter = ("data = '" + ddlFilters.SelectedValue) + "'";
            DataRow dr;
            if (dv.ToTable().Rows.Count > 0)
            {
                dr = dv.ToTable().Rows[0];
                string[] keys = { "" + dr["data"], "" + dr["value1"] };
                dr = searchTable.Rows.Find(keys);
            }
            else
            {
                dr = searchTable.NewRow();
            }        
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Between";
            dr["value1"] = Double.Parse(txtMin.Text,CultureInfo.InvariantCulture);
            dr["value2"] = Double.Parse(txtMax.Text, CultureInfo.InvariantCulture);
            try
            {
                searchTable.Rows.Add(dr);
            }
            catch {} //if dr is not a new row;

            updateFilterView();
        }

        protected void btnRequired_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();
            DataView dv = new DataView(searchTable);
            dv.RowFilter = "data = '" + ddlFilters.SelectedValue + "'";
            
            DataRow dr;
            if (dv.ToTable().Rows.Count == 1)
            {
                dr = dv.ToTable().Rows[0];
                string[] keys = { "" + dr["data"], "" + dr["value1"] };
                dr = searchTable.Rows.Find(keys);
            }
            else
            {
                dr = searchTable.NewRow();
            }
            
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Required";
            if (((Button)sender).Equals(btnRequired))
            {
                dr["value1"] = 1;
            }
            else
            {
                dr["value1"] = 0;
            }            
            try
            {
                searchTable.Rows.Add(dr);                
            }
            catch {} //if dr is not a new row;
            updateFilterView();
        }

        protected void btnContains_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();            
            DataRow dr = searchTable.NewRow();            
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Contains";
            dr["value1"] = txtContains.Text;
            try
            {
                searchTable.Rows.Add(dr);
            }
            catch {} //if dr is not a new row;
            updateFilterView();
        }

        protected void btnMaxDistance_Click(object sender, EventArgs e)
        {
            DataTable searchTable = getSearchTable();
            DataView dv = new DataView(searchTable);
            dv.RowFilter = "data = '" + ddlFilters.SelectedValue + "'";
            DataRow dr;
            if (dv.ToTable().Rows.Count == 1)
            {
                dr = dv.ToTable().Rows[0];
                string[] keys = { "" + dr["data"], "" + dr["value1"] };
                dr = searchTable.Rows.Find(keys);
            }
            else
            {
                dr = searchTable.NewRow();
            }
            dr["data"] = ddlFilters.SelectedValue;
            dr["operation"] = "Distance";
            string address = txtStreet.Text + " " + txtStreetNumber.Text + " , " + txtCity.Text + " , " + txtCountry.Text;
            Pair longLat = GoogleGeocoding.getLongLat(address);            
            if (longLat != null)
            {                
                try
                {                    
                    dr["value1"] = address;
                    string longLatDist = "" + longLat.First + ";" + longLat.Second + ";" + txtMaxDistance.Text;
                    longLatDist = longLatDist.Replace(',', '.');
                    dr["value2"] = longLatDist;
                    searchTable.Rows.Add(dr);
                }
                catch { } //if dr is not a new row;
                updateFilterView();
            }
        }

        private DataTable getSearchTable()
        {
            DataTable searchTable = (DataTable)Session["searchTable"];
            if (searchTable == null)
            {
                searchTable = new DataTable();
                DataColumn primary = searchTable.Columns.Add("data");
                searchTable.Columns.Add("operation");
                DataColumn secondary = searchTable.Columns.Add("value1");
                searchTable.Columns.Add("value2");
                DataColumn[] keys = {primary,secondary};
                searchTable.PrimaryKey = keys;                
                Session["searchTable"] = searchTable;
            }
            return searchTable;
        }

        private void updateFilterView()
        {                        
            DataTable searchTable = (DataTable)Session["searchTable"];
            try
            {
                updateRentableColumns();
                updateFilterColumns();
                string test = "" + ViewState["rentableOrder"];
                DataView dv = new DataView(DataConnector.getRentables("" + ViewState["rentableOrder"]));
                lblDatabaseError.Visible = false;
                gvRentables.Visible = true;
                if (searchTable != null)
                {
                    //DataTable fv = new DataTable();
                    //string language = "" + Session["Language"];
                    //string data = Language.getstring("Data", language);
                    //string operation = Language.getstring("Operation", language);
                    //string value1 = Language.getstring("Value1", language);
                    //string value2 = Language.getstring("Value2", language);
                    //fv.Columns.Add(data);
                    //fv.Columns.Add(operation);
                    //fv.Columns.Add(value1);
                    //fv.Columns.Add(value2);
                    //int i = 0;
                    dv.RowFilter = "1 = 1";
                    foreach (DataRow dr in searchTable.Rows)
                    {
                     //   DataRow r = fv.NewRow();
                     //   r[data] = Language.getstring("" + dr["data"], language);
                     //   r[operation] = Language.getstring("" + dr["operation"], language);
                     //   r[value1] = dr["value1"];
                     //   r[value2] = dr["value2"];
                     //   fv.Rows.Add(r);
                     //   i++;
                        if ((string)dr["operation"] == "Between")
                        {
                            dv.RowFilter += (" AND " + dr["data"] + " >=" + dr["value1"] + " AND " + dr["data"] + " <= " + dr["value2"] );
                        }
                        else if ((string)dr["operation"] == "Required")
                        {
                            dv.RowFilter += (" AND " + dr["data"] + "= " + dr["value1"]);
                        }
                        else if ((string)dr["operation"] == "Contains")
                        {
                            dv.RowFilter += (" AND " + dr["data"] + " LIKE '%" + dr["value1"] + "%'" );
                        }
                        else if ((string)dr["operation"] == "Distance")
                        {                                                        
                            string[] values = ("" + dr["value2"]).Split(';');
                            try
                            {
                                string rf = dv.RowFilter;
                                dv.Table = DataConnector.getRentables(double.Parse(values[0],CultureInfo.InvariantCulture), double.Parse(values[1],CultureInfo.InvariantCulture) , double.Parse(values[2],CultureInfo.InvariantCulture), "" + ViewState["rentableOrder"]);
                                dv.RowFilter = rf;
                            }
                            catch{}
                        }
                    }
                    //gvFilters.DataSource = fv;
                    //gvFilters.DataBind();

                    gvRentables.DataSource = dv;                    
                    gvRentables.DataBind();
                }
                else
                {                    
                    gvRentables.DataSource = dv;
                    gvRentables.DataBind();
                }
            }
            catch (Exception exception)
            {
                lblDatabaseError.Text = exception.Message + exception.StackTrace;
                lblDatabaseError.Visible = true;
                gvRentables.Visible = false;
            }


        }

        private void updateFilterColumns()
        {
            DataTable searchTable = (DataTable)Session["searchTable"];
            try
            {
                string language = "" + Session["Language"];
                gvFilters.Columns.Clear();
                gvFilters.DataSource = searchTable;

                CommandField cf = new CommandField();
                cf.HeaderText = "X";
                cf.ShowDeleteButton = true;
                cf.DeleteText = "X";
                gvFilters.Columns.Add(cf);

                Label lbl = new Label();
                lbl.Text = "DataBind:FilterType:None";
                TableCell tc = new TableCell();
                tc.HorizontalAlign = HorizontalAlign.Center;
                tc.Controls.Add(lbl);
                TableRow tr = new TableRow();
                tr.Cells.Add(tc);
                Table table = new Table();
                table.Width = Unit.Percentage(100);
                table.Rows.Add(tr);
                GridViewTemplate gvt = new GridViewTemplate(table, language);
                TemplateField tf = new TemplateField();
                tf.HeaderText = Language.getstring("Filter", language);
                tf.ItemTemplate = gvt;            
                gvFilters.Columns.Add(tf);

                gvFilters.DataBind();
            }
            catch{}
        }

        private void updateRentableColumns()
        {

            string language = "" + Session["Language"];
            gvRentables.Columns.Clear();

            Label lbl = new Label();
            lbl.Text = "DataBind:DateType:Free";
            TableCell tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);
            TableRow tr = new TableRow();
            tr.Cells.Add(tc);
            Table table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            GridViewTemplate gvt = new GridViewTemplate(table, language);
            TemplateField tf = new TemplateField();
            tf.HeaderText = Language.getstring("FreeFrom",language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.FREE;
            gvRentables.Columns.Add(tf);            

            lbl = new Label();
            lbl.Text="DataBind:RentableType:Type";                        
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);            
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);                        
            gvt = new GridViewTemplate(table,language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Type", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.TYPE;
            gvRentables.Columns.Add(tf);

            table = new Table();
            table.Width = Unit.Percentage(100);
            tr = new TableRow();            
            lbl = new Label();
            lbl.Text = "DataBind:Normal:Street";            
            tc = new TableCell();
            tc.Controls.Add(lbl);
            lbl = new Label();
            lbl.Text = " ";
            tc.Controls.Add(lbl);
            lbl = new Label();
            lbl.Text = "DataBind:Normal:Street_Number";            
            tc.Controls.Add(lbl);
            tr.Cells.Add(tc);
            table.Rows.Add(tr);
            tr = new TableRow();            
            lbl = new Label();
            lbl.Text = "DataBind:Normal:ZipCode";
            tc = new TableCell();            
            tc.Controls.Add(lbl);
            lbl = new Label();
            lbl.Text = " ";
            tc.Controls.Add(lbl);
            lbl = new Label();
            lbl.Text = "DataBind:Normal:City";            
            tc.Controls.Add(lbl);
            tr.Cells.Add(tc);
            table.Rows.Add(tr);
            tr = new TableRow();
            lbl = new Label();
            lbl.Text = "DataBind:CountryType:Country";
            tc = new TableCell();
            tc.Controls.Add(lbl);
            tr.Cells.Add(tc);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Address", language);
            tf.ItemTemplate = gvt;
            gvRentables.Columns.Add(tf);

            lbl = new Label();
            lbl.Text = "DataBind:Currency:Price";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Price", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.PRICE;
            gvRentables.Columns.Add(tf);

            lbl = new Label();
            lbl.Text = "DataBind:Normal:Floor";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Floor", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.FLOOR;
            gvRentables.Columns.Add(tf);

            lbl = new Label();
            lbl.Text = "DataBind:Area:Area";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Area", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.AREA;
            gvRentables.Columns.Add(tf);

            Image img = new Image();            
            img.ImageUrl = "DataBind:Boolean:Internet";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(img);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();            
            tf.HeaderText = Language.getstring("Internet", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.INTERNET;
            gvRentables.Columns.Add(tf);

            img = new Image();
            img.ImageUrl = "DataBind:Boolean:Cable";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(img);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Cable", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.CABLE;
            gvRentables.Columns.Add(tf);
            
            /*
            lbl = new Label();
            lbl.Text = "DataBind:Normal:Outlet_count";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbl);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("OutletCount", language);
            tf.ItemTemplate = gvt;
            tf.SortExpression = DataConnector.RentableOrder.OUTLET_COUNT;
            gvRentables.Columns.Add(tf);
            */

            LinkButton  lbtn = new LinkButton();            
            lbtn.Text = Language.getstring("Details", language);
            lbtn.PostBackUrl = "DataBind:clickDetails:rentableId";
            tc = new TableCell();
            tc.HorizontalAlign = HorizontalAlign.Center;
            tc.Controls.Add(lbtn);
            tr = new TableRow();
            tr.Cells.Add(tc);
            table = new Table();
            table.Width = Unit.Percentage(100);
            table.Rows.Add(tr);
            gvt = new GridViewTemplate(table, language);
            tf = new TemplateField();
            tf.HeaderText = Language.getstring("Details", language);
            tf.ItemTemplate = gvt;            
            gvRentables.Columns.Add(tf); 

        }

        protected void gvFilters_RowDeleting(object sender, EventArgs e)
        {
            DataTable searchTable = (DataTable)Session["searchTable"];
            if (searchTable != null)
            {
                searchTable.Rows[((GridViewDeleteEventArgs)e).RowIndex].Delete();
            }
            updateFilterView();
        }

        protected void gvRentables_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvRentables.PageIndex = e.NewPageIndex;
            updateFilterView();
        }

        protected void gvRentables_Sorting(object sender, GridViewSortEventArgs e)
        {
            if (ViewState["rentableOrder"].Equals(e.SortExpression))
            {
                ViewState["rentableOrder"] = e.SortExpression + " desc";
            }
            else
            {
                ViewState["rentableOrder"] = e.SortExpression;
            }
            updateFilterView();
        }
        
    }
}