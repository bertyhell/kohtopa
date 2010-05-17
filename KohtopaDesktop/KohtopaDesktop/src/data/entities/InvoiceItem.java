package data.entities;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class InvoiceItem {

	private String description;
	private double prijs;

	public InvoiceItem(String description, double prijs) {
		this.description = description;
		this.prijs = prijs;
	}

	public String getDescription() {
		return description;
	}

	public double getPrijs() {
		return prijs;
	}

	public Object[] toObjects(){
		return new Object[]{description, new Double(prijs)};
	}
}
