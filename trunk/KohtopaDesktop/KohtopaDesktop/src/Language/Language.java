package Language;

import data.ProgramSettings;
import gui.Logger;
import gui.Main;
import java.awt.Dimension;
import java.util.HashMap;
import java.io.File;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
	private static String[] monthsOfYear;
	private static Vector<String> rentableTypes;
	private static String[] windDir;
	private static JComboBox cbbCountry;
	private static String language;

	public static void read() {
		try {
			Logger.logger.info("Reading language file: " + "language_" + ProgramSettings.getLanguage() + ".xml");
			language = ProgramSettings.getLanguage();
			File file = new File("language_" + ProgramSettings.getLanguage() + ".xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("entry");

			countryCodes = new String[250];
			countries = new String[250];
			daysOfWeek = new String[7];
			monthsOfYear = new String[12];
			rentableTypes = new Vector<String>();
			windDir = new String[8];
			int i = 0;
			int j = 0;
			int m = 0;
			int n = 0;
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
				} else if (name.startsWith("lstRentableType")) {
					rentableTypes.add(entryNode.getTextContent());
				} else if (name.startsWith("lstWindowDir")) {
					windDir[m] = entryNode.getTextContent();
					m++;
				} else if (name.startsWith("lstMonth")) {
					monthsOfYear[n] = entryNode.getTextContent();
					n++;
				}
				Language.add(name, entryNode.getTextContent());
			}
			cbbCountry = new JComboBox(Language.getCountries());
			//cbbCountry.setRenderer(new CountryCellRenderer());
			cbbCountry.setPreferredSize(new Dimension(20, 23));

			for(String type : rentableTypes){
				Logger.logger.info("type: " + type);
			}
		} catch (Exception ex) {
			Logger.logger.error("Exception in read ProgramSettings: " + ex.getMessage());
			Logger.logger.debug("stacktrace: ", ex);
		}
	}

	public static String[] getCountries() {
		return countries;
	}

	public static String[] getCountryCodes() {
		return countryCodes;
	}

	public static String getCountryCodeByIndex(int i) {
		return countryCodes[i];
	}

	public static String getCountryByCode(String code) {
		return strings.get("lstCntry" + code);
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

	public static Object[] getRentableTypes() {
		return rentableTypes.toArray();
	}

	public static String getRentableType(int index){
		return rentableTypes.get(index);
	}

	public static String[] getWindDir() {
		return windDir; //TODO 000 replace by hashmap instead of array
	}

	public static String getWindDir(String dir) {
		if (dir == null) {
			//JOptionPane.showMessageDialog(Main.getInstance(), "Failed to find window direction", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			return windDir[0];
		} else if (dir.toLowerCase().equals("n")) {
			return windDir[0];
		} else if (dir.toLowerCase().equals("ne")) {
			return windDir[1];
		} else if (dir.toLowerCase().equals("e")) {
			return windDir[2];
		} else if (dir.toLowerCase().equals("se")) {
			return windDir[3];
		} else if (dir.toLowerCase().equals("s")) {
			return windDir[4];
		} else if (dir.toLowerCase().equals("sw")) {
			return windDir[5];
		} else if (dir.toLowerCase().equals("w")) {
			return windDir[6];
		} else if (dir.toLowerCase().equals("nw")) {
			return windDir[7];
		} else {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to find window direction", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			return windDir[0];
		}
	}

	public static String getWindDirCodeFromIndex(int index) {
		if (index == 0) {
			return "n";
		} else if (index == 1) {
			return "ne";
		} else if (index == 2) {
			return "e";
		} else if (index == 3) {
			return "se";
		} else if (index == 4) {
			return "s";
		} else if (index == 5) {
			return "sw";
		} else if (index == 6) {
			return "w";
		} else if (index == 7) {
			return "nw";
		} else {
			return null;
		}
	}

	public static String[] getMonthsOfYear() {
		return monthsOfYear;
	}

	public static String getLanguage() {
		return language;
	}
}
