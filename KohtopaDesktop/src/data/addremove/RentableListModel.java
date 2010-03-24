package data.addremove;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import data.entities.Rentable;

public class RentableListModel extends AbstractListModel{


	protected ArrayList<Rentable> items = new ArrayList<Rentable>();

	public int getSize() {
		return items.size();
	}

	public Rentable getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Rentable Rentable) {
		items.add(Rentable);
	}

	public void updateItems(int buildingId) throws SQLException, IOException{
		items = DataConnector.getRentablesFromBuilding(buildingId);
	}

}
