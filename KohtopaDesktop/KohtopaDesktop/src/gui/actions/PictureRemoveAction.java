package gui.actions;

import Language.Language;
import data.entities.Picture;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IIdentifiable;
import gui.interfaces.IPictureListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class PictureRemoveAction extends AbstractIconAction {

	public PictureRemoveAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			IIdentifiable root = ((JActionButton) e.getSource()).getRoot();
			Vector<Integer> selected = new Vector<Integer>();
			for (Object picture : ((IPictureListContainer) root).getSelectedPictures()) {
				//getting list of picture id's to be deleted
				selected.add(((Picture) picture).getId());
			}
			Main.getDataObject().deleteSelectedPictures(selected);
			//update list

			((IPictureListContainer) root).updatePictureList();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
