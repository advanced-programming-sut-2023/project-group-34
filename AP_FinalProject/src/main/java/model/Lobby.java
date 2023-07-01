package model;

import model.messenger.Group;
import model.user.User;

import java.util.ArrayList;

public class Lobby {
    private final int numberOfPlayers;

    private final ArrayList<User> players = new ArrayList<>();

    private boolean isPrivate = false;

    public static int counter = 0;
    private final int ID;
    private final String name;
    private final Group group;
    public Lobby(int numberOfPlayers, String name) {
        this.numberOfPlayers = numberOfPlayers;
        this.name = name;
        ID = ++counter;
        group = new Group(name);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addPlayer(User user) {
        players.add(user);
        group.getUsers().add(user);
    }
    public ArrayList<User> getPlayers() {
        return players;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getID() {
        return ID;
    }
    public User getAdmin() {
        if(players.isEmpty()) return null;
        return players.get(0);
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }
}
