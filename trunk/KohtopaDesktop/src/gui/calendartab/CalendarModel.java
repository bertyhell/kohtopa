/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import data.entities.Task;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
    public static final long CONVERTMSTODAY = 86400000;
    CalendarPanel pane;
    HashMap<Long,ArrayList<Task>> tasks;

    protected CalendarModel(int year, int month, CalendarPanel panel) {
        super(year, month, 1);
        this.pane = panel;
        tasks = new HashMap<Long,ArrayList<Task>>();

        // testcase, should later get data from database(when there are tasks
        // in it :))
        // in the database implementation they should be in order
        this.add(Calendar.HOUR, 5);
        addTask(new Task("test3", this.getTime()));
        this.add(Calendar.HOUR, -1);
        addTask(new Task("test20342°4831840918240980409217804971409704720", this.getTime()));
        this.add(Calendar.HOUR, -1);
        addTask(new Task("test1", this.getTime()));
        this.add(Calendar.HOUR, 4);
        addTask(new Task("test4", this.getTime()));
        this.add(Calendar.DATE, 2);
        addTask(new Task("nieuwe dag", this.getTime()));
        this.set(Calendar.HOUR, 0);
        this.add(Calendar.DATE, -2);




    }

    public void addTask(Task task) {
        long key = task.getDate().getTime()/CONVERTMSTODAY;
        if(!tasks.containsKey(key)) {
            System.out.println("new date found");
            tasks.put(key,new ArrayList<Task>());
        }
        
        tasks.get(key).add(task);
    }

    public void removeTask( Task task) {
        long key = task.getDate().getTime()/CONVERTMSTODAY;
        if(tasks.containsKey(key))
            tasks.get(key).remove(task);
    }

    public void updateTask(Date date, Task task, Task newTask) {
        long key = task.getDate().getTime()/CONVERTMSTODAY;
        if(tasks.containsKey(key)) {
            tasks.get(key).remove(task);
            tasks.get(key).add(newTask);
        }
    }

    public boolean hasTasksForDay(Date day) {
        long key = day.getTime()/CONVERTMSTODAY;
        return tasks.containsKey(key);

    }

    public HashMap<Long,ArrayList<Task>> getTasks() {
        return tasks;
    }

    /**
     * Getter for tasks for a day
     * @param day the day, hours, minutes, seconds and miliseconds do not matter
     * @return arrayList containing tasks for that day, this will be an empty
     * array if there are no tasks
     */
    public ArrayList<Task> getTasksForDay(Date day) {
        long key = day.getTime()/CONVERTMSTODAY;
        if(tasks.containsKey(key))
            return tasks.get(key);
        return new ArrayList<Task>();
    }

    public void setDate(int year, int month) {
        this.set(CalendarModel.YEAR, year);
        this.set(CalendarModel.MONTH, month);
        pane.updatePage();
    }

    public void changeDate(int nYears, int nMonths) {
        this.add(CalendarModel.YEAR, nYears);
        this.add(CalendarModel.MONTH, nMonths);
        pane.updatePage();
    }

    public Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.getTime());
        return c;
    }

    public void stateChanged(ChangeEvent e) {
        JSpinner s = (JSpinner) e.getSource();
        SpinnerDateModel sModel = (SpinnerDateModel)s.getModel();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sModel.getDate().getTime());
        setDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH));
        
    }



}
