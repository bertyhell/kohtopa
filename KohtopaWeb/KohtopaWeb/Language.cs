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

        public static void read(String language)
        {
            if (languages[language] == null)
            {
                try
                {
                    Hashtable strings = new Hashtable();
                    XmlReader reader = new XmlTextReader("language_" + language + ".xml");
                    while (reader.Read())
                    {
                        if (reader.NodeType == XmlNodeType.Element && reader.Name == "entry")
                        {
                            String name = reader.GetAttribute("name");
                            if (reader.Read())
                            {
                                strings.Add(name, reader.Value);
                            }
                        }
                    }
                    languages.Add(language, strings);
                    reader.Close();
                }
                catch (Exception e)
                {}
            }
        }

        public static void add(String key, String value, String language)
        {
            Hashtable h = (Hashtable)languages[languages];
            if (h == null)
            {
                h = new Hashtable();
                languages.Add(language, h);
            }            
            h.Add(key, value);
        }

        public static String getString(String key,String language)
        {
            Hashtable h = (Hashtable)languages[language];
            if (h == null)
            {
                Language.read(language);
                h = (Hashtable)languages[language];
            }
            return (String)h[key];            
        }

        public static void write(String language)
        {
            XmlTextWriter writer = new XmlTextWriter("language_" + language + ".xml" , System.Text.Encoding.UTF8);
            writer.Formatting = Formatting.Indented;            
            writer.Indentation = 4;
            writer.IndentChar = ' ';
            writer.WriteStartDocument(false);            
            writer.WriteStartElement("Language");            
            writer.WriteAttributeString("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");            
            writer.WriteAttributeString("xsi:noNamespaceSchemaLocation", "language.xsd");
            Hashtable h = (Hashtable)languages[language];
            foreach (String s in h.Keys)
            {
                writer.WriteStartElement("entry");
                writer.WriteAttributeString("name", s);
                writer.WriteValue((String)h[s]);
                writer.WriteEndElement();
            }            
            writer.WriteEndElement();            
            writer.Close();
        }        
    }
}
