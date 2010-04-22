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
	private static String[] daysOfWeek;
	private static String[] rentableTypes;
	private static String[] windDir;
	private static JComboBox cbbCountry;

	public static void read() {
		try {
			File file = new File("language_EN_English.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("entry");

			countryCodes = new String[250];
			countries = new String[250];
			daysOfWeek = new String[7];
			rentableTypes = new String[100]; //max 100 different types
			windDir = new String[8];
			int i = 0;
			int j = 0;
			int k = 0;
			int m = 0;
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node entryNode = nodeLst.item(s);
				String name = entryNode.getAttributes().item(0).getNodeValue();
				if (name.startsWith("lstCntry")) {
					countryCodes[i] = name.substring(8);
					countries[i] = entryNode.getTextContent();
					i++;
				} else if (name.startsWith("lstWeek")) {
					daysOfWeek[j] = entryNode.getTextContent();
					j++;
				}else if (name.startsWith("lstRentableType")){
					rentableTypes[k] = entryNode.getTextContent();
					k++;
				}else if (name.startsWith("lstWindDir")){
					rentableTypes[m] = entryNode.getTextContent();
					m++;
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

	public static int getIndexByCountryCode(String code) throws CountryNotFoundException {
		int i = 0;
		while (i < countryCodes.length) {
			if (countryCodes[i] != null && countryCodes[i].equals(code)) {
				return i;
			}
			i++;
		}
		throw new CountryNotFoundException(Language.getString("errCountryCodeNotFound") + code);
	}

	public static JComboBox getCountryComboBox() {
		return cbbCountry;
	}

	public static void add(String key, String value) {
		strings.put(key, value);
	}

	public static String getString(String key) {
		return strings.get(key);
	}

	public static String[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public static String[] getRentableTypes() {
		return rentableTypes;
	}

	public static String[] getWindDir() {
		return windDir;
	}


}
