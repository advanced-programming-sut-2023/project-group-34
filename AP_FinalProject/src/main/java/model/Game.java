package model;

import model.government.Government;
import model.map.GameMap;
import model.user.User;

import java.util.ArrayList;

public class Game {
    private final ArrayList<User> players = new ArrayList<>();

    private Government currentGovernment;
    private final ArrayList<Trade> allTrades = new ArrayList<>();
    private int numberOfRoundsPassed = 0;

    private final GameMap map;//TODO: Set it
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


    public int getNumberOfRoundsPassed() {
        return numberOfRoundsPassed;
    }

    public void setNumberOfRoundsPassed(int numberOfRoundsPassed) {
        this.numberOfRoundsPassed = numberOfRoundsPassed;
    }

    public void nextUser() {

    }

    public void nextTurn(){

    }

    public ArrayList<Trade> getAllTrades() {
        return allTrades;
    }

    public void addAllTrades(Trade trade){
        this.allTrades.add(trade);
    }
    public void startGame() {

    }

    public int calculateScore(User user) {
        return 0;
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }
}
