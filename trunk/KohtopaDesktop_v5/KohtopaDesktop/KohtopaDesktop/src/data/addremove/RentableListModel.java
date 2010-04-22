package data.addremove;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import data.entities.Rentable;
import java.util.Collections;
import javax.swing.DefaultListModel;

public class RentableListModel extends DefaultListModel {

	public void addElement(Rentable Rentable) {
		super.addElement(Rentable);
	}

	public Rentable getRentableAt(int index) {
		return (Rentable) super.getElementAt(index);
	}

	public void updateItems(int buildingId) throws SQLException, IOException {
		ArrayList<Rentable> items = DataConnector.getRentablesFromBuilding(buildingId);
		super.clear();
		for (Rentable rentable : items) {
			super.addElement(rentable);
		}
	}

	public ArrayList<Integer> getFloors() {
		ArrayList<Integer> floors = new ArrayList<Integer>();
		for (int i = 0; i < super.getSize(); i++) {
			int floor = getRentableAt(i).getFloor();
			int j = 0;
			while (j < floors.size() && floor != floors.get(j)) {
				j++;
			}
			if (j == floors.size()) {
				floors.add(floor);
			}
		}
		Collections.sort(floors);
		return floors;
	}
}
