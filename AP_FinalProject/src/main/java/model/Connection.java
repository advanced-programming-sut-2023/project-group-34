package model;

import com.google.gson.JsonSyntaxException;
import controller.ServerHandler;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;

public class Connection extends Thread {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private User user;
    
    public Connection (Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }
    
    @Override
    public void run () {
        try {
            //user = ServerHandler.serverHandler.referenceToNewlyAddedUser(getUserFromInputStream());
            String data;
            Matcher matcher;
            while (true) {
                if (inputStream.available() != 0) {
                    data = inputStream.readUTF();
                    if ((matcher = Commands.CLONE_MAP.getMatcher(data)) != null) {
                        String mapName = matcher.group("map");
                        String map = ServerHandler.serverHandler.getMapByName(mapName);
                        if (map == null) outputStream.writeUTF("fail");
                        else outputStream.writeUTF(map);
                    } else if (data.equals("info")) {
                        StringBuilder result = new StringBuilder();
                        boolean notFirstTime = false;
                        for (String map : ServerHandler.serverHandler.getMaps()) {
                            if (notFirstTime) result.append("\n");
                            else notFirstTime = true;
                            result.append(map);
                        }
                        outputStream.writeUTF(result.toString());
                    } else {
                        outputStream.writeUTF(ServerHandler.serverHandler.addNewMap(data).text);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private User getUserFromInputStream () throws IOException {
        while (true) {
            try {
                String register = inputStream.readUTF();
                return User.jsonToUser(register);
            } catch (JsonSyntaxException ignored) {
            }
        }
    }
}
