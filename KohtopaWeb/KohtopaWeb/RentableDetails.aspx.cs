using System;
using System.Collections;
using System.Configuration;
using System.Data;
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
    public partial class RentableDetails : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            try
            {
                Rentable rentable = DataConnector.getRentable(Int32.Parse(Request.Params["id"]));
                string language = "" + Session["Language"];
                lblFreeFromDescription.Text = Language.getstring("FreeFrom",language);
                //lblFreeFromValue.Text = rentable.
                lblTypeDescription.Text = Language.getstring("Type", language);
                lblTypeValue.Text = Language.getstring(DataConnector.rentableTypes.Split(';')[rentable.Type], language);
                lblAddressDescription.Text = Language.getstring("Address", language);
                lblAddressValue.Text = rentable.Building.Address.ToString();
                lblPriceDescription.Text = Language.getstring("Price", language);
                lblPriceValue.Text = "€ " + rentable.Price;
                lblFloorDescription.Text = Language.getstring("Floor",language);
                lblFloorValue.Text = "" + rentable.Floor;
                lblAreaDescription.Text = Language.getstring("Area", language);
                lblAreaValue.Text = "" + rentable.Area;
                lblInternetDescription.Text = Language.getstring("Internet", language);
                if (rentable.Internet)
                {
                    imgInternet.ImageUrl = "Images/ok.png";
                }
                else
                {
                    imgInternet.ImageUrl = "Images/cancel.png";
                }
                lblCableDescription.Text = Language.getstring("Cable", language);
                if (rentable.Cable)
                {
                    imgCable.ImageUrl = "Images/ok.png";
                }
                else
                {
                    imgCable.ImageUrl = "Image/cancel.png";
                }
                lblOutletCountDescription.Text = Language.getstring("OutletCount", language);
                lblOutletCountValue.Text = "" + rentable.OutletCount;
                lblWindowDirectionDescription.Text = Language.getstring("WindowDirection", language);
                lblWindowDirectionValue.Text = rentable.WindowDirection;
                lblWindowAreaDescription.Text = Language.getstring("WindowArea", language);
                lblWindowAreaValue.Text = "" + rentable.WindowArea;
                lblDescriptionDescription.Text = Language.getstring("Description", language);
                lblDescriptionValue.Text = rentable.Description;
            }
            catch { }
        }
    }
}
