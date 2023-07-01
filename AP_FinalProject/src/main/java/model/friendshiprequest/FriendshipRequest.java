package model.friendshiprequest;

import model.user.User;

public class FriendshipRequest {
    private RequestTypes requestType = RequestTypes.PENDING;
    private final User sender;
    private final User receiver;
    public static int counter = 0;
    private final int ID;

    public FriendshipRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        ID = ++counter;
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

    public int getID() {
        return ID;
    }
}