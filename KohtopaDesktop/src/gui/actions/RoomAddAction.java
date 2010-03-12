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
public class RoomAddAction extends AbstractAction {

	public RoomAddAction(Icon img) {
		super(Language.getString("addRoom"), img);
		super.putValue("SHORT_DESCRIPTION", Language.getString("addRoom"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}