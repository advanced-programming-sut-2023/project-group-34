package view.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.profile.AvatarMenu;

import java.net.URL;

public class ShopMenu extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        URL url = ShopMenu.class.getResource("/FXML/shopMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
