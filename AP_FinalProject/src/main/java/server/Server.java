package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server(8080);
    }
    public Server(int port) {
        System.out.println("Server started...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket , this);
                connection.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
