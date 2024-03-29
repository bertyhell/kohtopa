package gui.addremovetab;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.entities.Building;

public class BuildingCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//TODO split so it converts building to panelbuildinglist

		Building building = (Building)list.getModel().getElementAt(index);
		BuildingListPanel pnlBuilding = new BuildingListPanel(
				building.getId(),
				building.getPreviewImage(),
				building.getAddress().getStreetLine(),
				building.getAddress().getCityLine());
		if (isSelected) {
			pnlBuilding.setBgColor(new Color(150, 150, 150));
		} else {
			if (index % 2 == 0) {
				pnlBuilding.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlBuilding.setBgColor(new Color(170, 170, 170));
			}
		}
		return pnlBuilding;
	}
}
