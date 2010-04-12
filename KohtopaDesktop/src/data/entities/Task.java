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

    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
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
