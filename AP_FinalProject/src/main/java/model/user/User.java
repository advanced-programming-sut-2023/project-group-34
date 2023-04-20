package model.user;

import model.enums.Slogan;
import model.government.Government;

import java.util.ArrayList;
import java.util.Comparator;

public class User {
    private String name;
    private Password password;
    private String nickname;
    private String email;
    private String slogan;
    private static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    private Boolean isLoggedIn;
    private int score;
    private Government government;
    private Slogan sloganTypes;
    private int currentScore = 0;

    public User(String name, Password password, String nickname, String email){
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        government = new Government(this);
        users.add(this);
    }

    public int getPlayerRank(){
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.score - o2.score;
            }
        });
        return 0;
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
}
