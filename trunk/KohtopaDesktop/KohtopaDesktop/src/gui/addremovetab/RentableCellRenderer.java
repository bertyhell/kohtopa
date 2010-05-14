package gui.addremovetab;

import Language.Language;
import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.entities.Rentable;
import gui.Main;
import javax.swing.JOptionPane;

public class RentableCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Rentable rentable = (Rentable) value;
		RentableListPanel pnlRentable = null;
		try {
			pnlRentable = new RentableListPanel(
					rentable.getId(),
					rentable.getPreviewImage(),
					Language.getRentableTypes()[rentable.getType()],
					Main.getDataObject().getRenterInRentable(rentable.getId()),
					rentable.getFloor(),
					rentable.getDescription());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to retrieve renter from database: \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			pnlRentable = new RentableListPanel(
					rentable.getId(),
					rentable.getPreviewImage(),
					Language.getRentableTypes()[rentable.getType()],
					"",
					rentable.getFloor(),
					rentable.getDescription());
		}
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
