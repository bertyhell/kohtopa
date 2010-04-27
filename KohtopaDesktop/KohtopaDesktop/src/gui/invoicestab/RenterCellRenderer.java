package gui.invoicestab;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.DataModel;
import data.entities.Person;

public class RenterCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Person renter = (Person) list.getModel().getElementAt(index);
		RenterListPanel pnlRenter = new RenterListPanel(
				renter.getId(),
				null, //TODO add preview image for person
				renter.toString());
		if (isSelected) {
			pnlRenter.setBgColor(new Color(150, 150, 150));
		} else {
			if (index % 2 == 0) {
				pnlRenter.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlRenter.setBgColor(new Color(170, 170, 170));
			}
		}
		return pnlRenter;
	}
}
