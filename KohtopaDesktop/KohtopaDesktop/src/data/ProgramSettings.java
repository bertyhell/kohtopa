package data;

import gui.Logger;
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
    private static Integer ownerId;
    private static boolean savePass;
    private static Level loggerLevel;
    private static String language;
    private static String SETTINGS_FILE = System.getProperty("user.home") + "\\AppData\\Roaming\\kohtopa\\settings.xml";

    private static void setDefaults() {
	Logger.logger.info("using default settings");
	//default settings:
	username = "";
	password = "";
	ownerId = null;
//	connectionstring = "jdbc:oracle:thin:@localhost:1521:XE"; //jelle & ruben
//	connectionstring = "jdbc:oracle:thin:@192.168.58.128:1521:kohtopa"; //laptop bert
	connectionstring = "jdbc:oracle:thin:@192.168.19.129:1521:kohtopa"; //pc bert
	savePass = false;
	loggerLevel = Level.DEBUG;
	language = "English";
    }

    public static void write() {
	Logger.logger.info("Writing programsettings");
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


	    child = doc.createElement("savePass");
	    child.setTextContent(savePass ? "true" : "false");
	    root.appendChild(child);

	    if (savePass) {
		child = doc.createElement("username");
		child.setTextContent(username);
		root.appendChild(child);

		child = doc.createElement("password");
		child.setTextContent(password);
		root.appendChild(child);
	    }


	    child = doc.createElement("loggerLevel");
	    child.setTextContent(loggerLevel.toString());
	    root.appendChild(child);

	    child = doc.createElement("language");
	    child.setTextContent(language);
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
	    BufferedWriter output = new BufferedWriter(new FileWriter(new File(SETTINGS_FILE)));
	    output.write(xmlString);
	    output.close();

	    Logger.logger.info("Settings succesfully stored");
	} catch (Exception ex) {
	    Logger.logger.warn("wrong parameters added to jar: \n" + ex.getMessage());
	}
    }

    public static void read() {
	Logger.logger.info("reading program settings from " + SETTINGS_FILE);
	File file = new File(SETTINGS_FILE);
	if (file.exists()) {
	    Logger.logger.info("settingsfile found");
	    try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		//reading vars
		NodeList nodeLst = doc.getElementsByTagName("connectionstring");
		connectionstring = nodeLst.item(0).getTextContent();


		nodeLst = doc.getElementsByTagName("savePass");
		savePass = nodeLst.item(0).getTextContent().equals("true") ? true : false;

		if (savePass) {
		    nodeLst = doc.getElementsByTagName("username");
		    username = nodeLst.item(0).getTextContent();

		    nodeLst = doc.getElementsByTagName("password");
		    password = nodeLst.item(0).getTextContent();
		}


		nodeLst = doc.getElementsByTagName("loggerLevel");
		loggerLevel = Level.toLevel(nodeLst.item(0).getTextContent(), Level.ALL);

		nodeLst = doc.getElementsByTagName("language");
		language = nodeLst.item(0).getTextContent();

	    } catch (Exception ex) {
		Logger.logger.warn("couln't read settings file (using defaults): \n" + ex.getMessage());
		setDefaults();
	    }
	} else {
	    setDefaults();
	}
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

    public static int getOwnerId() {
	return ownerId;
    }

    public static void setOwnerId(Integer ownerId) {
	ProgramSettings.ownerId = ownerId;
    }

    public static boolean isLoggedIn() {
	return ownerId != null;
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

    public static String getLanguage() {
	return language;
    }

    public static void setLanguage(String language) {
	ProgramSettings.language = language;
    }

    public static void print() {
	Logger.logger.info("ProgramSettings: ");
	Logger.logger.info("\tconnectionstring: " + connectionstring);
	Logger.logger.info("\tusername: " + username);
	Logger.logger.info("\tpassword: " + password);
	Logger.logger.info("\townerId: " + ownerId);
	Logger.logger.info("\tsavePass: " + savePass);
	Logger.logger.info("\tloggerLevel: " + loggerLevel);
	Logger.logger.info("\tlanguage: " + language);
    }
}
