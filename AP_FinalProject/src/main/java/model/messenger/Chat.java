package model.messenger;

import model.user.User;
import server.Server;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    public static int ChatCounter = 0;
    private final int ID;
    private final ArrayList<Message> messages = new ArrayList<>();
    protected final ArrayList<User> users = new ArrayList<>();

    Chat() {
        ChatCounter++;
        ID = ChatCounter;
        if(Server.dataBase != null) Server.dataBase.addChat(this);
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
