package gui.AddRemoveTab;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.DataModel;
import model.data.Rentable;

public class RentableCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//TODO split so it converts building to panelbuildinglist

		Rentable rentable = (Rentable)list.getModel().getElementAt(index);
		RentableListPanel pnlRentable = new RentableListPanel(
				rentable.getId(),
				rentable.getType(),
				null,
				rentable.getFloor());
		if (index != DataModel.getBuildingIndex()) {
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
