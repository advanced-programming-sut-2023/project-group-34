package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.user.User;

import java.net.URL;
import java.util.Objects;

public class LaunchMenu extends Application {

    public static MediaPlayer mediaPlayer;

    private static Stage stage;

    public static void main(String[] args) {
        User.loadAllUsersFromDataBase();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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
