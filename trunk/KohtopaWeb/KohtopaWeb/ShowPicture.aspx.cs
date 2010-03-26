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
    public partial class ShowPicture : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //get the image id from the url
            try
            {
                int id = Int32.Parse(Request["imageId"]);
                Response.BinaryWrite(DataConnector.getPicture(id));
            }
            catch {}
        }
    }
}
