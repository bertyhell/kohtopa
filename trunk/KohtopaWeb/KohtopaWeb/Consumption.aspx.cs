using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

namespace KohtopaWeb
{
    public partial class Consumption : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Session["LanguageChanged"] = false;
            string language = "" + Session["Language"];

            gasLbl.Text = Language.getstring("Gas", "" + Session["language"]);
            gasValidator.Text = Language.getstring("InvalidValue", "" + Session["language"]);

            elLbl.Text = Language.getstring("Electricity", "" + Session["language"]);
            elValidator.Text = Language.getstring("InvalidValue", "" + Session["language"]);

            waterLbl.Text = Language.getstring("Water", "" + Session["language"]);
            waterValidator.Text = Language.getstring("InvalidValue", "" + Session["language"]);

            submitBtn.Text = Language.getstring("Submit", "" + Session["language"]);

            if (!IsPostBack)
            {
                RefeshData();
            }

        }

        protected void RefeshData()
        {
            // consumption
            DataTable consumptionTable = DataConnector.getConsumptions(((Person)Session["user"]).PersonId);
            Object[] data = consumptionTable.Rows[0].ItemArray;


            consumption.DataSource = consumptionTable;
            consumption.DataBind();


            gasValidator.ValueToCompare = data[0].ToString();
            gasValidator.Text = gasValidator.Text + "(min: " + data[0] + ")";

            waterValidator.ValueToCompare = data[1].ToString();
            waterValidator.Text = waterValidator.Text + "(min: " + data[1] + ")";

            elValidator.ValueToCompare = data[2].ToString();
            elValidator.Text = elValidator.Text + "(min: " + data[2] + ")";
        }

        protected void submit(object sender, EventArgs e)
        {
            // doet nog niks
        }
    }

}
