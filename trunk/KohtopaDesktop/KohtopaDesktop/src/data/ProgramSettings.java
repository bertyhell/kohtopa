package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@XmlRootElement
public class ProgramSettings {
	//user settings

	private static String connectionstring;
	private static String username;
	private static String password;
        private static int userID;
	private static boolean savePass;
	//confirm settings
	private static boolean confirmDeleteBuildings;
	private static boolean confirmDeletePictures;
	private static boolean confirmDeleteRentables;
	private static boolean confirmDeleteTasks;
	private static boolean confirmDeleteInvoices;
	private static boolean confirmDeleteFloors;

	public static void write() {
		try {
			//create xml string
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			//create the root element and add it to the document
			Element root = doc.createElement("Settings");
			doc.appendChild(root);

			//create child element, set content and add to root

			Element child = doc.createElement("connectionstring");
			child.setTextContent(connectionstring);
			root.appendChild(child);

			child = doc.createElement("username");
			child.setTextContent(username);
			root.appendChild(child);

			child = doc.createElement("password");
			child.setTextContent(password);
			root.appendChild(child);

			child = doc.createElement("savePass");
			child.setTextContent(savePass ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeleteBuildings");
			child.setTextContent(confirmDeleteBuildings ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeletePictures");
			child.setTextContent(confirmDeletePictures ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeleteRentables");
			child.setTextContent(confirmDeleteRentables ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeleteTasks");
			child.setTextContent(confirmDeleteTasks ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeleteInvoices");
			child.setTextContent(confirmDeleteInvoices ? "true" : "false");
			root.appendChild(child);

			child = doc.createElement("confirmDeleteFloors");
			child.setTextContent(confirmDeleteFloors ? "true" : "false");
			root.appendChild(child);

			//Output the XML

			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			//create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();

			//print xml
			BufferedWriter output = new BufferedWriter(new FileWriter(new File("settings.xml")));
			output.write(xmlString);
			output.close();
			System.out.println("Settings succesfully stored");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void read() {
		File file = new File("settings.xml");
		if (file.exists()) {
			try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			//reading vars
			NodeList nodeLst = doc.getElementsByTagName("connectionstring");
			connectionstring = nodeLst.item(0).getTextContent();

			nodeLst = doc.getElementsByTagName("username");
			username = nodeLst.item(0).getTextContent();

			nodeLst = doc.getElementsByTagName("password");
			password = nodeLst.item(0).getTextContent();
			
			nodeLst = doc.getElementsByTagName("savePass");
			savePass = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeleteBuildings");
			confirmDeleteBuildings = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeletePictures");
			confirmDeletePictures = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeleteRentables");
			confirmDeleteRentables = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeleteTasks");
			confirmDeleteTasks = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeleteInvoices");
			confirmDeleteInvoices = nodeLst.item(0).getTextContent().equals("true") ? true : false;

			nodeLst = doc.getElementsByTagName("confirmDeleteFloors");
			confirmDeleteFloors = nodeLst.item(0).getTextContent().equals("true") ? true : false;
			}catch(Exception ex){
				System.out.println("couln't read settings file (using defaults): \n" + ex.getMessage()); //add to log file
				setDefaults();
			}
		} else {
			setDefaults();
		}
	}

	private static void setDefaults() {
		//default settings:
		username = "";
		password = "";
                userID = 0;
		connectionstring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa";
		savePass = false;
		confirmDeleteBuildings = true;
		confirmDeletePictures = true;
		confirmDeleteRentables = true;
		confirmDeleteTasks = true;
		confirmDeleteInvoices = true;
		confirmDeleteFloors = true;
	}

	public static String getConnectionstring() {
		return connectionstring;
	}

	public static void setConnectionstring(String connectionstring) {
		ProgramSettings.connectionstring = connectionstring;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		ProgramSettings.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ProgramSettings.password = password;
	}

        public static int getUserID() {
            return userID;
        }

        public static void setUserID(int userID) {
            ProgramSettings.userID = userID;
        }

	public static boolean confirmDeleteBuildings() {
		return confirmDeleteBuildings;
	}

	public static void setconfirmDeleteBuildings(boolean confirmDeleteBuildings) {
		ProgramSettings.confirmDeleteBuildings = confirmDeleteBuildings;
	}

	public static boolean confirmDeleteFloors() {
		return confirmDeleteFloors;
	}

	public static void setconfirmDeleteFloors(boolean confirmDeleteFloors) {
		ProgramSettings.confirmDeleteFloors = confirmDeleteFloors;
	}

	public static boolean confirmDeleteInvoices() {
		return confirmDeleteInvoices;
	}

	public static void setconfirmDeleteInvoices(boolean confirmDeleteInvoices) {
		ProgramSettings.confirmDeleteInvoices = confirmDeleteInvoices;
	}

	public static boolean confirmDeletePictures() {
		return confirmDeletePictures;
	}

	public static void setconfirmDeletePictures(boolean confirmDeletePictures) {
		ProgramSettings.confirmDeletePictures = confirmDeletePictures;
	}

	public static boolean confirmDeleteRentables() {
		return confirmDeleteRentables;
	}

	public static void setconfirmDeleteRentables(boolean confirmDeleteRentables) {
		ProgramSettings.confirmDeleteRentables = confirmDeleteRentables;
	}

	public static boolean confirmDeleteTasks() {
		return confirmDeleteTasks;
	}

	public static void setconfirmDeleteTasks(boolean confirmDeleteTasks) {
		ProgramSettings.confirmDeleteTasks = confirmDeleteTasks;
	}

	public static boolean isRemeberPassword() {
		return savePass;
	}

	public static void setRemeberPassword(boolean remeberPassword) {
		ProgramSettings.savePass = remeberPassword;
	}
}
