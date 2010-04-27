package gui.addremovetab;

import Language.Language;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.entities.Rentable;

public class RentableCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Rentable rentable = (Rentable) value;
		RentableListPanel pnlRentable = new RentableListPanel(
				rentable.getId(),
				Language.getRentableTypes()[rentable.getType()],
				null, //TODO addpreview image
				rentable.getFloor(),
				rentable.getDescription());
		if (isSelected) {
			pnlRentable.setBgColor(new Color(150, 150, 150));
		} else {
			if (index % 2 == 0) {
				pnlRentable.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlRentable.setBgColor(new Color(170, 170, 170));
			}
		}
		return pnlRentable;
	}
}
