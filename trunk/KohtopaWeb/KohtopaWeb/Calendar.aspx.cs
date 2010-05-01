using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

namespace KohtopaWeb
{
    public partial class Calendar : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                kalender.SelectedDate = DateTime.Today;
                //kalender.TitleStyle.BackColor = System.Drawing.Color.DarkBlue;
                //kalender.TitleStyle.ForeColor = System.Drawing.Color.Snow;
                
                DataTable t = DataConnector.getTasks(((Person)Session["user"]).PersonId);
                   // , kalender.SelectedDate.AddDays(-kalender.SelectedDate.Day)
                   // , kalender.SelectedDate.AddMonths(1).AddDays(-kalender.SelectedDate.Day));
                t.PrimaryKey = new DataColumn[] {t.Columns["start_time"]};

                DataView v = new DataView(t);
                v.Table.Columns.Remove("RENTABLEID");
                Session["tasks"] = v;


            }
        }

        protected void RenderDay(object sender, DayRenderEventArgs e)
        {
            DataView v = (DataView)Session["tasks"];//DataConnector.getTasks(((Person)Session["user"]).PersonId, e.Day.Date, e.Day.Date.AddDays(1));
            
            DateTime dt = e.Day.Date;
            DateTime dn = e.Day.Date.AddDays(1);
            
            //mm/dd/yyyy
            v.RowFilter = "start_time >= #" + dt.ToString("M/dd/yyyy") + "# AND start_time < #" + dn.ToString("M/dd/yyyy") + "#";
            
            
            //string s = String.Format("start_time >= #{0:M/dd/yyyy}# AND start_time <= #{1:M/dd/yyyy}#",dt, dn);
            //DataRow[] rijen = v.Select(s);

            
            
            if (v.Count > 0)
            {
                e.Cell.BackColor = kalender.TitleStyle.BackColor;
                e.Cell.ForeColor = kalender.TitleStyle.ForeColor;
            }
            else
            {
                e.Cell.BackColor = kalender.DayStyle.BackColor;
                e.Cell.ForeColor = kalender.DayStyle.ForeColor;
            }
        }



        protected void SelectDate(object sender, EventArgs e)
        {
            DataView v = (DataView)Session["tasks"];//DataConnector.getTasks(((Person)Session["user"]).PersonId);//, kalender.SelectedDate.Date, DateTime.MaxValue);



            DateTime dt = kalender.SelectedDate.Date;
            DateTime dn = DateTime.MaxValue;

            v.RowFilter = "start_time >= #" + dt.ToString("M/dd/yyyy") + "# AND start_time < #" + dn.ToString("M/dd/yyyy") + "#";
            //string s = String.Format("start_time >= #{0:M/dd/yyyy}# AND start_time <= #{1:M/dd/yyyy}#",dt, dn);
           

            

            data.DataSource = v;
            data.DataBind();
        }

    }
    
}
