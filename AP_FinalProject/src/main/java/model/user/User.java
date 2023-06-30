package model.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import controller.UserController;
import model.Trade;
import model.enums.Slogan;
import model.government.Government;
import model.map.GameMap;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class User {
    private String name;
    private final Password password;
    private String nickname;
    private String email;
    private String slogan;
    private static final ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    private static Boolean isLoggedIn = false;
    private int score;
    private Government government;
    private Slogan sloganTypes;
    private ArrayList<GameMap> customMaps;
    private ArrayList<String> receivedMapsNames = new ArrayList<>();
    private final ArrayList<Trade> myTrades = new ArrayList<>();
    private final Queue<Trade> notificationsList = new LinkedList<>();

    private String avatarLink;

    private String currentPassword;

    private boolean status;

    private String lastSeen;

    private int ranking;



    public User(String name, String currentPassword, Password password, String nickname, String email) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        users.add(this);
        this.score = 0;
        this.currentPassword = currentPassword;
        this.avatarLink = UserController.randomAvatar().getLink();
        customMaps = new ArrayList<>();
    }


    public String getAvatarLink() {
        return avatarLink;
    }

    public int getRanking() {
        return getPlayerRank();
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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
        users.sort(Comparator.comparingInt(o -> o.score));

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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
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

    public static User getUserByUsername(String name){
        for (User user : users){
            if (user.getName().equals(name))
                return user;
        }
        return null;
    }

    public static void currentUserJsonSaver() {
        try (FileWriter writer = new FileWriter("currentUser.json")) {
            Gson gson = new Gson();
            if (currentUser != null)
                gson.toJson(currentUser.name, writer);
            else
                gson.toJson(null, writer);
        } catch (IOException ignored) {
        }
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return score == user.score && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(slogan, user.slogan) && Objects.equals(government, user.government) && sloganTypes == user.sloganTypes && Objects.equals(customMaps, user.customMaps) && Objects.equals(myTrades, user.myTrades) && Objects.equals(notificationsList, user.notificationsList);
    }

    @Override
    public int hashCode () {
        int currentScore = 0;
        return Objects.hash(name, password, nickname, email, slogan, score, government, sloganTypes, currentScore, customMaps, myTrades, notificationsList);
    }

    public static void loadCurrentUser() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("currentUser.json")) {
            String currentUserName = gson.fromJson(reader, String.class);
            currentUser = getUserByUsername(currentUserName);
        } catch (IOException ignored) {
        }
    }

    public static void stayLoggedIn() {
        isLoggedIn = true;
        currentUserJsonSaver();
    }

    public static void logout() {
        currentUser = null;
        isLoggedIn = false;
        currentUserJsonSaver();
    }
    
    public static void updateDataBase() {
        String usersListAsJSON = new Gson().toJson(users);
        try (FileWriter file = new FileWriter("Users.json")) {
            file.write(usersListAsJSON);
        } catch (IOException ignored) {
        }
        if (isLoggedIn) currentUserJsonSaver();
    }
    
    public void saveUserMaps() {
        XStream xstream = new XStream();
        String mapsAsXml = xstream.toXML(this.getCustomMaps());
        try (FileWriter file = new FileWriter(this.name + "Maps.xml")) {
            file.write(mapsAsXml);
        } catch (IOException ignored) { }
    }
    
    
    public static void loadAllUsersFromDataBase() {
        try (FileReader reader = new FileReader("Users.json")) {
            ArrayList<User> userObjects = new Gson().fromJson(reader, new TypeToken<ArrayList<User>>() {}.getType());
            if (userObjects != null){
                users.addAll(userObjects);
                for (int i = 0; i < users.size(); i++) {
                    users.get(i).loadUserMapsFromDataBase();
                }
            }
            loadCurrentUser();
        } catch (IOException ignored) {
        }
    }
    public void loadUserMapsFromDataBase() {
        XStream xstream = new XStream();
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypesByWildcard(new String[] {"**"});
        try (FileReader reader = new FileReader(this.name + "Maps.xml")) {
            ArrayList<GameMap> userMaps = (ArrayList<GameMap>) xstream.fromXML(reader);
            this.customMaps = new ArrayList<>();
            if (userMaps != null) {
                customMaps.addAll(userMaps);
            }
        } catch (IOException ignored) {
        }
    }


    public GameMap getMapByName (String name) {
        for (GameMap customMap : customMaps) {
            if (customMap.name.equals(name)) return customMap;
        }
        return null;
    }

    public ArrayList<GameMap> getCustomMaps() {
        return customMaps;
    }

    public static User getUserByEmail(String email){
        for (User user : getUsers()){
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public String getStatus() {
        if (status)
            return "online";
        else
            return "offline";
        //TODO check with the server if the user is online
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLastSeen() {
        return lastSeen;
        //TODO check with the server to get the last seen
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }
    
    public static User jsonToUser (String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
    
    public String toJson () {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    private void setCustomMaps (ArrayList<GameMap> customMaps) {
        this.customMaps = customMaps;
    }
    
    public ArrayList<String> getReceivedMapsNames () {
        return receivedMapsNames;
    }
}
