/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import Language.Language;
import Resources.RelativeLayout;
import data.entities.Task;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Jelle
 */
public class TaskDialog extends JDialog implements ListSelectionListener {
    private static JList tasks;
    private static CustomListModel listModel;
    private JPanel buttons;
    private static CalendarModel model;
    private static TaskDialog instance = new TaskDialog(Main.getInstance());
    private static ArrayList<Task> taskList;


    /**
     * Getter for the instance of the dialog
     * @param Date the date the instance uses
     * @return the dialog screen
     * @throws IllegalAccessException thrown when setModel has not yet been called
     * @see setModel(CalendarModel model)
     */
    public static TaskDialog getInstance(Date date) throws IllegalAccessException {
        if(model == null)
            throw new IllegalAccessException("No model set!");

       
        taskList = model.getTasksForDay(date);

        listModel.clear();
        for(Task t : taskList) {
            listModel.addElement(t);
        }
        
        tasks.updateUI();
        return instance;
    }

    /**
     * Sets the model, this method has to be called before an instance can be created
     * @param model the model this dialog uses
     */
    public static void setModel(CalendarModel model) {
        TaskDialog.model = model;
    }
    
    private TaskDialog(Frame owner) {
        super(owner,Language.getString("taskAdd"),true);
        TaskDialog.model = null;

        listModel = new CustomListModel();

        JPanel content = new JPanel();

        BorderLayout bl = new BorderLayout();
        content.setLayout(bl);

        tasks = new JList(listModel);
        tasks.addListSelectionListener(this);

        content.add(new JScrollPane(tasks));


        buttons = new JPanel();
        RelativeLayout rl = new RelativeLayout(RelativeLayout.X_AXIS);
        rl.setAlignment(RelativeLayout.CENTER);

        buttons.setLayout(rl);

        buttons.add(getButton(Main.getAction("taskAdd"), "taskAdd"));
        buttons.add(getButton(Main.getAction("taskEdit"), "taskEdit"));
        buttons.add(getButton(Main.getAction("taskRemove"), "taskRemove"));
        

        //Layout.buildConstraints(gbc, 1, 0, 1, 1, 20, 30, GridBagConstraints.NONE, GridBagConstraints.CENTER);
        content.add(buttons,BorderLayout.NORTH);



        this.setContentPane(content);
        //this.setLocationByPlatform(true);
        this.pack();
        this.setLocationRelativeTo(owner);
    }


    /**
     * Makes a button
     * @param action action for button
     * @param langstring tooltip string entry in language file
     * @return the button
     */
        private JButton getButton(Action action, String langstring) {
            JButton btn = new JButton(action);
            btn.setHideActionText(true);
            btn.setToolTipText(Language.getString(langstring));

            return btn;
        }

    public void valueChanged(ListSelectionEvent e) {

    }

    private class CustomListModel extends AbstractListModel {
        private ArrayList<Task> list;

        public CustomListModel() {
            
           list = new ArrayList<Task>();
        }

        public void clear() {
            list.clear();
        }

        public void addElement(Task t) {
            // i am going to assume the list wont be that big that efficiency matters
            // here, 100000+ tasks in one day is highly unlikely :)
            list.add(t);
            System.out.println(list.size());
            Collections.sort(list);
        }

        @Override
        public int getSize() {
            return list.size();
        }


        @Override
        public Object getElementAt(int index) {
            return list.get(index);
        }

    }


}
