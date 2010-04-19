package gui.actions;

import gui.Main;
import gui.addremovetab.BuildingDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JComponent;

public class BuildingEditAction extends AbstractIconAction {

    public BuildingEditAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //TODO if no selected > do nothing (nullpointerexception)
	new BuildingDialog(((JComponent)e.getSource()).getRootPane(), Main.getDataObject().getSelectedBuildingId(), false); //TODO replce main with e.source or something like that
    }
}
