package Language;

import java.awt.Dimension;
import java.util.HashMap;
import java.io.File;
import javax.swing.JComboBox;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Language {

	private static HashMap<String, String> strings = new HashMap<String, String>();
	private static String[] countryCodes;
	private static String[] countries;
	private static JComboBox cbbCountry;

	//TODO add extra node to xml: Language > "English"
	public static void read() {
		try {
			File file = new File("language_EN.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("entry");

			countryCodes = new String[250];
			countries = new String[250];
			int i = 0;
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node entryNode = nodeLst.item(s);
				String name = entryNode.getAttributes().item(0).getNodeValue();
				if (name.startsWith("cntry")) {
					countryCodes[i] = name.substring(5,7);
					countries[i] = entryNode.getTextContent();
					i++;
				}
				Language.add(name, entryNode.getTextContent());
			}
			cbbCountry = new JComboBox(Language.getCountries());
			//cbbCountry.setRenderer(new CountryCellRenderer());
			cbbCountry.setPreferredSize(new Dimension(20, 23));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getCountries() {
		return countries;
	}

	public static String[] getCountryCodes() {
		return countryCodes;
	}

	public static String getCountryByIndex(int i) {
		return countryCodes[i];
	}

	public static JComboBox getCountryComboBox() {
		cbbCountry.setSelectedIndex(21);
		return cbbCountry;
	}

	public static void add(String key, String value) {
		strings.put(key, value);
	}

	public static String getString(String key) {
		return strings.get(key);
	}
}
