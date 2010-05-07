using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

namespace KohtopaWeb
{
    public partial class Consumptions : System.Web.UI.Page
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
            submitBtn.CausesValidation = true;

            if (!IsPostBack)
            {
                RefeshData();
            }

        }

        protected void RefeshData()
        {
            // consumption
            DataTable consumptionTable = DataConnector.getConsumptions(((Person)Session["user"]).PersonId);

            if (consumptionTable.Rows.Count == 0)
            {
                gasValidator.ValueToCompare = "0";
                waterValidator.ValueToCompare = "0";
                elValidator.ValueToCompare = "0";

                return;
            }
            Object[] data = consumptionTable.Rows[0].ItemArray;

            
            consumption.DataSource = consumptionTable;
            consumption.DataBind();


            Rentable r = ((Person)Session["user"]).Rentable;
                insertTable.Visible = r != null;



            gasValidator.ValueToCompare = data[0].ToString();
            gasValidator.Text = gasValidator.Text + "(min: " + data[0] + ")";

            waterValidator.ValueToCompare = data[1].ToString();
            waterValidator.Text = waterValidator.Text + "(min: " + data[1] + ")";

            elValidator.ValueToCompare = data[2].ToString();
            elValidator.Text = elValidator.Text + "(min: " + data[2] + ")";
        }

        protected void submit(object sender, EventArgs e)
        {
            Consumption c = new Consumption();
            Rentable r = ((Person)Session["user"]).Rentable;

            if (r == null) return;
            c.Date = DateTime.Today;
            c.ConsumptionId = 1;
            c.Electricity = int.Parse(el.Text);
            c.Gas = int.Parse(gas.Text);
            c.Water = int.Parse(water.Text);

            DataConnector.insertConsumption(c);

            RefeshData();
        }
    }

}
