package controller;

import model.Messages;
import model.user.User;

import java.util.ArrayList;

public class ServerHandler {
    public static ServerHandler serverHandler = new ServerHandler();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<String> maps = new ArrayList<>();
    
    private boolean isTheMapAdded (String mapName) {
        return maps.contains(mapName);
    }
    
    public ArrayList<User> getUsers () {
        return users;
    }
    
    public ArrayList<String> getMaps () {
        return maps;
    }
    
    public String getMapByName (String name) {
        if (maps.contains(name)) return name;
        return null;
    }
    
    public User referenceToNewlyAddedUser (User addingUser) {
        for (User user : users) {
            if (user.equals(addingUser)) return user;
        }
        users.add(addingUser);
        return addingUser;
    }
    
    public Messages addNewMap (String map) {
        if (isTheMapAdded(map)) return Messages.MAP_ALREADY_ADDED;
        maps.add(map);
        //todo: graphical changes!
        return Messages.SUCCESS;
    }
}
