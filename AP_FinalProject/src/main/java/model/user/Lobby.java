package model.user;

import java.util.ArrayList;

public class Lobby {
    private final int numberOfPlayers;

    private final ArrayList<User> players = new ArrayList<>();

    private boolean isPrivate = false;

    public static int counter = 0;
    private final int ID;
    public Lobby(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        ID = ++counter;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void addPlayer(User user) {
        players.add(user);
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
}
