package gui.addremove;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class PanelListModel extends AbstractListModel {


	private ArrayList<AbstractListPanel> items = new ArrayList<AbstractListPanel>();

	public PanelListModel() {
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
