using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;

namespace KohtopaWeb
{
    public partial class FloorPlan : System.Web.UI.UserControl
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {

                XmlTextReader textReader = new XmlTextReader(Server.MapPath("floor.xml"));
                
                XmlDocument xd = new XmlDocument();
                xd.Load(textReader);

                XmlNode basenode = xd.DocumentElement;

                basenode.Normalize();

                foreach(XmlNode n in basenode.ChildNodes) 
                {
                    if(n.Name == "width") 
                    {
                        
                        floorplan.Width = int.Parse(n.FirstChild.Value);
                    }
                    else if(n.Name == "height") 
                    {
                        floorplan.Height = int.Parse(n.FirstChild.Value);
                    }
                    else if(n.Name == "namedpolygons")
                    {
                        foreach (XmlNode polygon in n.ChildNodes)
                        {
                            readHotspot(polygon.ChildNodes);
                        }
                    }
                }
                
            }
        }

        void readHotspot(XmlNodeList nodes)
        {
            // add new polygon

            PolygonHotSpot hs = new PolygonHotSpot();
            string coords = "";

            foreach(XmlNode n in nodes) 
            {
                if(n.Name == "name")
                {                    
                    string name = n.FirstChild.Value;
                    hs.PostBackValue = name;
                    hs.AlternateText = name;
                    hs.HotSpotMode = HotSpotMode.Navigate;
                    hs.NavigateUrl = "~/" + name;
                }
                else if(n.Name == "points")
                {
                    foreach(XmlNode child in n.ChildNodes) 
                    {
                        foreach (XmlNode point in child.ChildNodes)
                        {
                            coords += point.FirstChild.Value + ",";

                        }
                    }
                }
            }
            hs.Coordinates = coords.Substring(0,coords.Length-1);
            floorplan.HotSpots.Add(hs);

        }
    }
}

