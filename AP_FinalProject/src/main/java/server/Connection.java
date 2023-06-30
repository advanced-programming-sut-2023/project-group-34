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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class Connection extends Thread {
    private final Socket socket;

    private final DataInputStream dataInputStream;

    private final DataOutputStream dataOutputStream;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        User.loadCurrentUser();
        Server.server.dataBase.getSocketUserHashMap().put(socket , User.currentUser);
        Server.server.dataBase.getIsUserOnline().put(User.currentUser , true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                handleClient();
            } catch (IOException e) {
                makeClientOfLine();
                break;
            }
        }
    }
    private void makeClientOfLine() {
        User user = Server.server.dataBase.getSocketUserHashMap().get(socket);
        Server.server.dataBase.getSocketUserHashMap().remove(socket);
        Server.server.dataBase.getIsUserOnline().put(user , false);
    }

    private void handleClient() throws IOException {
        String input = dataInputStream.readUTF();

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
