package Language;

import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Language {

    private static HashMap<String, String> strings = new HashMap<String, String>();

    public static void read() {
        try {
            File file = new File("language_EN.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("entry");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node entryNode = nodeLst.item(s);
                Language.add(entryNode.getAttributes().item(0).getNodeValue(), entryNode.getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void add(String key, String value) {
        strings.put(key, value);
    }

    public static String getString(String key) {
        return strings.get(key);
    }
}
