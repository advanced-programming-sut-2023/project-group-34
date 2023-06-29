package view.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.profile.AvatarMenu;

import java.net.URL;

public class SelectPlayersMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = SelectPlayersMenu.class.getResource("/FXML/selectPlayers.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
