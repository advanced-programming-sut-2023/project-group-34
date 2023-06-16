package view.game.trade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.main.MainMenu;

import java.net.URL;

public class NewTradeMenu extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        URL url = NewTradeMenu.class.getResource("/FXML/newTradeMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
