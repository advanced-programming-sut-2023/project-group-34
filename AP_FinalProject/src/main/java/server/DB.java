package server;

import model.messenger.Chat;
import model.user.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final HashMap<Socket , User> socketUserHashMap = new HashMap<>();
    private final HashMap<User , String> usersLastSeen = new HashMap<>();
    public void addChat(Chat chat) {
        chats.add(chat);
    }
    public ArrayList<Chat> getChats() {
        return chats;
    }

    public HashMap<Socket, User> getSocketUserHashMap() {
        return socketUserHashMap;
    }

    public HashMap<User, String> getIsUserOnline() {
        return usersLastSeen;
    }
}
