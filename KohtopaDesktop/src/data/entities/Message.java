/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

import java.text.DateFormat;
import java.util.Date;

/**
 * Class Message, represents a message sent, contains data needed to add message
 * to the database.
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

    /**
     * Creates a new message with the data provided.
     * @param recipient ID of the recipient of the message
     * @param senderID ID of the sender of the message
     * @param sender name of the sender of the message, used to display the message
     * @param subject subject
     * @param date date the message was sent, this is a Timestamp in database,
     * @param text the message text
     * @param read state of the message:
     * 0: unread
     * 1: read
     * 2: replied
     */
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

    /**
     * Getter for sender ID
     * @return id of sender
     */
    public int getSenderID() {
        return senderID;
    }

    /**
     * Setter for sender ID
     * @param senderID new id of sender
     */
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    /**
     * Setter for read
     * @param read new value of read, 0: unread, 1: read, 2: replied
     */
    public void setRead(String read) {
        this.read = read;
    }

    /**
     * Getter for a string representation of the date of the message
     * @return the date the message was sent, format: day-month-year HH:MM:SS
     */
    public String getDateString() {
        return DateFormat.getDateInstance().format(date) + " "
                            + DateFormat.getTimeInstance().format(date);
    }

    /**
     * Getter for the date of the message
     * @return the date when the message was sent
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the read attribute of the message
     * @return the read attribute of the message (0: unread, 1: read, 2: replied)
     */
    public String getRead() {
        return read;
    }

    /**
     * Getter for the recipientID
     * @return the ID of the recipient
     */
    public int getRecipient() {
        return recipient;
    }

    /**
     * Getter for the name of the sender of the message
     * @return the name of the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Getter for subject of the message
     * @return the subject of the message
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Getter for the text of the message
     * @return the text of the message
     */
    public String getText() {
        return text;
    }

}
