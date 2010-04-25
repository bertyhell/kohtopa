/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.messagetab;

import data.DataConnector;
import data.entities.Message;
import gui.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Jelle
 */
public class MessagePopupMenu extends JPopupMenu {

    public MessagePopupMenu() {
        super("Message");
        this.add(new JMenuItem(Main.getAction("messageRemove")));
        this.add(new JMenuItem(Main.getAction("messageReply")));
        this.add(new JMenuItem(Main.getAction("messageMarkUnread")));

    }

}
