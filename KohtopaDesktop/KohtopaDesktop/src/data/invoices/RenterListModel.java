package data.invoices;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import data.entities.Renter;

/**
 *
 * @author Bert Verhelst
 */
public class RenterListModel extends AbstractListModel {

	protected ArrayList<Renter> items = new ArrayList<Renter>();

	public int getSize() {
		return items.size();
	}

	public Renter getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Renter renter) {
		items.add(renter);
	}

	public int getId(int RenterIndex) {
		return items.get(RenterIndex).getId();
	}

	public void updateItems(Integer ownerId) throws SQLException, IOException {
		DataConnector.selectRenterPreviews(ownerId);
	}
}
