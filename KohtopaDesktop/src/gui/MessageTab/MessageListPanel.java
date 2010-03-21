package gui.MessageTab;

import gui.AddRemoveTab.AbstractListPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
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
        if(!m.isRead())
            lblSubject.setFont(lblSubject.getFont().deriveFont(Font.BOLD+Font.ITALIC));

        buildConstraints(gbc, 0, 0, 2, 1, 100, 40, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        gbl.addLayoutComponent(lblSubject, gbc);
        this.add(lblSubject);

        //from
        JLabel lblFrom = new JLabel(from);
        if(!m.isRead())
            lblFrom.setFont(lblFrom.getFont().deriveFont(Font.BOLD+Font.ITALIC));

        buildConstraints(gbc, 0, 1, 1, 1, 49, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
        gbl.addLayoutComponent(lblFrom, gbc);
        this.add(lblFrom);

        //date
        JLabel lblDate = new JLabel(date);
        if(!m.isRead())
            lblDate.setFont(lblDate.getFont().deriveFont(Font.BOLD+Font.ITALIC));

        buildConstraints(gbc, 1, 1, 1, 1, 49, 30, GridBagConstraints.EAST, GridBagConstraints.EAST);
        gbl.addLayoutComponent(lblDate, gbc);
        this.add(lblDate);
    }
}
