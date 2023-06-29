package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static Server server;
    public DB dataBase;
    public static void main(String[] args) throws IOException {
        new Server(8002);
    }
    public Server(int port) {
        server = this;
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
