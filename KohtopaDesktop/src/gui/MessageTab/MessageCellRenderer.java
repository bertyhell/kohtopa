/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MessageTab;

import gui.AddRemoveTab.AbstractListPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.data.Message;

/**
 *
 * @author jelle
 */
public class MessageCellRenderer implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Message m = (Message) list.getModel().getElementAt(index);
        MessageListPanel p = new MessageListPanel(m);
        if(isSelected) {
            p.setBgColor(new Color(0,102,204));
        } else if(m.isRead()) {
            if (index % 2 == 0) {
                p.setBgColor(Color.LIGHT_GRAY);
            } else {
                p.setBgColor(new Color(170, 170, 170));
            }
        } else {
            p.setBgColor(new Color(255,255,150));
        }
        return p;
    }
}
