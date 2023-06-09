package model.user;

import model.Trade;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.enums.Slogan;
import model.government.Government;
import model.map.GameMap;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class User {
    private String name;
    private Password password;
    private String nickname;
    private String email;
    private String slogan;
    private static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    private static Boolean isLoggedIn;
    private int score;
    private Government government;
    private Slogan sloganTypes;
    private int currentScore = 0;

    private ArrayList<GameMap> customMaps;
    //TODO: json

    private ArrayList<Trade> myTrades = new ArrayList<>();
    private Queue<Trade> notificationsList = new LinkedList<>();

    public User(String name, Password password, String nickname, String email){
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        users.add(this);
        updateDataBase();
        customMaps = new ArrayList<>();
    }

    public ArrayList<Trade> getMyTrades() {
        return myTrades;
    }

    public Queue<Trade> getNotificationsList() {
        return notificationsList;
    }

    public void addToMyTrades(Trade trade){
        this.myTrades.add(trade);
    }

    public void putNotificationList(Trade trade){
        this.notificationsList.add(trade);
    }

    public int getPlayerRank(){
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.score - o2.score;
            }
        });

        int rank = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) rank =  i+1;
        }

        return rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public User getUserByUsername(String name){
        for (User user : users){
            if (user.getName().equals(name))
                return user;
        }
        return null;
    }

    public void addUser(User user){
        users.add(user);
    }

    public Slogan getSloganTypes() {
        return sloganTypes;
    }

    public void setSloganTypes(Slogan sloganTypes) {
        this.sloganTypes = sloganTypes;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public static void stayLoggedIn() {
        isLoggedIn = true;
    }

    public static void logout() {
        currentUser = null;
        isLoggedIn = false;
    }

    public static void updateDataBase() {
        String usersListAsJSON = new Gson().toJson(users);
        try (FileWriter file = new FileWriter("Users.json")) {
            file.write(usersListAsJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllUsersFromDataBase() {
        //TODO needs to be checked by ALIZADEH
        try (FileReader reader = new FileReader("Users.json")) {
            //TODO Convert JSON File to Java Object
            List<User> userObjects = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public ArrayList<GameMap> getCustomMaps() {
        return customMaps;
    }

    public GameMap newCustomMap() {
        GameMap map = new GameMap();
        this.customMaps.add(map);
        return map;
    }
}
