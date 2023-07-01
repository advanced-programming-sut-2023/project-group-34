package server;

import model.Lobby;
import model.friendshiprequest.FriendshipRequest;
import model.messenger.Chat;
import model.messenger.Group;
import model.user.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final ArrayList<Lobby> lobbies = new ArrayList<>();
    private final HashMap<Socket , User> socketUserHashMap = new HashMap<>();
    private final HashMap<User , String> usersLastSeen = new HashMap<>();
    private final ArrayList<FriendshipRequest> requests = new ArrayList<>();
    private final Group publicChat = new Group("public chat");
    {
        chats.add(publicChat);
    }
    public void addChat(Chat chat) {
        chats.add(chat);
    }
    public ArrayList<Chat> getChats() {
        return chats;
    }

    public HashMap<Socket, User> getSocketUserHashMap() {
        return socketUserHashMap;
    }

    public synchronized HashMap<User, String> getIsUserOnline() {
        return usersLastSeen;
    }

    public Group getPublicChat() {
        return publicChat;
    }

    public ArrayList<FriendshipRequest> getRequests() {
        return requests;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }
}
