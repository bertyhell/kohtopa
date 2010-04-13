/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import data.entities.Task;
import java.util.ArrayList;
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
    HashMap<Integer,ArrayList<Task>> tasks;

    protected CalendarModel(int year, int month, CalendarPanel panel) {
        super(year, month, 1);
        this.calendarPanel = panel;
        tasks = new HashMap<Integer,ArrayList<Task>>();
        Calendar c = this.getCalendar();

        // testcase, should later get data from database(when there are tasks
        // in it :))
        // in the database implementation they should be in order
        c.add(Calendar.HOUR, 5);
        addTask(new Task("test3", c.getTime()));
        c.add(Calendar.HOUR, -24);
        addTask(new Task("test20342°4831840918240980409217804971409704720", c.getTime()));
        c.add(Calendar.HOUR, -24);
        addTask(new Task("test1", c.getTime()));
        c.add(Calendar.HOUR, 4);
        addTask(new Task("test4", c.getTime()));
        c.add(Calendar.DATE, 2);
        addTask(new Task("nieuwe dag", c.getTime()));




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
            tasks.put(key,new ArrayList<Task>());
        }
        
        tasks.get(key).add(task);

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
            tasks.get(key).add(newTask);
        }
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
    private int getKey(Date day) {

        Calendar c = Calendar.getInstance();
        c.setTime(day);
        int key = c.get(Calendar.DATE);
        key += c.get(Calendar.MONTH)*100;
        key += c.get(Calendar.YEAR)*10000;
        //System.out.println(key);

        return key;
    }

    /**
     * Getter for all the tasks
     * @return the map representing all the tasks, keys are of form yyyymmdd
     */
    public HashMap<Integer,ArrayList<Task>> getTasks() {
        return tasks;
    }

    /**
     * Getter for tasks for a day
     * @param day the day, hours, minutes, seconds and miliseconds do not matter
     * @return arrayList containing tasks for that day, this will be an empty
     * array if there are no tasks
     */
    public ArrayList<Task> getTasksForDay(Date day) {
        int key = getKey(day);
        if(tasks.containsKey(key))
            return tasks.get(key);
        //System.out.println("no tasks found");
        return new ArrayList<Task>();
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
