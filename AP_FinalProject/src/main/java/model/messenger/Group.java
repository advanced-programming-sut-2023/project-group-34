package model.messenger;

import model.user.User;

import java.util.ArrayList;

public class Group extends Chat{
    private final ArrayList<User> users = new ArrayList<>();
    private final String name;

    public Group(String name){
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;

    }

    public String getName() {
        return name;
    }
}
