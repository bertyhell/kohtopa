package data.entities;

import gui.Main;
import java.util.Vector;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class Invoice {

	private Vector<InvoiceItem> items;
	private Person owner;
	private Person renter;

	public Invoice(int invRentId, boolean newInvoice) {
		owner = Main.getDataObject().getOwner();
		items = new Vector<InvoiceItem>();
		if (newInvoice) {
			renter = Main.getDataObject().getPerson(invRentId);
			//TODO read xml file from database
		} else {
			//TODO get renter from invoice id from database
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
}