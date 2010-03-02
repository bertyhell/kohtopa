package gui.addremove;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class BuildingListModel extends AbstractListModel {

	private ArrayList<BuildingListPanel> items = new ArrayList<BuildingListPanel>();

	public BuildingListModel() {
	}

	public void add(BuildingListPanel b){
		items.add(b);
	}

	public int getSize() {
		return items.size();
	}

	public Object getElementAt(int index) {
		return items.get(index);
	}
}
