package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static DB dataBase;
    public static final int SERVER_PORT = 8004;
    public static void main(String[] args) throws IOException {
        new Server(SERVER_PORT);
    }
    public Server(int port) {
        dataBase = new DB();
        System.out.println("Server started...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("new connection from" + socket.getInetAddress() + ":" + socket.getPort());
                Connection connection = new Connection(socket);
                connection.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
