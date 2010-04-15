/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

import java.text.DateFormat;
import java.util.Date;

/**
 * Class task, this represents a task from the calendar
 * @author Jelle
 */
public class Task implements Comparable<Task> {
    private String description;
    private Date date;
    private Date end;
    private Integer id;
    private Integer rentableID;
    private Integer repeats;

    /**
     * Constructor, creates a task with the given parameters
     * @param id the id of the task, to insert you can just set to any integer,
     * the database picks an id for you
     * @param rentableID the id of the rentable to which this task belongs
     * @param description description of the task
     * @param start start time of task
     * @param end end time of task
     * @param repeats amount of time when task repeats, this is currently not used
     */
    public Task(int id, int rentableID, String description, Date start, Date end, int repeats) {
        this.description = description;
        this.date = start;
        this.end = end;
        this.id = id;
        this.rentableID = rentableID;
        this.repeats = repeats;
    }

    /**
     * Constructor for a task, provided for easy construction
     * @param description description of the task
     * @param date date of the task
     */
    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
        this.end = date;
        id = null;
        rentableID = null;
        repeats = null;
    }

    /**
     * Getter for the repeats of a task
     * @return the repeats of a task
     */
    public int getRepeats() {
        return repeats;
    }

    /**
     * Setter for the repeats of a task
     * @param repeats the repeats of a task
     */
    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    /**
     * Getter for the end date of the task
     * @return the end date of the task
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Setter for the end date of the task
     * @param end the end date of the task
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Getter for the rentableID
     * @return the rentableID
     */
    public int getRentableID() {
        return rentableID;
    }

    /**
     * Setter for the rentableiID
     * @param rentableID the new rentableID
     */
    public void setRentableID(int rentableID) {
        this.rentableID = rentableID;
    }

    /**
     * Setter for the taskID
     * @param id the new taskID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the taskID
     * @return the taskID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the starting date of the task
     * @return the starting date of the task
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the starting date of the task
     * @param date the new starting date of the task
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for the description of the task
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the task
     * @param description the new description of the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the task
     * @return a string of form: HH:MM: description
     */
    @Override
    public String toString() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date)+": "+description;
    
        //return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date)+": "+description;
    }


    /**
     * Compares 2 task, first compares the dates, if they are equal, compares
     * the descriptions
     * @param o other task
     * @return negateve number if task should be shown before the other task
     */
    public int compareTo(Task o) {
        if(this.getDate().before(o.getDate())) {
            return -1;
        } else if(o.getDate() == this.getDate()) {
            return this.getDescription().compareTo(o.getDescription());
        } else {
            return 1;
        }
    }

}
