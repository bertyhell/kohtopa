/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.MessageTab;

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
import data.entities.Message;

/**
 * An implementation for a messagepane
 * @author Jelle
 */
public class MessagePane extends JPanel {

    private JSplitPane splitpane;
    private JScrollPane leftpane;
    private JScrollPane rightpane;
    private Vector<String> messages;
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
        list = new JList(DataConnector.getMessageData());

        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if(list.getSelectedValue() != null) {
                    Message m = (Message)list.getSelectedValue();
                    text.setText(m.getText());
                    m.setRead("1");
                }

            }
        });
        list.setBackground(new Color(217, 217, 217));
        list.setCellRenderer(new MessageCellRenderer());

        leftpane.setViewportView(list);

        // create the right pane
        rightpane = new JScrollPane();

        messages = new Vector<String>();
        for (int i = 0; i < 200; i++) {
            messages.add("content " + i + "\nrandom spam!");
        }

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
        //splitpane.setDividerLocation();



        this.setLayout(new BorderLayout());
        this.add(splitpane);
        //this.add(toolbar,BorderLayout.NORTH);

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
}
