package gui.messagetab;

import gui.AbstractListPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import data.entities.Message;

public class MessageListPanel extends AbstractListPanel {

	private String subject;
	private String from;
	private String date;

	/**
	 * Constructor, creates a MessageListPanel for a message
	 * @param m the messages to create the panel for
	 */
	public MessageListPanel(Message m) {
		super();
		this.subject = m.getSubject();
		this.from = m.getSender();
		this.date = m.getDateString();


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);


		// icon
		JLabel lblImage = new JLabel(new ImageIcon(getClass().getResource("/images/message_" + (m.getRead().equals("0") ? "new" : (m.getRead().equals("1") ? "read" : "reply")) + "_23.png")));
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//subject
		JLabel lblSubject = new JLabel(subject);
		if (m.getRead().equals("0")) {
			lblSubject.setFont(lblSubject.getFont().deriveFont(Font.BOLD + Font.ITALIC));
		}
		buildConstraints(gbc, 1, 0, 2, 1, 100, 40, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl.addLayoutComponent(lblSubject, gbc);
		this.add(lblSubject);

		//from
		JLabel lblFrom = new JLabel(from);
		if (m.getRead().equals("0")) {
			lblFrom.setFont(lblFrom.getFont().deriveFont(Font.BOLD + Font.ITALIC));
		}

		buildConstraints(gbc, 1, 1, 1, 1, 49, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblFrom, gbc);
		this.add(lblFrom);

		//date
		JLabel lblDate = new JLabel(date);
		if (m.getRead().equals("0")) {
			lblDate.setFont(lblDate.getFont().deriveFont(Font.BOLD + Font.ITALIC));
		}


		buildConstraints(gbc, 2, 1, 1, 1, 49, 30, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblDate, gbc);
		this.add(lblDate);
	}
}
