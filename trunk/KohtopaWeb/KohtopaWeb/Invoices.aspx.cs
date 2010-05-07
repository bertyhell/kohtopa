using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.IO;

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

            Session["invoices"] = invoiceTable; 
            tblInvoice.DataSource = invoiceTable;
            tblInvoice.DataBind();
        }

        protected void SendInvoice(object sender, GridViewCommandEventArgs e)
        {
            
            DataTable t = (DataTable)Session["invoices"];
            

            int index = int.Parse(e.CommandArgument.ToString());

            FileStream fs = new FileStream("C:\\Users\\Jelle\\Desktop\\kohtopa\\database\\database.pdf", FileMode.OpenOrCreate, 
              FileAccess.Read);

            byte[] data = new byte[fs.Length];            
            fs.Read(data,0,(int)fs.Length);
           
            fs.Close();

            t.Rows[index][3] = data;
            t.AcceptChanges();
            
            
            byte[] MyData;
            if (!t.Rows[index][3].GetType().Equals(DBNull.Value))
            {
                MyData = (byte[])t.Rows[index][3];

                tmp.Text = "ok";


                DateTime dt = (DateTime)t.Rows[index][1];
                string filename = dt.ToShortDateString().Replace("/", "_")+".pdf";
                Response.Buffer = true;
                Response.ContentType = "application/pdf";
                Response.AddHeader("Content-Disposition", "attachment;filename=" + filename);// + t.Rows[index][1] + ".pdf");
                
                Response.BinaryWrite(MyData);
                Response.End();
            }
            else
            {
                tmp.Text = "error";
            }
        }
    }
}
