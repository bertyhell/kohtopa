package gui.actions;

import gui.messagetab.ComposeMessageDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class MessageNewAction extends AbstractIconAction {

    public MessageNewAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ComposeMessageDialog.getInstance().setVisible(true);
    }
}