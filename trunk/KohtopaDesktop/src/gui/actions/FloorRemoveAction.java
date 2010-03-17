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
public class FloorRemoveAction extends AbstractAction {

	public FloorRemoveAction(Icon img) {
		super(Language.getString("removeFloors"), img);
		this.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setEnabled(boolean enable){
		super.setEnabled(enable);
		super.putValue("SHORT_DESCRIPTION", enable?Language.getString("removeFloors"):"");
	}
}