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
public class RoomRemoveAction extends AbstractAction{

	public RoomRemoveAction(Icon img) {
		super(Language.getString("removeRooms"), img);
		this.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setEnabled(boolean enable){
		super.setEnabled(enable);
		super.putValue("SHORT_DESCRIPTION", enable?Language.getString("removeRooms"):"");
	}
}
