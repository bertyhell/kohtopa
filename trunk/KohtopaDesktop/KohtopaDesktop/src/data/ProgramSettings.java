package data;

import gui.Main;
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
import org.apache.log4j.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@XmlRootElement
public class ProgramSettings {
	//user settings

	private static String connectionstring;
	private static String username;
	private static String password;
	private static int ownerId;
	private static boolean savePass;
	private static Level loggerLevel;

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

			child = doc.createElement("loggerLevel");
			child.setTextContent(loggerLevel.toString());
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

			Main.logger.info("Settings succesfully stored");
		} catch (Exception ex) {
			Main.logger.warn("wrong parameters added to jar: \n" + ex.getMessage());
		}
	}

	public static void read() {
		File file = new File("settings.xml");
		if (file.exists()) {
			try {
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

				nodeLst = doc.getElementsByTagName("loggerLevel");
				loggerLevel = Level.toLevel(nodeLst.item(0).getTextContent(), Level.ALL);

			} catch (Exception ex) {
				Main.logger.warn("couln't read settings file (using defaults): \n" + ex.getMessage());
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
		ownerId = 0;
		connectionstring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa";
		savePass = false;
		loggerLevel = Level.OFF;
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

	public static int getOwnerID() {
		return ownerId;
	}

	public static void setOwnerID(int userID) {
		ProgramSettings.ownerId = userID;
	}

	public static boolean isRemeberPassword() {
		return savePass;
	}

	public static void setRemeberPassword(boolean remeberPassword) {
		ProgramSettings.savePass = remeberPassword;
	}

	public static boolean isSavePass() {
		return savePass;
	}

	public static Level getLoggerLevel() {
		return loggerLevel;
	}
}
