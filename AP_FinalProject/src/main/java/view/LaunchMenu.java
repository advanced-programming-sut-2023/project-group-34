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
import server.Server;

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
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        User.loadAllUsersFromDataBase();
        User.loadCurrentUser();
        socket = new Socket("localhost", Server.SERVER_PORT);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        LaunchMenuController.authentication();
        if (User.currentUser != null) {
            LaunchMenu.dataOutputStream.writeUTF("user login");
            LaunchMenu.dataOutputStream.writeUTF(new Gson().toJson(User.currentUser));
        }
        launch(args);
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
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
//        mediaPlayer.setCycleCount(-1);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        LaunchMenu.stage = stage;
    }
}
