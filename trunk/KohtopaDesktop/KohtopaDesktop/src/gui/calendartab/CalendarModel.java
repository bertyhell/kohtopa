/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import data.DataConnector;
import data.entities.Task;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents a model for a CalendarPanel, it listens to the spinner,
 * functions as a Calendar and keeps track of tasks, tasks are put in a HashMap
 * with as key time(ms)/86400000(this represents days since beginning of calendar),
 * this cannot be a date because  a date has hours too.
 * @author Jelle
 */
public class CalendarModel extends GregorianCalendar implements ChangeListener {
    //public static final long CONVERTMSTODAY = 86400000;
    CalendarPanel calendarPanel;
    HashMap<Integer,Vector<Task>> tasks;

    /**
     * Creates a calendarModel with the given year, month, monitoring the given
     * CalendarPanel
     * @param year the year
     * @param month the month
     * @param panel the CalendarPanel to monitor
     */
    protected CalendarModel(int year, int month, CalendarPanel panel) {
        super(year, month, 1);
        this.calendarPanel = panel;
        tasks = new HashMap<Integer,Vector<Task>>();
        
        tasks = DataConnector.getTasks();
    }

    /**
     * Add a task to the model(and to the database)
     * @param task the task to add
     */
    public void addTask(Task task) {
        if(task == null)
            return;

        int key = getKey(task.getDate());
        if(!tasks.containsKey(key)) {
            //System.out.println("new date found: "+key);
            tasks.put(key,new Vector<Task>());
        }

        tasks.get(key).add(task);
        DataConnector.insertTask(task);

        calendarPanel.updatePage();
    }

    /**
     * Remove a Task from the model(and from the database)
     * @param task the task to remove
     */
    public void removeTask( Task task) {
        if(task == null)
            return;

        int key = getKey(task.getDate());
        if(tasks.containsKey(key))
            tasks.get(key).remove(task);

        DataConnector.removeTask(task);
        calendarPanel.updatePage();
    }

    /**
     * Update a task in the model(and the database)
     * @param task the task to change
     * @param newTask the new task
     */
    public void updateTask(Task task, Task newTask) {
        if(task == null || newTask == null)
            return;

        int key = getKey(task.getDate());
        if(tasks.containsKey(key)) {
            tasks.get(key).remove(task);
            // add new task
            key = getKey(newTask.getDate());
            if(!tasks.containsKey(key)) {
                tasks.put(key,new Vector<Task>());
            }
            tasks.get(key).add(newTask);
        }
        DataConnector.updateTask(task, newTask);
        calendarPanel.updatePage();
    }

    /**
     * Utility method, checks if there are tasks for a day
     * @param day the day to test
     * @return true if there are tasks for that day
     */
    public boolean hasTasksForDay(Date day) {
        int key = getKey(day);
        return tasks.containsKey(key);
    }

    /**
     * Utility method, creates a key for a date
     * @param day the day to make a key for
     * @return a key, formed as an int: yyyymmdd
     */
    public static int getKey(Date day) {

        Calendar c = Calendar.getInstance();
        c.setTime(day);
        int key = c.get(Calendar.DATE);
        key += c.get(Calendar.MONTH)*100;
        key += c.get(Calendar.YEAR)*10000;
        //System.out.println(key);

        return key;
    }

    /**
     * Getter for the tasks
     * @return the map representing all the tasks, keys are of form yyyymmdd
     */
    public HashMap<Integer,Vector<Task>> getTasks() {
        return tasks;
    }

    /**
     * Setter for all tasks
     * @param tasks the hashmap containing the tasks
     */
    public void setTasks(HashMap<Integer,Vector<Task>> tasks) {
        this.tasks = tasks;
    }

    /**
     * Getter for tasks for a day
     * @param day the day, hours, minutes, seconds and miliseconds do not matter
     * @return Vector containing tasks for that day, this will be an empty
     * array if there are no tasks
     */
    public Vector<Task> getTasksForDay(Date day) {
        int key = getKey(day);
        if(tasks.containsKey(key))
            return tasks.get(key);
        //System.out.println("no tasks found");
        return new Vector<Task>();
    }

    /**
     * Setter for the date of the model
     * @param year the year
     * @param month the month
     */
    public void setDate(int year, int month) {
        this.set(CalendarModel.YEAR, year);
        this.set(CalendarModel.MONTH, month);
        calendarPanel.updatePage();
    }

    /**
     * Changes the date of the model
     * @param nYears amount of added years(can be negative for substraction)
     * @param nMonths amount of added months(can be negative for substraction)
     */
    public void changeDate(int nYears, int nMonths) {
        this.add(CalendarModel.YEAR, nYears);
        this.add(CalendarModel.MONTH, nMonths);
        calendarPanel.updatePage();
    }

    /**
     * Returns a Calendar with current date the date the model is set to, use this
     * if you want to temporarily change the date without changin the model
     * @return an instance of Calendar set to the time of the model
     */
    public Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.getTime());
        return c;
    }

    /**
     * Listener that checks if the spinner of the CalendarPanel has changed
     * @param e
     */
    public void stateChanged(ChangeEvent e) {
        JSpinner s = (JSpinner) e.getSource();
        SpinnerDateModel sModel = (SpinnerDateModel)s.getModel();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sModel.getDate().getTime());
        setDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH));

    }



}
