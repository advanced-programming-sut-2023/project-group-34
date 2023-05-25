package view.starter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.LaunchMenu;

import java.net.URL;

public class RegisterMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = RegisterMenu.class.getResource("/FXML/registerMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
