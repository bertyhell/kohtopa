/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.messagetab;

import data.DataConnector;
import data.ProgramSettings;
import data.entities.Message;
import data.entities.Person;
import gui.Layout;
import gui.Main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jelle
 */
public class ComposeMessageDialog extends JDialog {
    private JComboBox renters;
    private JTextField subject;
    private JTextArea message;

    private static ComposeMessageDialog instance = new ComposeMessageDialog();

    public static ComposeMessageDialog getInstance() {
        instance.subject.setText("");
        instance.message.setText("");
        return instance;
    }

    private ComposeMessageDialog() {
        JPanel content = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = gbl.getConstraints(content);
        content.setLayout(gbl);

        //GridBagConstraints gbc, int x, int y, int w, int h, int wx, int wy, int fill, int anchor) {
        Layout.buildConstraints(gbc, 0, 0, 1, 1, 10, 10, GridBagConstraints.NONE, GridBagConstraints.EAST);
        content.add(new JLabel("To: ",JLabel.LEFT),gbc);

        // combobox with renters
        ArrayList<Person> renterList = DataConnector.getRenters(ProgramSettings.getUserID());
        renters = new JComboBox();

        for(Person p:renterList) {
            renters.addItem(p);
        }

        Layout.buildConstraints(gbc, 1, 0, 1, 1, 30, 10, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        content.add(renters,gbc);


        Layout.buildConstraints(gbc, 0, 1, 1, 1, 10, 10, GridBagConstraints.NONE, GridBagConstraints.EAST);
        content.add(new JLabel("Subject: ",JLabel.LEFT),gbc);

        subject = new JTextField();
        Layout.buildConstraints(gbc, 1, 1, 1, 1, 30, 10, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        content.add(subject, gbc);


        Layout.buildConstraints(gbc, 0, 2, 1, 1, 10, 10, GridBagConstraints.NONE, GridBagConstraints.WEST);
        content.add(new JLabel("Message: "),gbc);

        message = new JTextArea();
        message.setLineWrap(true);
        message.setPreferredSize(new Dimension(300,300));
        Layout.buildConstraints(gbc, 0, 3, 2, 10, 40, 500, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        content.add(new JScrollPane(message),gbc);


        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //int recipient, int senderID, String sender, String subject, Date date, String text, String read) {

                DataConnector.sendMessage(
                        new Message(((Person)renters.getSelectedItem()).getId(),
                        ProgramSettings.getUserID(),
                        ProgramSettings.getUsername(),
                        subject.getText(),
                        new Date(System.currentTimeMillis()),
                        message.getText(),
                        "0"));

                instance.setVisible(false);
            }
        });

        Layout.buildConstraints(gbc, 0, 13, 2, 10, 40, 10, GridBagConstraints.NONE, GridBagConstraints.CENTER);
        content.add(send,gbc);

        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        this.setContentPane(content);
        this.pack();
        this.setLocationRelativeTo(Main.getInstance());
    }

}
