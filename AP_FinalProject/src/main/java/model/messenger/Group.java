package model.messenger;

import model.user.User;

import java.util.ArrayList;

public class Group {
    private final ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }
}
