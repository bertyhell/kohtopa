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
    public class Rentable
    {
        //a class to represent a rentable from the database
        int rentableId,type, outletCount, floor;
        bool internet, cable, rented;
        double area, windowArea, price;
        Person owner;
        string description, windowDirection;
        Building building;
        DateTime freeFrom;        

        public DateTime FreeFrom
        {
            get { return freeFrom; }
            set { freeFrom = value; }
        }

        public string WindowDirection
        {
            get { return windowDirection; }
            set { windowDirection = value; }
        }

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public Person Owner
        {
            get { return owner; }
            set { owner = value; }
        }

        public double Price
        {
            get { return price; }
            set { price = value; }
        }

        public double WindowArea
        {
            get { return windowArea; }
            set { windowArea = value; }
        }

        public double Area
        {
            get { return area; }
            set { area = value; }
        }

        public bool Rented
        {
            get { return rented; }
            set { rented = value; }
        }

        public bool Cable
        {
            get { return cable; }
            set { cable = value; }
        }

        public bool Internet
        {
            get { return internet; }
            set { internet = value; }
        }

        public int Floor
        {
            get { return floor; }
            set { floor = value; }
        }

        public int OutletCount
        {
            get { return outletCount; }
            set { outletCount = value; }
        }

        public int Type
        {
            get { return type; }
            set { type = value; }
        }

        public Building Building
        {
            get { return building; }
            set { building = value; }
        }

        public int RentableId
        {
            get { return rentableId; }
            set { rentableId = value; }
        }                               
    }
}
