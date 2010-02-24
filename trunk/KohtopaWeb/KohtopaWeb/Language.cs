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
        private static Hashtable strings = new Hashtable();
        private static String current_path;

        public static void read(String path)
        {
            Language.current_path = path;
            try
            {                                
                XmlReader reader = new XmlTextReader(path);                
                while (reader.Read())
                {
                    if (reader.NodeType == XmlNodeType.Element && reader.Name == "entry")
                    {
                        String name = reader.GetAttribute("name");
                        if (reader.Read())
                        {
                            Language.add(name, reader.Value);
                        }                        
                    }
                }
            }
            catch (Exception e){
                string test = e.Message;
            }
        }

        public static void add(String key, String value)
        {
            strings.Add(key, value);
        }

        public static String getString(String key)
        {
            return (String)strings[key];
        }

        public static void write(String path)
        {
            XmlTextWriter writer = new XmlTextWriter(path, System.Text.Encoding.UTF8);
            writer.Formatting = Formatting.Indented;            
            writer.Indentation = 4;
            writer.IndentChar = ' ';
            writer.WriteStartDocument(false);            
            writer.WriteStartElement("competitie");            
            writer.WriteAttributeString("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");            
            writer.WriteAttributeString("xsi:noNamespaceSchemaLocation", "language.xsd");
            foreach (String s in strings.Keys)
            {
                writer.WriteStartElement("entry");
                writer.WriteAttributeString("name", s);
                writer.WriteValue((String)strings[s]);
                writer.WriteEndElement();
            }            
            writer.WriteEndElement();            
            writer.Close();
        }

        public static void write()
        {
            Language.write(current_path);
        }
    }
}
