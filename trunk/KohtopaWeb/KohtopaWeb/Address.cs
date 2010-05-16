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
    //a class to represent an address from the database

    public class Address
    {
        private int addressId;
        private string street,streetNumber, zipcode, city, country;

        public string Country
        {
            get { return country; }
            set { country = value; }
        }

        public string City
        {
            get { return city; }
            set { city = value; }
        }

        public string Zipcode
        {
            get { return zipcode; }
            set { zipcode = value; }
        }

        public string Street
        {
            get { return street; }
            set { street = value; }
        }

        public string StreetNumber
        {
            get { return streetNumber; }
            set { streetNumber = value; }
        }

        public int AddressId
        {
            get { return addressId; }
            set { addressId = value; }
        }

        public override string ToString()
        {
            return street + " " + streetNumber + ", " + zipcode + " " + city + ", " + country;
        }
    }
}
