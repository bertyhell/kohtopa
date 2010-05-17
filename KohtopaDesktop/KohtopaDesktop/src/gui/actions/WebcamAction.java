/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import Language.Language;
import data.entities.Building;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IBuildingListContainer;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import webcammodule.WebcamDialog;

/**
 *
 * @author Ruben
 */
public class WebcamAction extends AbstractIconAction {

	public WebcamAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object[] selected = ((IBuildingListContainer)((JActionButton)e.getSource()).getRoot()).getSelectedBuildings();
		if(selected.length == 1){
			new WebcamDialog(Main.getInstance(), ((Building)selected[0]).getId()).setVisible(true);
		}else{
			JOptionPane.showMessageDialog(Main.getInstance(), "Please select exacly 1 building to view its webcam.", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}


}