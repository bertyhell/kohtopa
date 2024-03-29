﻿using System;
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
using System.Globalization;
using System.Net;
using System.IO;

namespace KohtopaWeb
{
    public class GoogleGeocoding
    {
        //a class that uses google maps to convert a address in a pair of latitude and longitude.

        private static string googleKey = ConfigurationManager.AppSettings["GoogleMapsKey"];

        private static double deg2rad(double deg)
        {
            return (deg * Math.PI / 180.0);
        }

        private static double rad2deg(double rad)
        {
            return (rad / Math.PI * 180.0);
        }

        public static double getDistance(Pair p1, Pair p2)
        {            
            double theta = (double)p1.First - (double)p2.First;
            double dist = Math.Sin(deg2rad((double)p1.Second)) * Math.Sin(deg2rad((double)p2.Second)) + Math.Cos(deg2rad((double)p1.Second)) * Math.Cos(deg2rad((double)p2.Second)) * Math.Cos(deg2rad(theta));
            dist = Math.Acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515 * 1.609344;
            return dist;                        
        }

        public static Pair getLongLat(string address)
        {
            Pair p = null;
            try
            {
                string xml = GetXml(address);
                int start = xml.IndexOf("<coordinates>") + 13;
                int end = xml.IndexOf("</coordinates>");
                string[] values = xml.Substring(start, end - start).Split(',');
                double longtitude = Double.Parse(values[0], CultureInfo.InvariantCulture);
                double latitude = Double.Parse(values[1], CultureInfo.InvariantCulture);
                p = new Pair(longtitude, latitude);
            }
            catch{}
            return p;
        }

        private static string GetXml(string address)
        {            
            string url = string.Format("http://maps.google.com/maps/geo?output=xml&q={0}&key={1}", HttpUtility.UrlEncode(address), googleKey);

            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            using (Stream stream = request.GetResponse().GetResponseStream())
            {
                using (StreamReader reader = new StreamReader(stream))
                {
                    return reader.ReadToEnd();
                }
            }
        }
    }

    public class GoogleAddress
    {
        private string address;
        private Pair longLat;

        public GoogleAddress(string address, Pair longLat)
        {
            this.address = address;
            this.longLat = longLat;
        }

        public GoogleAddress() {}

        public string Address
        {
            get
            {
                return address;
            }
            set
            {
                address = value;
            }
        }

        public Pair LongLat
        {
            get
            {
                return longLat;
            }
            set
            {
                longLat = value;
            }
        }

        public override string ToString()
        {
            return address;
        }
    }
}
