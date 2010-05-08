package gui.actions;

import Language.Language;
import data.entities.Picture;
import gui.Main;
import gui.addremovetab.IIdentifiable;
import gui.addremovetab.IPictureListContainer;
import gui.addremovetab.IRentableListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class PictureRemoveAction extends AbstractIconAction {

	public PictureRemoveAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("remove pictures");
		try {
			JRootPane root = ((JComponent) e.getSource()).getRootPane();
			Vector<Integer> selected = new Vector<Integer>();
			for(Object picture :  ((IPictureListContainer) root).getSelectedPictures()){
				//getting list of picture id's to be deleted
				selected.add(((Picture)picture).getId());
			}
			Main.getDataObject().deleteSelectedPictures(selected);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
