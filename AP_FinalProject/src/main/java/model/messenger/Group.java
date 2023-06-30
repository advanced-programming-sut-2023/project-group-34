package model.messenger;

import model.user.User;

import java.io.Serializable;

public class Group extends Chat implements Serializable {
    private final String name;
    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
