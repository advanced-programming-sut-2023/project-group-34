package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class LaunchMenu extends Application {

    public static MediaPlayer mediaPlayer;

    private static Stage stage;
    public static boolean gameStarted;

    public static void main(String[] args) throws InterruptedException, IOException {
        User.loadAllUsersFromDataBase();
        User.loadCurrentUser();
//        launch(args);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(dateTimeFormatter.format(LocalTime.now()));
//        ChatMenu.socket = new Socket("localhost" , 8080);
//        ChatMenu.dataInputStream = new DataInputStream(ChatMenu.socket.getInputStream());
//        ChatMenu.dataOutputStream = new DataOutputStream(ChatMenu.socket.getOutputStream());
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
