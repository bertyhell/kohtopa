using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

namespace KohtopaWeb
{
    public partial class Invoices : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack || (bool)Session["LanguageChanged"])
            {
                if (!IsPostBack)
                {
                    RefeshData();
                }
                
            }
        }

        protected void RefeshData()
        {
            //invoices
            DataTable invoiceTable = DataConnector.getInvoices(((Person)Session["user"]).PersonId);
            tblInvoice.DataSource = invoiceTable;
            tblInvoice.DataBind();
        }
    }
}
