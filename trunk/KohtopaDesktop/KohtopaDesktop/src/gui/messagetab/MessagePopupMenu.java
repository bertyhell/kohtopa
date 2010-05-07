package gui.messagetab;

import gui.Main;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Jelle
 */
public class MessagePopupMenu extends JPopupMenu {

    public MessagePopupMenu() {
        super("Message");
        this.add(new JMenuItem(Main.getAction("messageReply")));
        this.add(new JMenuItem(Main.getAction("messageMarkUnread")));

    }

}
