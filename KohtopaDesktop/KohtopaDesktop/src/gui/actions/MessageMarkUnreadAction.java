/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.actions;

import data.DataConnector;
import data.entities.Message;
import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author Jelle
 */
public class MessageMarkUnreadAction extends AbstractIconAction {

    public MessageMarkUnreadAction(String id, Icon img) {
        super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Message[] messages = Main.getMessagePane().getSelectedMessages();
        if (messages != null) {
            for (Message m : messages) {
                m.setRead("0");

                DataConnector.updateMessageState(m);
                Main.updatePanels();
            }
        }
    }
}
