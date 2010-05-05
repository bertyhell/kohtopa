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
    public class Consumption
    {
        //CONSUMPTIONID  RENTABLEID  GAS  WATER  ELECTRICITY  DATE_CONSUMPTION 

        private int consumptionid,rentableid,gas,water,electricity;
        private DateTime date_consumption;

        public int ConsumptionId
        {
            get { return consumptionid; }
            set { consumptionid = value; }
        }

        public int RentableId
        {
            get { return rentableid; }
            set { rentableid = value; }
        }

        public int Gas
        {
            get { return gas; }
            set { gas = value; }
        }

        public int Water
        {
            get { return water; }
            set { water = value; }
        }

        public int Electricity
        {
            get { return electricity; }
            set { electricity = value; }
        }

        public DateTime Date
        {
            get { return date_consumption; }
            set { date_consumption = value; }
        }

    }
}
