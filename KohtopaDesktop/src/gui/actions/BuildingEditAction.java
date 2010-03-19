package gui.actions;

import gui.Main;
import gui.AddRemoveTab.BuildingDialog;
import gui.AddRemoveTab.BuildingListPanel;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class BuildingEditAction extends AbstractIconAction {

	public BuildingEditAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) { //TODO if no selected > do nothing (nullpointerexception)
		int buildingId = ((BuildingListPanel)Main.getInstance().getListBuildings().getSelectedValue()).getId();
		BuildingDialog.show(Main.getInstance(), buildingId, false); //TODO replce main with e.source or something like that
	}


}
