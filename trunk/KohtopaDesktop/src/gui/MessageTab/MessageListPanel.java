package gui.MessageTab;

import gui.addremove.AbstractListPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import model.data.Message;

public class MessageListPanel extends AbstractListPanel {

    private String subject;
    private String from;
    private String date;

    public MessageListPanel(Message m) {
        super();
        this.subject = m.getSubject();
        this.from = m.getSender();
        this.date = m.getDate();


        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        //adding information labels


        //subject
        JLabel lblSubject = new JLabel(subject);

        buildConstraints(gbc, 0, 0, 2, 1, 100, 40, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        gbl.addLayoutComponent(lblSubject, gbc);
        this.add(lblSubject);

        //from
        JLabel lblFrom = new JLabel(from);
        buildConstraints(gbc, 0, 1, 1, 1, 48, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
        gbl.addLayoutComponent(lblFrom, gbc);
        this.add(lblFrom);

        //date
        JLabel lblDate = new JLabel(date);
        buildConstraints(gbc, 1, 1, 1, 1, 48, 30, GridBagConstraints.EAST, GridBagConstraints.EAST);
        gbl.addLayoutComponent(lblDate, gbc);
        this.add(lblDate);
    }

}
