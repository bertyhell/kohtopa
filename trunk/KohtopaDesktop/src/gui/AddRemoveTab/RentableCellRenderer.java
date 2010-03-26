package gui.AddRemoveTab;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.DataModel;
import data.addremove.RentableListModel;
import data.entities.Rentable;

public class RentableCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

	    System.out.println("value item: " + value);
	    System.out.println("jlist: " + list.getModel().getClass());
	    System.out.println("index of item: " + index);
		((RentableListModel)list.getModel()).printItems();
		Rentable rentable = (Rentable)value;
		RentableListPanel pnlRentable = new RentableListPanel(
				rentable.getId(),
				rentable.getType(),
				null,
				rentable.getFloor(),
				rentable.getDescription());
		if (index != DataModel.getRentableIndex()) {
			if (index % 2 == 0) {
				pnlRentable.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlRentable.setBgColor(new Color(170, 170, 170));
			}
		} else {
			pnlRentable.setBgColor(new Color(150, 150, 150));
		}
		return pnlRentable;
	}
}
