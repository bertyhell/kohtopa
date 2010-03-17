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
	private static final String[] countryCodes = {"AD", "AE", "AF", "AG", "AI", "AL", "AM", "AN", "AO", "AQ", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BV", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "gs", "GT", "GU", "GW", "GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SK", "SL", "SM", "SN", "SO", "SR", "ST", "SV", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", "ZA", "ZM", "ZW"};
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
			countries = new String[countryCodes.length];
			int i = 0;
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node entryNode = nodeLst.item(s);
				String name = entryNode.getAttributes().item(0).getNodeValue();
				if (name.startsWith("cntry")) {
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
