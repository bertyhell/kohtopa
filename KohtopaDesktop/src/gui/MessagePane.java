/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import Language.Language;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * An implementation for a messagepane
 * @author Jelle
 */
public class MessagePane extends JPanel {

    private JSplitPane splitpane;
    private JScrollPane leftpane;
    private JScrollPane rightpane;
    private JPanel toolbar;
    private Vector<Vector<String>> messagedata;
    private Vector<String> messages;
    private JTable table;
    private JTextArea text;

    /**
     * Constructs a new MessagePane, the pane contains 2 parts,
     * the first part contains a list of messages
     * the second part contains the message itself
     */
    public MessagePane() {

//        // create toolbar and add buttons
//        toolbar = new JPanel();
//
//        // add buttons
//        JButton newMessageBtn = new JButton(Main.getAction("newMessage"));
//        toolbar.add(newMessageBtn);
//
//        JButton repMessageBtn = new JButton(Main.getAction("replyMessage"));
//        toolbar.add(repMessageBtn);
//
//        JButton remMessageBtn = new JButton(Main.getAction("removeMessages"));
//        toolbar.add(remMessageBtn);




        // create the left pane (containing list of messages)
        leftpane = new JScrollPane();

        // create message vector
        messagedata = new Vector<Vector<String>>();

        // create columns vector
        Vector<String> cols = new Vector<String>();
        cols.add("title");
        cols.add("from");
        cols.add("date");

        // fill with dummy data

        for(int i=0 ; i<200 ; i++) {
            messagedata.add(new Vector<String>());
            for(int j=0; j<cols.size() ; j++)
                messagedata.get(i).add(cols.get(j)+" "+i);
        }

        // make new JTable and add to panel
        table = new MessageTable(cols,messagedata);
        



        leftpane.setViewportView(table);



        // create the right pane
        rightpane = new JScrollPane();

        messages = new Vector<String>();
        for(int i=0 ; i<200 ; i++) {
            messages.add("content "+i+"\nrandom spam!");
        }

        // textfield
        text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);

        rightpane.setViewportView(text);

        


        // make splitpane, set resizeweight to 20% left
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftpane,rightpane);
        splitpane.setResizeWeight(0.2);
        splitpane.setDividerSize(5);
        splitpane.setDividerLocation(200);



        this.setLayout(new BorderLayout());
        this.add(splitpane);
        //this.add(toolbar,BorderLayout.NORTH);

    }

    private class MessageTable extends JTable {

        public MessageTable(Vector<String> cols, Vector<Vector<String>> messagedata) {
            setModel(new DefaultTableModel(messagedata,cols));

            setAutoCreateRowSorter(true);
            setShowGrid(false);

            setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

            setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            setCellSelectionEnabled(false);
            setRowSelectionAllowed(true);
        }

        @Override
        public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
            //if(!leftpane.getValueIsAdjusting())
            text.setText(messages.get(rowIndex));
            super.changeSelection(rowIndex, columnIndex, toggle, extend);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }


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
