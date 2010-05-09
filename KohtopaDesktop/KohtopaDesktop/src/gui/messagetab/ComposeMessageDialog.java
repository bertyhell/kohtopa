/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.messagetab;

import Language.Language;
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
import java.util.Date;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ComposeMessageDialog class, this is used to write a message to somebody
 * @author jelle
 */
public class ComposeMessageDialog extends JDialog {
    private JComboBox renters;
    private JTextField subject;
    private JTextArea message;
    private Message original;

    private static ComposeMessageDialog instance = new ComposeMessageDialog();

    /**
     * Returns the standard instance, this is the normal compose message dialog
     * @return an instance of the dialog
     */
    public static ComposeMessageDialog getInstance() {
        return getInstance(null);
    }

    /**
     * Returns an instance of ComposeMessageDialog, this can be both a reply
     * message dialog or a send message dialog
     * @param original the original message that you want to reply to, if this
     * is null its a normal compose message dialog, this is the same as calling
     * getInstance()
     * @return an instance of the dialog
     */
    public static ComposeMessageDialog getInstance(Message original) {
        instance.original = original;
        if(original == null) {
            instance.renters.setEnabled(true);
            instance.subject.setText("");
        } else {
            instance.renters.setEnabled(false);
            int i=0;

            // find the renter that needs to be replied to
            while(i<instance.renters.getItemCount() && !instance.renters.getItemAt(i).toString().contains(original.getSender())) {

                i++;
            }
            
            
            // if there is a renter found, set the renter to it
            if(i<instance.renters.getItemCount())
                instance.renters.setSelectedIndex(i);
            instance.subject.setText("Re: "+original.getSubject());
        }
        // clear message field
        instance.message.setText("");
        return instance;
    }

    /**
     * Creates a new ComposeMessageDialog
     */
    private ComposeMessageDialog() {
		this.setIconImage(new ImageIcon(getClass().getResource("/images/message_new_23.png")).getImage());
        this.setTitle(Language.getString("newMessage"));
		JPanel content = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = gbl.getConstraints(content);
        content.setLayout(gbl);

        //GridBagConstraints gbc, int x, int y, int w, int h, int wx, int wy, int fill, int anchor) {
        Layout.buildConstraints(gbc, 0, 0, 1, 1, 10, 10, GridBagConstraints.NONE, GridBagConstraints.EAST);
        content.add(new JLabel("To: ",JLabel.LEFT),gbc);

        // combobox with renters
        Vector<Person> renterList = DataConnector.getRenters(ProgramSettings.getOwnerID());
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
                        ProgramSettings.getOwnerID(),
                        ProgramSettings.getUsername(),
                        subject.getText(),
                        new Date(System.currentTimeMillis()),
                        message.getText(),
                        "0"));
                if(original != null) {
                    original.setRead("2");

                    DataConnector.updateMessageState(original);
                    Main.updatePanels();
                }
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
