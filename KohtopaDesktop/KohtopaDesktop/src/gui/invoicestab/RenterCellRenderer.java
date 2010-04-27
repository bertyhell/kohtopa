package gui.invoicestab;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.DataModel;
import data.entities.Renter;

public class RenterCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Renter renter = (Renter)list.getModel().getElementAt(index);
		RenterListPanel pnlRenter = new RenterListPanel(
				renter.getId(),
				null, //TODO add preview image for person
				renter.getName());
		if (index != DataModel.getBuildingIndex()) {
			if (index % 2 == 0) {
				pnlRenter.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlRenter.setBgColor(new Color(170, 170, 170));
			}
		} else {
			pnlRenter.setBgColor(new Color(150, 150, 150));
		}
		return pnlRenter;
	}
}
