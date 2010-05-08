/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import Language.Language;
import data.entities.Task;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Vector;
import java.util.Collections;
import java.util.Date;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Class TaskDialog, creates a dialog that shows the tasks for the selected day
 * @author Jelle
 */
public class TaskDialog extends JDialog implements ListSelectionListener {

    private static JList tasks;
    private static CustomListModel listModel;
    private Box buttons;
    private static CalendarModel model;
    private static TaskDialog instance = new TaskDialog(Main.getInstance());
    private static Vector<Task> taskList;
    private static Date date;

    /**
     * Getter for the instance of the dialog
     * @param Date the date the instance uses
     * @return the dialog screen
     * @throws IllegalAccessException thrown when setModel has not yet been called
     * @see setModel(CalendarModel model)
     */
    public static TaskDialog getInstance(Date date) throws IllegalAccessException {
        if (model == null) {
            throw new IllegalAccessException("No model set!");
        }

        TaskDialog.date = date;
        taskList = model.getTasksForDay(date);

        listModel.clear();
        for (Task t : taskList) {
            listModel.addElement(t);
        }

        tasks.updateUI();
        return instance;
    }

    /**
     * Updates the dialog to reflect the new situation
     */
    public static void update() {
        try {
            TaskDialog.getInstance(date);
        } catch (IllegalAccessException ex) {
            
        }
    }

    /**
     * Getter for the date
     * @return the date of the dialog
     */
    public static Date getDate() {
        return TaskDialog.date;
    }

    /**
     * Getter for the model of the dialog
     * @return the model of the dialog
     */
    public static CalendarModel getModel() {
        return TaskDialog.model;
    }

    /**
     * Getter for the selected task
     * @return the selected task, null if none are selected
     */
    public static Task getSelectedTask() {
        return (Task) tasks.getSelectedValue();
    }

    /**
     * Sets the model, this method has to be called before an instance can be created
     * @param model the model this dialog uses
     */
    public static void setModel(CalendarModel model) {
        TaskDialog.model = model;
    }

    /**
     * Constructor, creates a new TaskDialog with the given owner
     * @param owner the owner of the TaskDialog
     */
    private TaskDialog(Frame owner) {
        super(owner, Language.getString("taskAdd"), true);
        TaskDialog.model = null;
		this.setIconImage(new ImageIcon(getClass().getResource("/images/task_23.png")).getImage());

        listModel = new CustomListModel();

        JPanel content = new JPanel();

        date = new Date(System.currentTimeMillis());
        BorderLayout bl = new BorderLayout();
        content.setLayout(bl);

        tasks = new JList(listModel);
        tasks.addListSelectionListener(this);

        content.add(new JScrollPane(tasks));


        buttons = Box.createHorizontalBox();
        buttons.add(getButton(Main.getAction("taskAdd"), "taskAdd"));
        buttons.add(getButton(Main.getAction("taskEdit"), "taskEdit"));
        buttons.add(getButton(Main.getAction("taskRemove"), "taskRemove"));

        content.add(buttons, BorderLayout.NORTH);



        this.setContentPane(content);
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

    /**
     * Listener for a change in the listValue
     * @param e
     */
    public void valueChanged(ListSelectionEvent e) {
        Main.getAction("taskEdit").setEnabled(e.getFirstIndex() != -1);
        Main.getAction("taskRemove").setEnabled(e.getFirstIndex() != -1);

    }

    /**
     * Class CustomListModel, this is a model that orders the tasks based on
     * the comparator that tasks have, adding an element is O(n*log(n)), where
     * n is small (n = amount of tasks already present for that day)
     */
    private class CustomListModel extends AbstractListModel {

        private Vector<Task> list;

        /**
         * Creates a new CustomListModel
         */
        public CustomListModel() {

            list = new Vector<Task>();
        }

        /**
         * Clears the model
         */
        public void clear() {
            list.clear();
        }

        /**
         * Add a task to the model, efficiency O(n*log(n)), since there are never
         * many tasks, this does not matter much
         * @param t
         */
        public void addElement(Task t) {
            // i am going to assume the list wont be that big that efficiency matters
            // here, 100000+ tasks in one day is highly unlikely :)
            list.add(t);
            Collections.sort(list);
        }

        /**
         * Getter for the size of the table of the model
         * @return the size of the table of the model
         */
        @Override
        public int getSize() {
            return list.size();
        }

        /**
         * Getter for the element at index index
         * @param index the index of the needed element
         * @return the element at place index
         */
        @Override
        public Object getElementAt(int index) {
            if(index >= list.size())
                return null;
            return list.get(index);
        }
    }
}
