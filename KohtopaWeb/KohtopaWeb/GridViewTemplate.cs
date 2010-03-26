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
using System.Globalization;

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
            tbl.Width = table.Width;
            container.Controls.Add(tbl);
            foreach (TableRow tr in table.Rows)
            {
                TableRow tblr = new TableRow();
                tblr.HorizontalAlign = tr.HorizontalAlign;
                tbl.Rows.Add(tblr);
                foreach (TableCell tc in tr.Cells)
                {
                    TableCell tblc = new TableCell();
                    tblc.HorizontalAlign = tc.HorizontalAlign;
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
                        else if (control.GetType() == typeof(TextBox))
                        {
                            TextBox textbox = new TextBox();
                            TextBox txt = (TextBox)control;
                            textbox.AccessKey = txt.AccessKey;
                            textbox.AppRelativeTemplateSourceDirectory = txt.AppRelativeTemplateSourceDirectory;
                            textbox.AutoCompleteType = txt.AutoCompleteType;
                            textbox.AutoPostBack = txt.AutoPostBack;
                            textbox.BackColor = txt.BackColor;
                            textbox.BorderColor = txt.BorderColor;
                            textbox.BorderStyle = txt.BorderStyle;
                            textbox.BorderWidth = txt.BorderWidth;
                            textbox.CausesValidation = txt.CausesValidation;
                            textbox.Columns = txt.Columns;
                            textbox.CssClass = txt.CssClass;
                            textbox.Enabled = txt.Enabled;
                            textbox.EnableTheming = txt.EnableTheming;
                            textbox.EnableViewState = txt.EnableViewState;
                            textbox.ForeColor = txt.ForeColor;
                            textbox.Height = txt.Height;
                            textbox.ID = txt.ID;
                            textbox.MaxLength = txt.MaxLength;
                            textbox.ReadOnly = txt.ReadOnly;
                            textbox.Rows = txt.Rows;
                            textbox.SkinID = txt.SkinID;
                            textbox.TabIndex = txt.TabIndex;
                            textbox.TemplateControl = txt.TemplateControl;
                            textbox.Text = txt.Text;
                            textbox.TextMode = txt.TextMode;
                            textbox.ToolTip = txt.ToolTip;
                            textbox.ValidationGroup = txt.ValidationGroup;
                            textbox.Visible = txt.Visible;
                            textbox.Width = txt.Width;
                            textbox.Wrap = txt.Wrap;
                            if (textbox.Text.StartsWith("DataBind:"))
                            {
                                textbox.DataBinding += new EventHandler(txt_DataBinding);
                            }
                            tblc.Controls.Add(textbox);
                        }
                        else if (control.GetType() == typeof(Image))
                        {                            
                            Image image = new Image();
                            Image img = (Image)control;
                            image.AccessKey = img.AccessKey;
                            image.AlternateText = img.AlternateText;
                            image.AppRelativeTemplateSourceDirectory = img.AppRelativeTemplateSourceDirectory;
                            image.BackColor = img.BackColor;
                            image.BorderColor = img.BorderColor;
                            image.BorderStyle = img.BorderStyle;
                            image.BorderWidth = img.BorderWidth;
                            image.CssClass = img.CssClass;
                            image.DescriptionUrl = img.DescriptionUrl;
                            image.EnableTheming = img.EnableTheming;
                            image.EnableViewState = img.EnableViewState;
                            image.ForeColor = img.ForeColor;
                            image.GenerateEmptyAlternateText = img.GenerateEmptyAlternateText;
                            image.Height = img.Height;
                            image.ID = img.ID;
                            image.ImageAlign = img.ImageAlign;
                            image.ImageUrl = img.ImageUrl;
                            image.SkinID = img.SkinID;
                            image.TabIndex = img.TabIndex;
                            image.TemplateControl = img.TemplateControl;
                            image.ToolTip = img.ToolTip;
                            image.Visible = img.Visible;
                            image.Width = img.Width;
                            if (image.ImageUrl.StartsWith("DataBind:"))
                            {
                                image.DataBinding += new EventHandler(img_DataBinding);                                
                            }
                            tblc.Controls.Add(image);
                        }
                        else if (control.GetType() == typeof(LinkButton))
                        {
                            LinkButton linkButton = new LinkButton();
                            LinkButton lbtn = (LinkButton)control;
                            linkButton.AccessKey = lbtn.AccessKey;
                            linkButton.AppRelativeTemplateSourceDirectory = lbtn.AppRelativeTemplateSourceDirectory;
                            linkButton.BackColor = lbtn.BackColor;
                            linkButton.BorderColor = lbtn.BorderColor;
                            linkButton.BorderStyle = lbtn.BorderStyle;
                            linkButton.BorderWidth = lbtn.BorderWidth;
                            linkButton.CausesValidation = lbtn.CausesValidation;
                            linkButton.CommandArgument = lbtn.CommandArgument;
                            linkButton.CommandName = lbtn.CommandName;
                            linkButton.CssClass = lbtn.CssClass;
                            linkButton.Enabled = lbtn.Enabled;
                            linkButton.EnableTheming = lbtn.EnableTheming;
                            linkButton.EnableViewState = lbtn.EnableViewState;
                            linkButton.ForeColor = lbtn.ForeColor;
                            linkButton.Height = lbtn.Height;
                            linkButton.ID = lbtn.ID;                            
                            linkButton.PostBackUrl = lbtn.PostBackUrl;
                            linkButton.SkinID = lbtn.SkinID;
                            linkButton.TabIndex = lbtn.TabIndex;
                            linkButton.TemplateControl = lbtn.TemplateControl;
                            linkButton.Text = lbtn.Text;
                            linkButton.ToolTip = lbtn.ToolTip;
                            linkButton.ValidationGroup = lbtn.ValidationGroup;
                            linkButton.Visible = lbtn.Visible;
                            linkButton.Width = lbtn.Width;
                            if (linkButton.PostBackUrl.StartsWith("DataBind:"))
                            {
                                linkButton.DataBinding += new EventHandler(lbtn_DataBinding);
                            }
                            tblc.Controls.Add(linkButton);
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
                    catch{}
                }
            }
            else if (data[1].Equals("DateType"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value){
                    try
                    {
                        DateTime d = (DateTime)dataValue;
                        lbl.Text = d.ToShortDateString();
                    }
                    catch
                    {
                    }
                }
            }
            else if (data[1].Equals("CountryType"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    try
                    {
                        RegionInfo ri = new RegionInfo("" + dataValue);
                        lbl.Text = ri.NativeName;
                    }
                    catch{}
                }
            }
            else if (data[1].Equals("Currency"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    try
                    {
                        lbl.Text = "€ " + dataValue;
                    }
                    catch { }
                }
            }
            else if (data[1].Equals("Area"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    try
                    {
                        lbl.Text = "" + dataValue + " m²";
                    }
                    catch { }
                }
            }            
        }

        void txt_DataBinding(object sender, EventArgs e)
        {
            TextBox txt = (TextBox)sender;
            string[] data = txt.Text.Split(':');
            GridViewRow container = (GridViewRow)txt.NamingContainer;
            if (data[1].Equals("Normal"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    txt.Text = dataValue.ToString();
                }
            }            
        }

        void img_DataBinding(object sender, EventArgs e)
        {
            Image img = (Image)sender;
            string[] data = img.ImageUrl.Split(':');
            GridViewRow container = (GridViewRow)img.NamingContainer;
            if (data[1].Equals("Normal"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    img.ImageUrl = dataValue.ToString();                    
                }
            }
            if (data[1].Equals("Boolean"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    if (("" + dataValue).Equals("0"))
                    {                        
                        img.ImageUrl = "Images/cancel.png";
                    }
                    else
                    {
                        img.ImageUrl = "Images/ok.png";
                    }                    
                }
            }
        }

        void lbtn_DataBinding(object sender, EventArgs e)
        {
            LinkButton lbtn = (LinkButton)sender;
            string[] data = lbtn.PostBackUrl.Split(':');
            GridViewRow container = (GridViewRow)lbtn.NamingContainer;
            if (data[1].Equals("Normal"))
            {
                object dataValue = DataBinder.Eval(container.DataItem, data[2]);
                if (dataValue != DBNull.Value)
                {
                    lbtn.PostBackUrl = dataValue.ToString();
                }
            }
        }
    }
}
