package view.game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.profile.AvatarMenu;

import java.net.URL;

public class GameMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = AvatarMenu.class.getResource("/FXML/game.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
