package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private final Socket socket;

    private final DataInputStream dataInputStream;

    private final DataOutputStream dataOutputStream;
    private final Server server;

    public Connection(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
    @Override
    public void run() {
        while (true) {
            handleClient();
        }
    }

    private void handleClient() {

    }
}
