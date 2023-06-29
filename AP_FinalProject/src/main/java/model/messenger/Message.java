package model.messenger;

import model.user.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String message;
    public static int counter = 0;
    private final int ID;
    private boolean Seen = false;
    private final User sender;
    private String sendTime;
    public Message(String message, User sender) {
        this.message = message;
        this.sender = sender;
        counter++;
        ID = counter;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        sendTime = dtf.format(LocalTime.now());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getID() {
        return ID;
    }

    public boolean isSeen() {
        return Seen;
    }

    public void setSeen(boolean seen) {
        Seen = seen;
    }

    public User getSender() {
        return sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
