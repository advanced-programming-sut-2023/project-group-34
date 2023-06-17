package view.game.trade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.profile.ProfileMenu;

import java.net.URL;

public class TradeList extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        URL url = TradeList.class.getResource("/FXML/tradeListMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
