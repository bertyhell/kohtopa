package gui.actions;

import Language.Language;
import data.entities.Picture;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IIdentifiable;
import gui.interfaces.IPictureListContainer;
import gui.interfaces.IRentableListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class PicturePreviewAction extends AbstractIconAction {

	public PicturePreviewAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			IIdentifiable root = ((JActionButton) e.getSource()).getRoot();
			Object[] selected = ((IPictureListContainer) root).getSelectedPictures();
			if (selected.length == 1) {
				if (root instanceof IRentableListContainer) {
					//add building picture
					Main.getDataObject().addBuildingPreviewPicture(root.getId(), ((Picture) selected[0]).getPicture());
				} else {
					// add rentable picture
					Main.getDataObject().addRentablePreviewPicture(root.getId(), ((Picture) selected[0]).getPicture());
				}
			} else {
				JOptionPane.showMessageDialog(Main.getInstance(), "Please select exacly 1 image to make preview", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
