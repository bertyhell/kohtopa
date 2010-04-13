/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Jelle
 */
public class Task implements Comparable<Task> {
    private String description;
    private Date date;
    private Date end;
    private Integer id;
    private Integer rentableID;
    private Integer repeats;

    public Task(int id, int rentableID, String description, Date start, Date end, int repeats) {
        this.description = description;
        this.date = start;
        this.end = end;
        this.id = id;
        this.rentableID = rentableID;
        this.repeats = repeats;
        //System.out.println("nex task: "+date);
    }

    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
        this.end = date;
        id = null;
        rentableID = null;
        repeats = null;
    }

    public Integer getRepeats() {
        return repeats;
    }

    public void setRepeats(Integer repeats) {
        this.repeats = repeats;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getRentableID() {
        return rentableID;
    }

    public void setRentableID(int rentableID) {
        this.rentableID = rentableID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date)+": "+description;
    
        //return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date)+": "+description;
    }


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
