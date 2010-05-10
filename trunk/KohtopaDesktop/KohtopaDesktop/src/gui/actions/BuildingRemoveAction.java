package gui.actions;

import Language.Language;
import data.entities.Building;
import gui.JActionButton;
import gui.Main;
import gui.addremovetab.IBuildingListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class BuildingRemoveAction extends AbstractIconAction {

	public BuildingRemoveAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Vector<Integer> selected = new Vector<Integer>();
		for (Object building : ((IBuildingListContainer) ((JActionButton) e.getSource()).getRoot()).getSelectedBuildings()) {
			selected.add(((Building) building).getId());
		}
		if (JOptionPane.showConfirmDialog(
				null,
				Language.getString("confirmDelete") + " " + selected.size() + " " + Language.getString("building_s"), //TODO p000 change building(s) to building if 1 else buildings
				Language.getString("confirm"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			//delete images
			Main.getDataObject().deleteSelectedBuildings(selected);
		}
	}
}
