package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import Language.Language;

/**
 *
 * @author bert
 */
public class BuildingAddAction extends AbstractAction {

	public BuildingAddAction(Icon img) {
		super(Language.getString("addBuilding"), img);
		super.putValue("SHORT_DESCRIPTION", Language.getString("addBuilding"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}