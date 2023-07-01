package server;

import com.google.gson.Gson;
import model.messenger.Group;
import model.messenger.Message;
import model.messenger.PrivateChat;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.regex.Matcher;

public class Connection extends Thread {
    private final Socket socket;

    private final DataInputStream dataInputStream;

    private final DataOutputStream dataOutputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                handleClient();
            } catch (IOException e) {
                System.out.println("User went offline : " + socket.getInetAddress());
                makeClientOfLine();
                break;
            }
        }
    }
    private void makeClientOfLine() {
        User user = Server.dataBase.getSocketUserHashMap().get(socket);
        Server.dataBase.getSocketUserHashMap().remove(socket);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String lastSeen = dateTimeFormatter.format(LocalTime.now());
        for(Map.Entry<User , String> entry : Server.dataBase.getIsUserOnline().entrySet()) {
            if(user != null && entry.getKey().getName().equals(user.getName())) {
                Server.dataBase.getIsUserOnline().put(entry.getKey() , lastSeen);
            }
        }
    }

    private void handleClient() throws IOException {
        String input = dataInputStream.readUTF();
        System.out.println("A new request : " + input + " from " + socket.getInetAddress());
        Matcher matcher;
        if((ServerInputs.GET_CHATS.getMatcher(input)).find()) {
            ServerController.getChats(this);
        }
        else if((matcher = ServerInputs.GET_CHAT.getMatcher(input)).find()) {

            ServerController.getChat(this , matcher);
        }
        else if((ServerInputs.UPDATE_CHAT.getMatcher(input)).find()) {
            ServerController.updateChat(this);
        }
        else if((matcher = ServerInputs.CREATE_GROUP.getMatcher(input)).find()) {
           ServerController.createGroup(this , matcher);
        }
        else if(ServerInputs.CREATE_PV.getMatcher(input).find()) {
            ServerController.createPV(this);
        }
        else if((matcher = ServerInputs.GET_USER.getMatcher(input)).find()) {
            dataOutputStream.writeUTF(new Gson().toJson(ServerController.getUser(matcher.group("username"))));
        }
        else if((matcher = ServerInputs.ADD_USER.getMatcher(input)).find()) {
            ServerController.addUserToGroup(matcher);
        }
        else if(ServerInputs.USER_LOGIN.getMatcher(input).find()) {
            ServerController.userLogin(this);
        }
        else if(ServerInputs.GET_PUBLIC_CHAT.getMatcher(input).find()) {
            dataOutputStream.writeUTF(new Gson().toJson(Server.dataBase.getPublicChat()));
        }
        else if(ServerInputs.GET_USERS.getMatcher(input).find()) {
            ServerController.getUsers(this);
        }
        else if((matcher = ServerInputs.ANSWER_REQUEST.getMatcher(input)).find())
            ServerController.answerRequest(matcher);
        else if((matcher = ServerInputs.SEND_FRIEND_REQUEST.getMatcher(input)).find())
            ServerController.sendRequest(matcher , this);
        else if(ServerInputs.GET_REQUESTS.getMatcher(input).find()) {
            ServerController.getRequests(this);
        }
        else
            throw new IOException("wrong command");
    }
    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }
}
