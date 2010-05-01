package data.addremove;

import data.DataConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import data.entities.Building;

public class BuildingListModel extends AbstractListModel {

	protected ArrayList<Building> items = new ArrayList<Building>();

	public int getSize() {
		return items.size();
	}

	public Building getElementAt(int index) {
		try{
			return items.get(index);
		}catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("error building selected not in scope: " + ex.getMessage());
			return null;
		}
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Building building) {
		items.add(building);
	}

	public void updateItems() throws SQLException, IOException {
		items = DataConnector.selectBuildingPreviews();
	}

	public int getId(int buildingIndex) {
		return items.get(buildingIndex).getId();
	}
}
