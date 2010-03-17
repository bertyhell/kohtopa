package gui.actions;

import Language.Language;
import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class FloorAddAction extends AbstractAction {

	public FloorAddAction(Icon img) {
		super(Language.getString("addFloor"), img);
		super.putValue("SHORT_DESCRIPTION", Language.getString("addFloor"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}