package gui.AddRemoveTab;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import model.data.Building;

public class BuildingListModel extends AbstractListModel{


	protected ArrayList<Building> items = new ArrayList<Building>();

	public int getSize() {
		return items.size();
	}

	public Building getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Building building) {
		items.add(building);
	}

	public void setData(ArrayList<Building> items) {
		this.items = items;
	}

}
