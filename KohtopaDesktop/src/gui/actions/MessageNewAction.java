package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import Language.Language;
import javax.swing.ImageIcon;

/**
 *
 * @author bert
 */
public class MessageNewAction extends AbstractAction {

	public MessageNewAction() {
		super(Language.getString("newMessage"));
                this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(getClass().getResource("/images/message_new_23.png")));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
	}
}