package tasks;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import main.DataConnector;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class RunnableInvoice implements Runnable {

	private final String FILENAME = "temp.pdf";
	private final String SMTPHOST = "smtp.scarlet.be";

	public void run() {
		System.out.println("getting facturen");
		try {
			Vector<String> invoices = new Vector<String>();
			DataConnector.selectInvoicesToBeSend(invoices);
			for (String invoice : invoices) {
				//generate pdf from xml from database
				convertXml2Pdf(invoice);

				//send generated pdf trough mail
//				SendInvoiceByMail("verhelst_bert@hotmail.com", "invoice@kohtopa.be");

				//put generated pdf invoice back in database
				//TODO 100 put pdf invoice back in database and put send = 1

				//delete temp pdf
//				File pdf = new File(FILENAME);
//				if (pdf.exists()) {
//					pdf.delete();
//				} else {
//					System.out.println("Failed to delete temp pdf: this could cause problems for all other invoices");
//				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void convertXml2Pdf(String invoice) {
		try {
			//init xml reader
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(invoice));
			Document d = builder.parse(is);

			//init pdf doc
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILENAME));
				document.open();
				PdfContentByte cb = writer.getDirectContent();
				Graphics2D graphics2D = cb.createGraphics(PageSize.A4.getWidth(), PageSize.A4.getHeight());

				//start writing invoice

				//owner
				Node owner = d.getElementsByTagName("owner").item(0);
				NodeList infoOwner = owner.getChildNodes();
				graphics2D.drawString("Owner:", 40, 54);
				for (int i = 0; i < infoOwner.getLength(); i++) {
					graphics2D.drawString(infoOwner.item(i).getTextContent(), 40, 64 +i*7); //TODO 040 fix country in xml > its still BE should be belgium
					//TODO 040 add email to xml file
				}
				
				//renter
				Node renter = d.getElementsByTagName("renter").item(0);
				NodeList infoRenter = renter.getChildNodes();
				graphics2D.drawString("Renter:", 400, 54);
				for (int i = 0; i < infoOwner.getLength(); i++) {
					graphics2D.drawString(infoOwner.item(i).getTextContent(), 400, 64 +i*7); //TODO 040 fix country in xml > its still BE should be belgium
				}

				//invoice items:
				

				


				graphics2D.dispose();

				//end writing invoice
			} catch (Exception ex) {
				System.out.println("Exception during pfd generation: " + ex.getMessage());
				ex.printStackTrace();
			}
			document.close();
//
//
//			//detail factuur
//			document.add(new Paragraph("Overzicht Telefoongesprekken"));
//			float[] widths = {10f, 8f, 8f, 5f, 5f};
//			PdfPTable tabel = new PdfPTable(widths);
//			tabel.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//			tabel.setSpacingBefore(15f);
//			tabel.setWidthPercentage(100f);
//
//			tabel.addCell(new PdfPCell(new Paragraph("Datum tijd")));
//			tabel.addCell(new PdfPCell(new Paragraph("Nummer van")));
//			tabel.addCell(new PdfPCell(new Paragraph("Nummer naar")));
//			tabel.addCell(new PdfPCell(new Paragraph("Duur (seconden)")));
//			tabel.addCell(new PdfPCell(new Paragraph("Kost (eurocent)")));
//
//
//
//
//			//create pdf
//			Node owner = d.getElementsByTagName("owner").item(0);
//			NodeList infoOwner = owner.getChildNodes();
//			for (int i = 0; i < infoOwner.getLength(); i++) {
//				System.out.println(infoOwner.item(i).getTextContent());
//			}
//

			System.out.println("invoice succesfully generated");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void SendInvoiceByMail(String to, String from) {
		{
			String msgText1 = "Hello \nThis is an automaticaly generated invoice by Kohtopa.";
			String subject = "Invoice Kohtopa";

			// create some properties and get the default Session
			Properties props = System.getProperties();
			props.put("mail.smtp.host", SMTPHOST);

			Session session = Session.getInstance(props, null);

			try {
				// create a message
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = {new InternetAddress(to)};
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(subject);

				// create and fill the first message part
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(msgText1);

				// create the second message part
				MimeBodyPart mbp2 = new MimeBodyPart();

				// attach the file to the message
				FileDataSource fds = new FileDataSource(FILENAME);
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());

				// create the Multipart and add its parts to it
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp1);
				mp.addBodyPart(mbp2);

				// add the Multipart to the message
				msg.setContent(mp);

				// set the Date: header
				msg.setSentDate(new Date());

				// send the message
				Transport.send(msg);


			} catch (MessagingException ex) {
				System.out.println("Failed to send mail: " + ex.getMessage());
				ex.printStackTrace();
			}                       
		}
	}
}
