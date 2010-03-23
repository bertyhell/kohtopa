package gui.AddRemoveTab;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import model.data.Rentable;

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

	public void setData(ArrayList<Rentable> items) {
		this.items = items;
	}

}
