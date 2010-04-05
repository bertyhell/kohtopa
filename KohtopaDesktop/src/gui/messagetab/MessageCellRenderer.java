/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.messagetab;

import gui.addremovetab.AbstractListPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.entities.Message;

/**
 *
 * @author jelle
 */
public class MessageCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Message m = (Message) list.getModel().getElementAt(index);
		MessageListPanel p = new MessageListPanel(m);
		if (isSelected) {
			p.setBgColor(new Color(150, 150, 150));
		} else {
			if (index % 2 == 0) {
				p.setBgColor(Color.LIGHT_GRAY);
			} else {
				p.setBgColor(new Color(170, 170, 170));
			}
		}
                p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return p;
	}
}