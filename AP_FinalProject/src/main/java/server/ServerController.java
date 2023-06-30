package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.messenger.Chat;
import model.messenger.Group;
import model.messenger.Message;
import model.messenger.PrivateChat;
import model.user.User;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.lang.reflect.Type;
import java.math.MathContext;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ServerController {

    public static void addUserToGroup(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String username = matcher.group("username");
        Chat chat = null;
        for(Chat tempChat : Server.dataBase.getChats()) {
            if(tempChat.getID() == id) chat = tempChat;
        }
        assert chat != null;
        User user = null;
        for(User tempUser : Server.dataBase.getIsUserOnline().keySet()) {
            if(tempUser.getName().equals(username)) user = tempUser;
        }
        assert user != null;
        chat.getUsers().add(user);
    }

    public static void createPV(Connection connection) throws IOException {
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> users = gson.fromJson(json , type);
        PrivateChat privateChat = new PrivateChat();
        for (String username : users) {
            User user = getUser(username);
            if(user != null) privateChat.getUsers().add(user);
        }
    }

    public static User getUser(String username) {
        for(User user : Server.dataBase.getIsUserOnline().keySet()) {
            if(user.getName().equals(username)) return user;
        }
        return null;
    }
    public static void createGroup(Connection connection , Matcher matcher) throws IOException {
        String groupName = matcher.group("name");
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> users = gson.fromJson(json , type);
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
        for (Chat tempChat : Server.dataBase.getChats()) {
            if (tempChat.getID() == ChatID) {
                Server.dataBase.getChats().remove(tempChat);
                Server.dataBase.getChats().add(chat);
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
        for(Chat tempChat : Server.dataBase.getChats()) {
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
        User user = Server.dataBase.getSocketUserHashMap().get(socket);
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<PrivateChat> privateChats = new ArrayList<>();
        for (Chat chat : Server.dataBase.getChats()) {
            if (chat instanceof PrivateChat pv && pv.getUsers().contains(user)) privateChats.add(pv);
            if (chat instanceof Group group && group.getUsers().contains(user)) groups.add(group);
        }
        try {
            Gson gson = new Gson();
            String json1 = gson.toJson(groups);
            String json2 = gson.toJson(privateChats);
            connection.getDataOutputStream().writeUTF(json1);
            connection.getDataOutputStream().writeUTF(json2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
