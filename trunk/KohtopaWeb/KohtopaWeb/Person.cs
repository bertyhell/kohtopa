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
    public class Person
    {
        int personId;
        Address address;
        string roleId, name, firstName, email, telephone, cellphone, username, password;
        Rentable rentable;

        public Rentable Rentable
        {
            get { return rentable; }
            set { rentable = value; }
        }

        public string Password
        {
            get { return password; }
            set { password = value; }
        }

        public string Username
        {
            get { return username; }
            set { username = value; }
        }

        public string Cellphone
        {
            get { return cellphone; }
            set { cellphone = value; }
        }

        public string Telephone
        {
            get { return telephone; }
            set { telephone = value; }
        }

        public string Email
        {
            get { return email; }
            set { email = value; }
        }

        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public string RoleId
        {
            get { return roleId; }
            set { roleId = value; }
        }

        public Address Address
        {
            get { return address; }
            set { address = value; }
        }

        public int PersonId
        {
            get { return personId; }
            set { personId = value; }
        }         
    }
}
