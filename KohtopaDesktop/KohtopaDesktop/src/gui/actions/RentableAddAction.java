package gui.actions;

import Language.Language;
import gui.Main;
import gui.addremovetab.BuildingDialog;
import gui.addremovetab.RentableDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class RentableAddAction extends AbstractIconAction {

	public RentableAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Main.getDataObject().isBuildingSelected()) {
			new RentableDialog(null, Main.getDataObject().getSelectedBuildingId(), true).setVisible(true); //TODO show what building your adding the rentable to
		} else {
			JOptionPane.showMessageDialog(Main.getInstance(), "please select a building first \n", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
