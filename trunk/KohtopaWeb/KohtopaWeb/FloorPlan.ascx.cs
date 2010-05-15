using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml;
using System.IO;
using System.Data;

namespace KohtopaWeb
{
    public partial class FloorPlan : System.Web.UI.UserControl
    {
        private int buildingID;
        private int floor;

        public int BuildingID { 
            get { return buildingID;  }
            set { buildingID = value; }
        }
        public int Floor
        {
            get { return floor; }
            set { floor = value; }
        }
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                try
                {

                    byte[] xmlFile = DataConnector.getFloorPlan(buildingID, floor);
                    MemoryStream input = null;
                    // geen gevonden => doe niets
                    if (xmlFile == null)
                    {
                        //return;
                    }
                    else
                    {
                        input = new MemoryStream(xmlFile);
                    }

                    // tijdelijk
                    FileStream fs = new FileStream(Server.MapPath("floor.xml"), FileMode.OpenOrCreate,
                      FileAccess.Read);

                    byte[] data = new byte[fs.Length];
                    fs.Read(data, 0, (int)fs.Length);

                    fs.Close();
                    MemoryStream inputtmp = new MemoryStream(data);

                    XmlReader reader = new XmlTextReader(inputtmp);


                    //XmlTextReader textReader = new XmlTextReader(Server.MapPath("floor.xml"));
                    XmlDocument xd = new XmlDocument();
                    xd.Load(reader);
                    XmlNode basenode = xd.DocumentElement;
                    basenode.Normalize();

                    foreach (XmlNode n in basenode.ChildNodes)
                    {
                        if (n.Name == "width")
                        {

                            floorplan.Width = int.Parse(n.FirstChild.Value);
                        }
                        else if (n.Name == "height")
                        {
                            floorplan.Height = int.Parse(n.FirstChild.Value);
                        }
                        else if (n.Name == "image")
                        {
                            String name = n.ChildNodes[0].FirstChild.Value;
                            int id = int.Parse(n.ChildNodes[1].FirstChild.Value);

                            // get image data

                            floorplan.AlternateText = name;
                            
                            //floorplan.ImageUrl = "~/Images/ico.png";
                            floorplan.ImageUrl = "~/ShowPicture.aspx?imageId=" + id;
                        }
                        else if (n.Name == "namedpolygons")
                        {
                            foreach (XmlNode polygon in n.ChildNodes)
                            {
                                readHotspot(polygon.ChildNodes);
                            }
                        }
                    }
                }
                catch (Exception exc)
                {
                    Logger.log(Server, exc.Message);
                }                
            }
        }

        void readHotspot(XmlNodeList nodes)
        {
            try
            {
                // add new polygon            
                PolygonHotSpot hs = new PolygonHotSpot();
                string coords = "";

                foreach (XmlNode n in nodes)
                {
                    if (n.Name == "name")
                    {
                        string name = n.FirstChild.Value;
                        hs.PostBackValue = name;
                        hs.AlternateText = name;
                        hs.HotSpotMode = HotSpotMode.Navigate;
                        hs.NavigateUrl = "~/RentableDetails.aspx?id=" + name;
                    }
                    else if (n.Name == "points")
                    {
                        foreach (XmlNode child in n.ChildNodes)
                        {
                            foreach (XmlNode point in child.ChildNodes)
                            {
                                coords += point.FirstChild.Value + ",";

                            }
                        }
                    }
                }
                hs.Coordinates = coords.Substring(0, coords.Length - 1);
                floorplan.HotSpots.Add(hs);
            }
            catch (Exception exc)
            {
                Logger.log(Server, exc.Message);
            }
        }
    }
}

