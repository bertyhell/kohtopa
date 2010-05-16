package gui.actions;

import Language.Language;
import data.entities.Building;
import gui.JActionButton;
import gui.Main;
import gui.addremovetab.BuildingEditDialog;
import gui.interfaces.IBuildingListContainer;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class BuildingEditAction extends AbstractIconAction {

	public BuildingEditAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object[] selected = ((IBuildingListContainer)((JActionButton)e.getSource()).getRoot()).getSelectedBuildings();
		if(selected.length == 1){
			new BuildingEditDialog(Main.getInstance(), ((Building)selected[0]).getId()).setVisible(true);
		}else{
			JOptionPane.showMessageDialog(Main.getInstance(), "please select exacly 1 building to Edit",Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}


}
