package gui.AddRemoveTab;

import gui.Main;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class RentableCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		AbstractListPanel comp = (AbstractListPanel) list.getModel().getElementAt(index);
		int rentableIndex = Main.getRentableIndex();
		if (index != rentableIndex) {
			if (index % 2 == 0) {
				comp.setBgColor(Color.LIGHT_GRAY);
			} else {
				comp.setBgColor(new Color(170, 170, 170));
			}
		} else {
			comp.setBgColor(new Color(150, 150, 150));
		}
		return comp;
	}
}
