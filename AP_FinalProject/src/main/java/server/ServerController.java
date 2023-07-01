package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.friendshiprequest.FriendshipRequest;
import model.friendshiprequest.RequestTypes;
import model.messenger.Chat;
import model.messenger.Group;
import model.messenger.PrivateChat;
import model.user.User;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

public class ServerController {

    public static void sendRequest(Matcher matcher, Connection connection) {
        String username = matcher.group("username");
        User receiver = getUser(username);
        assert receiver != null;
        User sender = Server.dataBase.getSocketUserHashMap().get(connection.getSocket());
        assert sender != null;
        Server.dataBase.getRequests().add(new FriendshipRequest(sender, receiver));
    }

    public static void getRequests(Connection connection) throws IOException {
        User user = Server.dataBase.getSocketUserHashMap().get(connection.getSocket());
        assert user != null;
        ArrayList<FriendshipRequest> result = new ArrayList<>();
        for (FriendshipRequest request : Server.dataBase.getRequests()) {
            if (request.getSender().getName().equals(user.getName()) || request.getReceiver().getName().equals(user.getName())) {
                result.add(request);
            }
        }
        connection.getDataOutputStream().writeUTF(new Gson().toJson(result));
    }

    public static void answerRequest(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String answer = matcher.group("answer");
        RequestTypes requestType;
        if (answer.equalsIgnoreCase(RequestTypes.ACCEPTED.toString())) {
            requestType = RequestTypes.ACCEPTED;
        } else if (answer.equalsIgnoreCase(RequestTypes.PENDING.toString())) {
            requestType = RequestTypes.PENDING;
        } else {
            requestType = RequestTypes.DENIED;
        }
        for (FriendshipRequest request : Server.dataBase.getRequests()) {
            if (request.getID() == id) request.setRequestType(requestType);
        }
    }

    public static void addUserToGroup(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String username = matcher.group("username");
        Chat chat = null;
        for (Chat tempChat : Server.dataBase.getChats()) {
            if (tempChat.getID() == id) chat = tempChat;
        }
        assert chat != null;
        User user = null;
        for (User tempUser : Server.dataBase.getIsUserOnline().keySet()) {
            if (tempUser.getName().equals(username)) user = tempUser;
        }
        assert user != null;
        chat.getUsers().add(user);
    }

    public static void createPV(Connection connection) throws IOException {
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> users = gson.fromJson(json, type);
        PrivateChat privateChat = new PrivateChat();
        for (String username : users) {
            User user = getUser(username);
            if (user != null) privateChat.getUsers().add(user);
        }
    }

    public static void userLogin(Connection connection) throws IOException {
        String json = connection.getDataInputStream().readUTF();
        User user = new Gson().fromJson(json, User.class);
        User.currentUser = user;
        for (User user1 : Server.dataBase.getIsUserOnline().keySet()) {
            if (user1.getName().equals(User.currentUser.getName())) {
                System.out.println("user " + user.getName() + " back online : " + connection.getSocket().getInetAddress());
                for (Map.Entry<Socket, User> entry : Server.dataBase.getSocketUserHashMap().entrySet()) {
                    if (entry.getValue().getName().equals(user1.getName())) {
                        Server.dataBase.getSocketUserHashMap().remove(entry.getKey(), entry.getValue());
                    }
                }
                Server.dataBase.getIsUserOnline().remove(user1);
                Server.dataBase.getIsUserOnline().put(user, "just now");
                Server.dataBase.getSocketUserHashMap().put(connection.getSocket(), user);
                return;
            }
        }
        user.setScore(new Random().nextInt(1000));
        System.out.println("new User :" + User.currentUser.getName());
        Server.dataBase.getSocketUserHashMap().put(connection.getSocket(), User.currentUser);
        Server.dataBase.getIsUserOnline().put(User.currentUser, "just now");
        Server.dataBase.getPublicChat().getUsers().add(User.currentUser);
    }

    public static User getUser(String username) {
        for (User user : Server.dataBase.getIsUserOnline().keySet()) {
            if (user.getName().equals(username)) return user;
        }
        return null;
    }

    public static void getUsers(Connection connection) throws IOException {
//        ArrayList<User> users = new ArrayList<>(Server.dataBase.getIsUserOnline().keySet());
//        for(User user : users) {
//            String time = Server.dataBase.getIsUserOnline().get(user);
//            System.out.println(user.getName() + "  " + time +"  "+ Server.dataBase.getIsUserOnline().size());
//            if(time.equals("just now")) {
//                user.setStatus(true);
//            }
//            else user.setStatus(false);
//            user.setLastSeen(time);
//            user.setScore(new Random().nextInt(1000));
//        }
//        connection.getDataOutputStream().writeUTF(new Gson().toJson(users));
        int counter = 0;
        ArrayList<User> users = new ArrayList<>();
        for (Map.Entry<User, String> entry : Server.dataBase.getIsUserOnline().entrySet()) {
            System.out.println(++counter);
            System.out.println(entry.getKey().getName() + "  " + entry.getValue());
            entry.getKey().setStatus(entry.getValue().equals("just now"));
            entry.getKey().setLastSeen(entry.getValue());
            if(!contains(users , entry.getKey()))
                users.add(entry.getKey());
        }
        connection.getDataOutputStream().writeUTF(new Gson().toJson(users));
    }

    public static void createGroup(Connection connection, Matcher matcher) throws IOException {
        String groupName = matcher.group("name");
        Gson gson = new Gson();
        String json = connection.getDataInputStream().readUTF();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> users = gson.fromJson(json, type);
        Group newGroup = new Group(groupName);
        for (String username : users) {
            User user = getUser(username);
            if (user != null) newGroup.getUsers().add(user);
        }
    }

    public static void updateChat(Connection connection) {
        Chat chat;
        try {
            Gson gson = new Gson();
            String jason = connection.getDataInputStream().readUTF();
            if (!isItPrivateChat(jason)) chat = gson.fromJson(jason, Group.class);
            else chat = gson.fromJson(jason, PrivateChat.class);
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
        for (Chat tempChat : Server.dataBase.getChats()) {
            if (tempChat.getID() == id) {
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
            if (chat instanceof PrivateChat pv && contains(pv.getUsers(), user)) privateChats.add(pv);
            if (chat instanceof Group group && contains(group.getUsers(), user)) groups.add(group);
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

    public static boolean contains(ArrayList<User> users, User user) {
        for (User user1 : users) {
//            if(user1 == null) continue;
            if (user1.getName().equals(user.getName()))
                return true;
        }
        return false;
    }
}
