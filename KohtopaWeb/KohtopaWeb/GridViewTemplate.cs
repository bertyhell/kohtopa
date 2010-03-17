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
    public class GridViewTemplate : ITemplate
    {
        private Table table;
        private string language;
        public GridViewTemplate(Table table,string language)
        {
            this.table = table;
            this.language = language;
        }
     
        void ITemplate.InstantiateIn(System.Web.UI.Control container)
        {
            Table tbl = new Table();
            container.Controls.Add(tbl);
            foreach (TableRow tr in table.Rows)
            {
                TableRow tblr = new TableRow();
                tbl.Rows.Add(tblr);
                foreach (TableCell tc in tr.Cells)
                {
                    TableCell tblc = new TableCell();
                    tblr.Cells.Add(tblc);
                    foreach (Control control in tc.Controls)
                    {                           
                        if (control.GetType() == typeof(Label))
                        {
                            Label label = new Label();                            
                            Label lbl = (Label)control;                            
                            label.AccessKey = lbl.AccessKey;
                            label.AppRelativeTemplateSourceDirectory = lbl.AppRelativeTemplateSourceDirectory;
                            label.AssociatedControlID = lbl.AssociatedControlID;                            
                            label.BackColor = lbl.BackColor;
                            label.BorderColor = lbl.BorderColor;
                            label.BorderStyle = lbl.BorderStyle;
                            label.BorderWidth = lbl.BorderWidth;                            
                            label.CssClass = lbl.CssClass;
                            label.Enabled = lbl.Enabled;
                            label.EnableTheming = lbl.EnableTheming;
                            label.EnableViewState = lbl.EnableViewState;                            
                            label.ForeColor = lbl.ForeColor;
                            label.Height = lbl.Height;
                            label.SkinID = lbl.SkinID;
                            label.TabIndex = lbl.TabIndex;
                            label.TemplateControl = lbl.TemplateControl;
                            label.Text = lbl.Text;
                            label.ToolTip = lbl.ToolTip;
                            label.Visible = lbl.Visible;
                            label.Width = lbl.Width;                                                        
                            if (label.Text.StartsWith("DataBind:"))
                            {                                
                                label.DataBinding += new EventHandler(lbl_DataBinding);
                            }                            
                            tblc.Controls.Add(label);                            
                        }
                    }
                }
            }
        }
     
        /// <summary>
        /// This is the event, which will be raised when the binding happens.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void lbl_DataBinding(object sender, EventArgs e)
        {
            Label lbl = (Label)sender;                   
            string[] data = lbl.Text.Split(':');
            GridViewRow container = (GridViewRow)lbl.NamingContainer;
            if (data[1].Equals("Normal"))
            {                
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    lbl.Text = dataValue.ToString();
                }
            }
            else if (data[1].Equals("RentableType"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    try
                    {
                        int i = Int32.Parse(dataValue.ToString());
                        lbl.Text = Language.getstring(DataConnector.rentableTypes.Split(';')[i],language);
                    }
                    catch (Exception exc)
                    {
                    }
                }
            }
        }        
    }
}
