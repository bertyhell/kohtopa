package gui.actions;

import gui.Main;
import gui.messagetab.ComposeMessageDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class MessageReplyAction extends AbstractIconAction {

    public MessageReplyAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComposeMessageDialog.getInstance(Main.getMessagePane().getSelectedMessage()).setVisible(true);
    }
}