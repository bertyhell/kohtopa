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
	super(Language.getString("newMessages"), img);
    }

    public void actionPerformed(ActionEvent e) {
	JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
    }
}
