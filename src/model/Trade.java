package src.model;

import src.model.enums.make_able.Resources;
import src.model.user.User;


public class Trade {
    private User sender;
    private User receiver;
    private boolean isAccepted;
    private boolean wasShown;
    private Resources wanted;
    private int wantedNumber;
    private int totalResourceAmount;
    private String message;
    private Resources offered;
    private int offeredNumber;

    public Trade(Resources wanted, int wantedNumber, Resources offered, int offeredNumber){
        this.wanted = wanted;
        this.wantedNumber = wantedNumber;
        this.offered = offered;
        this.offeredNumber = offeredNumber;
        this.isAccepted = false;
        this.wasShown = false;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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

    public boolean isWasShown() {
        return wasShown;
    }

    public void setWasShown(boolean wasShown) {
        this.wasShown = wasShown;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getTotalResourceAmount() {
        return totalResourceAmount;
    }

    public void setTotalResourceAmount(int totalResourceAmount) {
        this.totalResourceAmount = totalResourceAmount;
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
}
