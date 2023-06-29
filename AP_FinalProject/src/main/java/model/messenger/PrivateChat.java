package model.messenger;

import model.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivateChat extends Chat implements Serializable {

    public String getName(){
        for (User user : users){
            if (!user.equals(User.currentUser))
                return user.getName();
        }
        return null;
    }

}
