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
public class TaskAddAction extends AbstractAction {

	public TaskAddAction(Icon img) {
		super(Language.getString("addTask"), img);
		super.putValue("SHORT_DESCRIPTION", Language.getString("addTask"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}