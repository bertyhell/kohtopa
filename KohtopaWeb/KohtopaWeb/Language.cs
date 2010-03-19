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
using System.Collections;
using System.Xml;

namespace KohtopaWeb
{
    public class Language
    {
        private static Hashtable languages = new Hashtable();
        private static DataTable dtLanguages = new DataTable("languages");

        public static void read()
        {
            readLanguages();
            foreach (DataRow dr in dtLanguages.Rows)
            {
                read((string)dr["id"]);
            }
        }

        private static void read(string language)
        {
            if (languages[language] == null)
            {
                try
                {
                    Hashtable strings = new Hashtable();
                    XmlReader reader = new XmlTextReader("language_" + language + ".xml");
                    try
                    {
                        while (reader.Read())
                        {
                            if (reader.NodeType == XmlNodeType.Element && reader.Name == "entry")
                            {
                                string name = reader.GetAttribute("name");
                                if (reader.Read())
                                {
                                    strings.Add(name, reader.Value);
                                }
                            }
                        }
                        languages.Add(language, strings);
                    }
                    finally
                    {
                        reader.Close();
                    }                    
                }
                catch (Exception e)
                {}
            }
        }

        public static void add(string key, string value, string language)
        {
            Hashtable h = (Hashtable)languages[languages];
            if (h == null)
            {
                h = new Hashtable();
                languages.Add(language, h);
            }            
            h.Add(key, value);
        }

        public static string getstring(string key,string language)
        {
            Hashtable h = (Hashtable)languages[language];            
            return (string)h[key];            
        }

        public static void write(string language)
        {
            try
            {
                XmlTextWriter writer = new XmlTextWriter("language_" + language + ".xml", System.Text.Encoding.UTF8);
                try
                {
                    writer.Formatting = Formatting.Indented;
                    writer.Indentation = 4;
                    writer.IndentChar = ' ';
                    writer.WriteStartDocument(false);
                    writer.WriteStartElement("Language");
                    writer.WriteAttributeString("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                    writer.WriteAttributeString("xsi:noNamespaceSchemaLocation", "language.xsd");
                    Hashtable h = (Hashtable)languages[language];
                    foreach (string s in h.Keys)
                    {
                        writer.WriteStartElement("entry");
                        writer.WriteAttributeString("name", s);
                        writer.WriteValue((string)h[s]);
                        writer.WriteEndElement();
                    }
                    writer.WriteEndElement();
                }
                finally
                {
                    writer.Close();
                }
            }
            catch (Exception exc){}
        }

        private static void readLanguages()
        {
            dtLanguages.Clear();
            dtLanguages.Columns.Add("id");
            dtLanguages.Columns.Add("language");            
            try
            {                
                XmlReader reader = new XmlTextReader("languages.xml");
                try
                {
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element && reader.Name == "entry")
                        {
                            string id = reader.GetAttribute("id");
                            if (reader.Read())
                            {
                                DataRow dr = dtLanguages.NewRow();
                                dr["id"] = id;
                                dr["language"] = reader.Value;
                                dtLanguages.Rows.Add(dr);
                            }
                        }
                    }                    
                }
                finally
                {
                    reader.Close();
                }
            }
            catch (Exception e)
            { }            
        }

        public static DataTable getLanguages()
        {
            return dtLanguages;
        }
    }
}
