package view.game.trade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.game.ShopMenu;

import java.net.URL;

public class MainTradeMenu extends Application {




    @Override
    public void start(Stage stage) throws Exception {
        URL url = ShopMenu.class.getResource("/FXML/mainTradeMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
