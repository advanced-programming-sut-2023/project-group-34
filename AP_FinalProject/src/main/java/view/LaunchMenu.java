package view;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.messenger.Chat;
import model.messenger.Group;
import model.messenger.Message;
import model.messenger.PrivateChat;
import model.user.User;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class LaunchMenu extends Application {

    public static MediaPlayer mediaPlayer;

    private static Stage stage;
    public static boolean gameStarted;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        User.loadAllUsersFromDataBase();
        User.loadCurrentUser();
//        launch(args);
        Socket socket = new Socket("localhost", 8002);
//        ChatMenu.dataInputStream = new DataInputStream(ChatMenu.socket.getInputStream());
//        ChatMenu.dataOutputStream = new DataOutputStream(ChatMenu.socket.getOutputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream.writeUTF("get chats");
//        System.out.println("FF");
//        System.out.println(dataInputStream.readUTF());
        Gson gson = new Gson();
        System.out.println("------------");
        String json = dataInputStream.readUTF();
        Group ss = gson.fromJson(json , Group.class);
        System.out.println(json);
        System.out.println(ss.getName());
//        System.out.println(group.getMessages().get(0).getMessage());
//        System.out.println(group.getID());
//        ObjectInputStream objectInputStream = new ObjectInputStream(ChatMenu.socket.getInputStream());
//        Message message = (Message) ((ArrayList<?>)objectInputStream.readObject()).get(0);
//        System.out.println(objectInputStream.readObject());
//        Group chat = (Group) objectInputStream.readObject();
//        System.out.println(chat.getName());
//        System.out.println(chat.getID());
//        System.out.println(chat.getMessages().get(0).getMessage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameStarted = false;
        LaunchMenu.stage = stage;
        URL url = LaunchMenu.class.getResource("/FXML/launchMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        Media media = new Media(Objects.requireNonNull(LaunchMenu.class.getResource
                ("/media/IntroSong.mp3")).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(-1);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        LaunchMenu.stage = stage;
    }
}
