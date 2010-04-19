package gui.actions;

import Language.Language;
import gui.Main;
import gui.addremovetab.IdentifiableI;
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
		System.out.println("set preview");
		IdentifiableI dialog = Main.getFocusedDialog();
		int[] indexs = dialog.getSelectedPictures();
		System.out.println("index: " + indexs[0]);
		System.out.println("id: " +dialog.getId());
		
		if (indexs.length != 1) {
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("infoSelectPicture"), Language.getString("picture"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				if (dialog.getType().equals("BuildingDialog")) {
					Main.getDataObject().addBuildingPreviewPicture(dialog.getId(),indexs[0]);
				} else {
					Main.getDataObject().addRentablePreviewPicture(dialog.getId(),indexs[0]);
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
