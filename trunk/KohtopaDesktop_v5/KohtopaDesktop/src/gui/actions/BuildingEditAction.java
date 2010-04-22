package gui.actions;

import gui.Main;
import gui.addremovetab.BuildingDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

public class BuildingEditAction extends AbstractIconAction {

	public BuildingEditAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Main.getDataObject().isBuildingSelected()){
			new BuildingDialog(null, Main.getDataObject().getSelectedBuildingId(), false).setVisible(true);
		}
	}
}
