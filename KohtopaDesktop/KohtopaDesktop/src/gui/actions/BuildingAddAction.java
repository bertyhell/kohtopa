package gui.actions;

import gui.Main;
import gui.addremovetab.BuildingAddDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

public class BuildingAddAction extends AbstractIconAction {

	public BuildingAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new BuildingAddDialog(Main.getInstance()).setVisible(true);
	}
}
