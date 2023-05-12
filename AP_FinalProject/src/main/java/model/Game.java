package model;

import model.government.Government;
import model.map.GameMap;
import model.user.User;

import java.util.ArrayList;

public class Game {
    private final ArrayList<User> players = new ArrayList<>();

    private Government currentGovernment;
    private final ArrayList<Trade> allTrades = new ArrayList<>();
    private final GameMap map;
    public Game(GameMap map) {
        this.map = map;
        setCurrentGovernment(User.currentUser.getGovernment());
    }

    public GameMap getMap() {
        return map;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }
    
    public void addPlayer (User user) {
        players.add(user);
    }



    public ArrayList<Trade> getAllTrades() {
        return allTrades;
    }

    public void addAllTrades(Trade trade){
        this.allTrades.add(trade);
    }
    
    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }
}
