package model.friendshiprequest;

import model.user.User;

import java.util.Currency;

public class FriendshipRequest {
    private RequestTypes requestType = RequestTypes.PENDING;
    private final User sender;
    private final User receiver;

    public FriendshipRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public RequestTypes getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypes requestType) {
        this.requestType = requestType;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getStatus(){
        if (requestType.equals(RequestTypes.PENDING))
            return "pending";
        else if (requestType.equals(RequestTypes.ACCEPTED))
            return "accepted";
        else return "denied";
    }

    public String getName(){
        if (sender.getName().equals(User.currentUser.getName()))
            return receiver.getName();
        else return sender.getName();
    }
}
