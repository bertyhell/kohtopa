package gui.actions;

import Language.Language;
import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class RoomEditAction extends AbstractAction {

    public RoomEditAction(Icon img) {
	super(Language.getString("editRoom"), img);
	this.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
	JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
    }
}
