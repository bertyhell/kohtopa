package data.entities;

import gui.Main;
import java.util.Date;
import java.util.Vector;

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
	private boolean send;
	private boolean paid;

	public Invoice(int invRentId, boolean newInvoice) {
		owner = Main.getDataObject().getOwner();
		items = new Vector<InvoiceItem>();
		if (newInvoice) {
			renter = Main.getDataObject().getPerson(invRentId);
		} else {
			//TODO get renter from invoice id from database
			//TODO read xml file from database
		}
	}


	public Invoice(int invoceId, Date sendingDate, boolean send, boolean paid){
		this.id = invoceId;
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
}
