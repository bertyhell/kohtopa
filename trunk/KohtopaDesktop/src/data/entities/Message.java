/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

/**
 *
 * @author jelle
 */
public class Message {
    int recipient;
    String sender;
    String subject;
    String date;
    String text;
    String read;

    public Message(int recipient, String sender, String subject, String date, String text, String read) {
        this.recipient = recipient;
        this.sender = sender;
        if(subject == null || subject.equals(""))
            this.subject = "No subject";
        else
            this.subject = subject;
        this.date = date;
        this.text = text;
        this.read = read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDate() {
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
