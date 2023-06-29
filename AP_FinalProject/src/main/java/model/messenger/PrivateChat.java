package model.messenger;

import model.user.User;

import java.util.ArrayList;

public class PrivateChat extends Chat{
    private final ArrayList<User> users;


    public PrivateChat(ArrayList<User> users) {
        this.users = users;
    }
    public ArrayList<User> getUsers() {
        return users;
    }

    public String getName(){
        for (User user : users){
            if (!user.equals(User.currentUser))
                return user.getName();
        }
        return null;
    }

}
