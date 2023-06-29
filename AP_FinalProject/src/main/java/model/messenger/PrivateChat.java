package model.messenger;

import model.user.User;

import java.util.ArrayList;

public class PrivateChat {
    private final ArrayList<User> users;

    public PrivateChat(ArrayList<User> users) {
        this.users = users;
    }
    public ArrayList<User> getUsers() {
        return users;
    }

}
