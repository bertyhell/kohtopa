/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author jelle
 */
public class Message {
    int recipient;
    int senderID;
    String sender;
    String subject;
    Date date;
    String text;
    String read;

    public Message(int recipient, int senderID, String sender, String subject, Date date, String text, String read) {
        this.recipient = recipient;
        this.senderID = senderID;
        this.sender = sender;
        if(subject == null || subject.equals(""))
            this.subject = "No subject";
        else
            this.subject = subject;
        this.date = date;
        this.text = text;
        this.read = read;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDateString() {
        return DateFormat.getDateInstance().format(date) + " "
                            + DateFormat.getTimeInstance().format(date);
    }

    public Date getDate() {
        return date;
    }

    public String getRead() {
        return read;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

}
