package model;

import model.enums.make_able.Resources;
import model.user.User;


public class Trade {
    private final User sender;
    private User receiver;
    private boolean isAccepted;
    private final Resources wanted;
    private final int wantedNumber;
    private String message;
    private final Resources offered;
    private final int offeredNumber;

    public Trade(Resources wanted, int wantedNumber, Resources offered, int offeredNumber, User sender, String message){
        this.wanted = wanted;
        this.wantedNumber = wantedNumber;
        this.offered = offered;
        this.offeredNumber = offeredNumber;
        this.sender = sender;
        this.message = message;
        this.isAccepted = false;
    }

    public User getSender() {
        return sender;
    }
    
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public Resources getOffered() {
        return offered;
    }

    public int getOfferedNumber() {
        return offeredNumber;
    }

    public int getWantedNumber() {
        return wantedNumber;
    }

    public Resources getWanted() {
        return wanted;
    }

    public String getMessage() {
        return message;
    }

}
