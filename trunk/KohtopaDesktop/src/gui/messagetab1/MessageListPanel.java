package gui.messagetab1;

import gui.addremovetab1.AbstractListPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import data.entities.Message;

public class MessageListPanel extends AbstractListPanel {

    private String subject;
    private String from;
    private String date;
    private static HashMap<String, ImageIcon> icons =
            new HashMap<String, ImageIcon>();

    public static void addIcon(String type, ImageIcon img) {
        icons.put(type, img);
    }

    public MessageListPanel(Message m) {
        super();
        this.subject = m.getSubject();
        this.from = m.getSender();
        this.date = m.getDate();


        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        //adding information labels


        // icon
        JLabel lblImage = new JLabel(icons.get(m.getRead()));
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
