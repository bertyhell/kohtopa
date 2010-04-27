package data.invoices;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.AbstractListModel;
import data.entities.Person;
import java.util.Vector;

/**
 *
 * @author Bert Verhelst
 */
public class RenterListModel extends AbstractListModel {

	protected Vector<Person> items = new Vector<Person>();

	public int getSize() {
		return items.size();
	}

	public Person getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Person renter) {
		items.add(renter);
	}

	public int getId(int RenterIndex) {
		return items.get(RenterIndex).getId();
	}

	public void updateItems(Integer ownerId) throws SQLException, IOException {
		items = DataConnector.selectRenterPreviews(ownerId);
		System.out.println("number of renters: " + items.size());
	}
}
