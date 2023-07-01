package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Lobby;
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
import java.util.regex.Pattern;

public class ServerController {

    public static void makeLobby(Matcher matcher , Connection connection) throws IOException {
        int id = Integer.parseInt(matcher.group("id"));
        String name = matcher.group("name");
        Lobby lobby = new Lobby(id , name);
        Server.dataBase.getLobbies().add(lobby);
        connection.getDataOutputStream().writeUTF(new Gson().toJson(lobby));
    }

    public static void getLobby(Matcher matcher , Connection connection) throws IOException {
        int id = Integer.parseInt(matcher.group("id"));
        Lobby toBeSend = null;
        for(Lobby lobby : Server.dataBase.getLobbies()) {
            if(lobby.getID() == id) toBeSend = lobby;
        }
        connection.getDataOutputStream().writeUTF(new Gson().toJson(toBeSend));
    }
    public static Lobby getLobbyById(int id) {
        for(Lobby lobby : Server.dataBase.getLobbies()) {
            if(lobby.getID() == id) return lobby;
        }
        return null;
    }
    public static void getLobbies(Connection connection) throws IOException {
        ArrayList<Lobby> lobbies = new ArrayList<>(Server.dataBase.getLobbies());
        connection.getDataOutputStream().writeUTF(new Gson().toJson(lobbies));
    }
    public static void startGame(Matcher matcher , Connection connection) throws IOException {
        int id = Integer.parseInt(matcher.group("id"));
        Socket socket = new Socket("localhost" , 8090);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        Lobby lobby = getLobbyById(id);
        if(lobby == null) throw new RuntimeException("Failed during starting game (lobby does not exits)");
        dataOutputStream.writeUTF(new Gson().toJson(lobby.getPlayers()));
        Server.dataBase.getLobbies().remove(lobby);
    }
    public static void joinLobby(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String username = matcher.group("username");
        User user = getUser(username);
        if(user == null) throw new RuntimeException("Failed during joining lobby (user not found)");
        Lobby lobby = getLobbyById(id);
        if(lobby == null) throw new RuntimeException("Failed during joining lobby (lobby does not exits)");
        lobby.addPlayer(user);
    }
    public static void deleteLobby(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        Lobby lobby = getLobbyById(id);
        if(lobby == null) throw new RuntimeException("Failed during deleting lobby (lobby does not exits)");
        if(lobby.getPlayers().isEmpty()) Server.dataBase.getLobbies().remove(lobby);
    }
    public static void changeLobby(Matcher matcher) {
        String status = matcher.group("status");
        int id = Integer.parseInt(matcher.group("id"));
        Lobby lobby = getLobbyById(id);
        if(lobby == null) throw new RuntimeException("Failed during changing lobby (lobby does not exits)");
        switch (status) {
            case "private" -> lobby.setPrivate(true);
            case "public" -> lobby.setPrivate(false);
        }
    }

    public static void sendRequest(Matcher matcher, Connection connection) {
        String username = matcher.group("username");
        User receiver = getUser(username);
        User sender = Server.dataBase.getSocketUserHashMap().get(connection.getSocket());
        assert receiver != null;
        assert sender != null;
        Server.dataBase.getRequests().add(new FriendshipRequest(sender, receiver));
    }

    public static void getRequests(Connection connection) throws IOException {
        User user = Server.dataBase.getSocketUserHashMap().get(connection.getSocket());
        assert user != null;
        ArrayList<FriendshipRequest> result = new ArrayList<>();
        ArrayList<FriendshipRequest> requests = Server.dataBase.getRequests();
        for (int i = requests.size() - 1; i >= 0; i--) {
            FriendshipRequest request = requests.get(i);
            if(request.getReceiver() == null) {
                System.out.println("FUck");
                Server.dataBase.getRequests().remove(request);
                continue;
            }
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
        ArrayList<User> users = new ArrayList<>();
        for (Map.Entry<User, String> entry : Server.dataBase.getIsUserOnline().entrySet()) {
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
            if(user1 == null) continue;
            if (user1.getName().equals(user.getName()))
                return true;
        }
        return false;
    }
}
