package gui.actions;

import gui.addremovetab.BuildingDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JComponent;

public class BuildingAddAction extends AbstractIconAction {

	public BuildingAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new BuildingDialog(((JComponent) e.getSource()).getRootPane(), -1, true).setVisible(true);
	}
}
