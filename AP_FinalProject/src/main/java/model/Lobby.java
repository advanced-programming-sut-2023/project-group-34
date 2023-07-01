package model;

import model.messenger.Group;
import model.user.User;

import java.util.ArrayList;

public class Lobby {
    private final int numberOfPlayers;

    private final String name;

    private final ArrayList<User> players = new ArrayList<>();

    private boolean isPrivate = false;
    private final Group group;

    public static int counter = 0;
    private final int ID;
    public Lobby(int numberOfPlayers, String name) {
        this.numberOfPlayers = numberOfPlayers;
        this.name = name;
        group = new Group(this.name);
        ID = ++counter;
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

    public String getStatus(){
        if (isPrivate)
            return "private";
        else return "public";
    }

    public String getPlayersNames(){
        if (!isPrivate){
            String playersNames = "";
            for (User player : players)
                playersNames = playersNames.concat(player.getNickname() + "  ");
            return playersNames;
        } else return "";
    }

    public String getCapacity(){
        return players.size() + "/" + numberOfPlayers;
    }

    public Group getGroup() {
        return group;
    }
}
