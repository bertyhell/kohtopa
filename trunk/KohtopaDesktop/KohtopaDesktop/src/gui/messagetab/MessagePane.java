/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.messagetab;

import Language.Language;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import data.DataConnector;
import data.ProgramSettings;
import data.entities.Message;
import gui.Main;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MessagePane, this is used for the tab messages
 * @author Jelle
 */
public class MessagePane extends JPanel implements MouseListener {

	private JSplitPane splitpane;
	private JScrollPane leftpane;
	private JScrollPane rightpane;
	private Vector<Message> messages;
	private Message selectedMessage;
	private JList list;
	private JTextArea text;

	/**
	 * Constructs a new MessagePane, the pane contains 2 parts,
	 * the first part contains a list of messages
	 * the second part contains the message itself
	 */
	public MessagePane() {

		// create the left pane (containing list of messages)
		leftpane = new JScrollPane();


		// make new JList and add to panel
		messages = DataConnector.getMessageData(ProgramSettings.getOwnerID());
		list = new JList(messages);

		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				Main.getAction("messageReply").setEnabled(list.getSelectedIndex() != -1);
				Main.getAction("messageMarkUnread").setEnabled(list.getSelectedIndex() != -1);
				if (list.getSelectedIndex() != -1) {
					Message m = (Message) list.getSelectedValue();
					text.setText(m.getText());

					text.setCaretPosition(0);

					selectedMessage = m;

					if (m.getRead().equals("0")) {
						m.setRead("1");
						DataConnector.updateMessageState(m);
					}
				}

			}
		});
		list.setBackground(new Color(217, 217, 217));
		list.setCellRenderer(new MessageCellRenderer());

		leftpane.setViewportView(list);

		// create the right pane
		rightpane = new JScrollPane();


		// textfield
		text = new JTextArea();
		text.setEditable(false);
		text.setLineWrap(true);



		rightpane.setViewportView(text);






		// make splitpane, set resizeweight to 20% left
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpane, rightpane);
		splitpane.setDividerSize(5);
		splitpane.setResizeWeight(0.2);
		splitpane.setDividerLocation(350);



		this.setLayout(new BorderLayout());
		this.add(splitpane);

		Main.getAction("messageReply").setEnabled(list.getSelectedIndex() != -1);
		Main.getAction("messageMarkUnread").setEnabled(list.getSelectedIndex() != -1);
		//this.add(toolbar,BorderLayout.NORTH);
		list.addMouseListener(this);

	}

	/**
	 * Getter for the selected message
	 * @return the selected message
	 */
	public Message getSelectedMessage() {
		return selectedMessage;
	}

	/**
	 * Returns the selected messages
	 * @return the selected messages, null if none are selected
	 */
	public Message[] getSelectedMessages() {
		Object[] entries = list.getSelectedValues();
		if (entries == null) {
			return null;
		}
		Message[] msgs = new Message[entries.length];
		for (int i = 0; i < entries.length; i++) {
			msgs[i] = (Message) entries[i];
		}
		return msgs;
	}

	/**
	 * Makes a button
	 * @param action action for button
	 * @param langstring tooltip string entry in language file
	 * @return the button
	 */
	private JButton getButton(AbstractAction action, String langstring) {
		JButton btn = new JButton(action);
		btn.setHideActionText(true);
		btn.setToolTipText(Language.getString(langstring));

		return btn;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			MessagePopupMenu menu = new MessagePopupMenu();
			menu.show(list, e.getX(), e.getY());
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
