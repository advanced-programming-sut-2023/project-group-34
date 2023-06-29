package model.messenger;

import model.user.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String message;
    public static int counter = 0;
    private final int ID;
    private boolean Seen = false;
    private boolean liked;
    private boolean disliked;
    private boolean laughed;
    private final User sender;
    private String sendTime;
    public Message(String message, User sender) {
        this.message = message;
        this.sender = sender;
        counter++;
        ID = counter;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        sendTime = dtf.format(LocalTime.now());
        liked = false;
        disliked = false;
        laughed = false;
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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public boolean isLaughed() {
        return laughed;
    }

    public void setLaughed(boolean laughed) {
        this.laughed = laughed;
    }
}
