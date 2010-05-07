package databasepoller;

import java.io.File;
import java.io.StringReader;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class RunnableInvoice implements Runnable {

	public void run() {
		System.out.println("getting facturen");
		try {
			Vector<String> invoices = new Vector<String>();
			DataConnector.selectInvoicesToBeSend(invoices);
			for(String invoice : invoices){
				System.out.println("found a factuur to be send _________________");
				File pdfFactuur = convertXml2Pdf(invoice);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private File convertXml2Pdf(String invoice) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(invoice));
			Document d = builder.parse(is);

			Node owner = d.getElementsByTagName("owner").item(0);
			NodeList infoOwner = owner.getChildNodes();
			for(int i=0;i<infoOwner.getLength();i++){
				System.out.println(infoOwner.item(i).getTextContent());
			}
			return null;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}
}
