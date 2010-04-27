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

	public Invoice(int renterId){
		owner = Main.getDataObject().getOwner();
	}
}
