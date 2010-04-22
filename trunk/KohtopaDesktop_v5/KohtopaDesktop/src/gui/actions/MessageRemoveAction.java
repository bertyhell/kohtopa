package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class MessageRemoveAction extends AbstractIconAction {

    public MessageRemoveAction(String id, Icon img) {
        super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.getMessagePane().removeSelectedMessage();
    }
}
