package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class EditTaskAction extends AbstractAction {

	public EditTaskAction(Icon img) {
		super("Add Task", img);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}