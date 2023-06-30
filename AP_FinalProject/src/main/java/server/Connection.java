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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
        User.currentUser = new Gson().fromJson(dataInputStream.readUTF() , User.class);
        if(User.currentUser == null) {
            System.out.println("No user!!!");
            System.exit(-1);
        }
        boolean flag = true;
        for(User user : Server.dataBase.getIsUserOnline().keySet())
        {
            if(user.getName().equals(User.currentUser.getName())) {
                System.out.println("user back online : " + socket.getInetAddress());
                flag = false;
            }
        }
        if(flag) System.out.println("new User :" + User.currentUser.getName());
        Server.dataBase.getSocketUserHashMap().put(socket , User.currentUser);
        Server.dataBase.getIsUserOnline().put(User.currentUser , "-1");
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
        Server.dataBase.getIsUserOnline().put(user , dateTimeFormatter.format(LocalDate.now()));
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
