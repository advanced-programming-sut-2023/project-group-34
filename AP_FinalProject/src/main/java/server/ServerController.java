package server;

import com.google.gson.Gson;
import model.messenger.Chat;
import model.messenger.Group;
import model.messenger.Message;
import model.messenger.PrivateChat;
import model.user.User;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.math.MathContext;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ServerController {

    public static void addUserToGroup(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String username = matcher.group("username");
        Chat chat = null;
        for(Chat tempChat : Server.server.dataBase.getChats()) {
            if(tempChat.getID() == id) chat = tempChat;
        }
        assert chat != null;
        User user = null;
        for(User tempUser : Server.server.dataBase.getIsUserOnline().keySet()) {
            if(tempUser.getName().equals(username)) user = tempUser;
        }
        assert user != null;
        chat.getUsers().add(user);
    }

    public static void createPV(Connection connection) throws IOException {
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        ArrayList<String> users = gson.fromJson(json , ArrayList.class);
        PrivateChat privateChat = new PrivateChat();
        for (String username : users) {
            User user = getUser(username);
            if(user != null) privateChat.getUsers().add(user);
        }
    }

    public static User getUser(String username) {
        for(User user : Server.server.dataBase.getIsUserOnline().keySet()) {
            if(user.getName().equals(username)) return user;
        }
        return null;
    }
    public static void createGroup(Connection connection , Matcher matcher) throws IOException {
        String groupName = matcher.group("name");
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        ArrayList<String> users = gson.fromJson(json , ArrayList.class);
        Group newGroup = new Group(groupName);
        for (String username : users) {
            User user = getUser(username);
            if(user != null) newGroup.getUsers().add(user);
        }
    }
    public static void updateChat(Connection connection) {
        Chat chat;
        try {
            Gson gson = new Gson();
            String jason = connection.getDataInputStream().readUTF();
            if(!isItPrivateChat(jason)) chat = gson.fromJson(jason , Group.class);
            else chat = gson.fromJson(jason , PrivateChat.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int ChatID = chat.getID();
        for (Chat tempChat : Server.server.dataBase.getChats()) {
            if (tempChat.getID() == ChatID) {
                Server.server.dataBase.getChats().remove(tempChat);
                Server.server.dataBase.getChats().add(chat);
                return;
            }
        }
    }

    public static boolean isItPrivateChat(String json) {
        return !json.contains("\"name\"");
    }

    public static void getChat(Connection connection, Matcher matcher) throws IOException {
        int id = Integer.parseInt(matcher.group("id"));
        Chat chat = null;
        for(Chat tempChat : Server.server.dataBase.getChats()) {
            if(tempChat.getID() == id) {
                chat = tempChat;
                break;
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(chat);
        connection.getDataOutputStream().writeUTF(json);
    }

    public static void getChats(Connection connection) {
        Socket socket = connection.getSocket();
        User user = Server.server.dataBase.getSocketUserHashMap().get(socket);
        ArrayList<Chat> chats = new ArrayList<>();
        for (Chat chat : Server.server.dataBase.getChats()) {
            if (chat instanceof PrivateChat pv && pv.getUsers().contains(user)) chats.add(pv);
            if (chat instanceof Group group && group.getUsers().contains(user)) chats.add(group);
        }
        try {
            Gson gson = new Gson();
            String json = gson.toJson(chats);
            connection.getDataOutputStream().writeUTF(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
