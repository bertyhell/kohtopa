package data.entities;

import Language.Language;
import gui.Logger;
import gui.Main;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
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
public class Invoice {

	private int invoiceId;
	private Vector<InvoiceItem> items;
	private Person owner;
	private Person renter;
	private Date sendingDate;
	private boolean send;
	private boolean paid;
	private Date start;
	private Date end;

	public Invoice(int personId, int invoiceId, boolean newInvoice) {
		this.invoiceId = invoiceId;
		owner = Main.getDataObject().getOwner();
		renter = Main.getDataObject().getPerson(personId);
		items = new Vector<InvoiceItem>();
		if (!newInvoice) {
			try {
				sendingDate = Main.getDataObject().getInvoiceSendingDate(invoiceId);
				//TODO get renter from invoice id from database
				//TODO read xml file from database
				//get dom parser ready
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(Main.getDataObject().getInvoiceXmlString(invoiceId)));
				Document d = builder.parse(is);

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Node dateStart = d.getElementsByTagName("invoice_start_date").item(0);
				Node dateEnd = d.getElementsByTagName("invoice_end_date").item(0);
				start = formatter.parse(dateStart.getTextContent());
				end = formatter.parse(dateEnd.getTextContent());

				NodeList invoiceItems = d.getElementsByTagName("invoice_item");
				NodeList invoiceItemChilds;
				String description = null;
				for (int i = 0; i < invoiceItems.getLength(); i++) {
					invoiceItemChilds = invoiceItems.item(i).getChildNodes();
					for (int j = 0; j < invoiceItemChilds.getLength(); j++) {
						if (invoiceItemChilds.item(j).getNodeName().equals("description")) {
							description = invoiceItemChilds.item(j).getTextContent();
						} else if (invoiceItemChilds.item(j).getNodeName().equals("price")) {
							items.add(new InvoiceItem(description, Double.parseDouble(invoiceItemChilds.item(j).getTextContent())));
						}
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), "Error getting xml items parsing... \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
				Logger.logger.debug("Stacktrace\n" + ex);
			}
		}
	}

	public Invoice(int invoceId, Date sendingDate, boolean send, boolean paid) {
		this.invoiceId = invoceId;
		this.sendingDate = sendingDate;
		this.paid = paid;
		this.send = send;
	}

	public Person getOwner() {
		return owner;
	}

	public Person getRenter() {
		return renter;
	}

	public void addItem(String description, double price) {
		items.add(new InvoiceItem(description, price));
	}

	public void addItem(InvoiceItem item) {
		items.add(item);
	}

	public int getId() {
		return invoiceId;
	}

	public Vector<InvoiceItem> getItems() {
		return items;
	}

	public boolean isPaid() {
		return paid;
	}

	public boolean isSend() {
		return send;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public Date getEnd() {
		return end;
	}

	public Date getStart() {
		return start;
	}
}
