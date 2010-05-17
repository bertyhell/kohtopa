package data.entities;

import gui.Logger;
import gui.Main;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class Invoice {

	private int id;
	private Vector<InvoiceItem> items;
	private Person owner;
	private Person renter;
	private Date sendingDate;
        private String xml;
	private boolean send;
	private boolean paid;

//	public Invoice(Person p, int invoiceid) {
//            this(p);
//	}

        public Invoice(Person p) {

		owner = Main.getDataObject().getOwner();
		items = new Vector<InvoiceItem>();

                renter = p;

        }



        public Invoice(int invoiceId, Date sendingDate, Blob data,
                Person renter, boolean send, boolean paid){
            this(renter);
            this.id = invoiceId;
            this.sendingDate = sendingDate;
            this.paid = paid;
            this.send = send;
            this.renter = renter;

            try {
                if(data != null) {
                    xml = new String(data.getBytes(1, (int) data.length()));
                    //System.out.println(xml);
                    parseXml(xml);
                }
            } catch (SQLException ex) {
               Logger.logger.debug("Probleem bij ophalen xml", ex);
            }
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
		return id;
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

    private void parseXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document d = builder.parse(is);

            NodeList nodes = d.getElementsByTagName("invoice_item");

            for(int i=0 ; i<nodes.getLength() ; i++) {
                Element current = (Element)nodes.item(i);
                String description = current.getElementsByTagName("description").item(0).getTextContent();
                
                double price = Double.parseDouble(current.getElementsByTagName("price").item(0).getTextContent());

                //System.out.println(description+"="+price);
                addItem(description,price);
            }

        } catch (Exception ex) {
            Logger.logger.debug("Probleem bij parsen xml", ex);
        }

    }
}
