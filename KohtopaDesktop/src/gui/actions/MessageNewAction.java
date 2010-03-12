package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import Language.Language;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class MessageNewAction extends AbstractAction {

	public MessageNewAction(Icon img) {
		super(Language.getString("newMessage"), img);
		super.putValue("SHORT_DESCRIPTION", Language.getString("newMessage"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		super.putValue("SHORT_DESCRIPTION", enable ? Language.getString("newMessage") : "");
	}
}
