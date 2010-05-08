package gui.actions;

import gui.Main;
import gui.addremovetab.IBuildingListContainer;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JComponent;

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
		Object[] selected = ((IBuildingListContainer)((JComponent)e.getSource()).getRootPane()).getSelectedBuildings();
		Main.getDataObject().deleteSelectedBuildings(selected);
	}
}
