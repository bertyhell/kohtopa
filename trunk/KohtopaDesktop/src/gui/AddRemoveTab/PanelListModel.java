package gui.AddRemoveTab;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class PanelListModel extends AbstractListModel {


	private ArrayList<AbstractListPanel> items = new ArrayList<AbstractListPanel>();

	public PanelListModel() {
	}

	public int getSize() {
		return items.size();
	}

	public Object getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items = new ArrayList<AbstractListPanel>();
	}

	public void addElement(AbstractListPanel i) {
		items.add(i);
	}
}
