package view.game.trade;

import controller.GameController;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.Trade;
import model.enums.make_able.Resources;
import model.user.User;
import view.LaunchMenu;
import view.game.shop.ShopMenu;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainTradeController implements Initializable {

    private static int counter = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onToNewRequest(MouseEvent mouseEvent) throws Exception{
        new ChoosingTradePartnerMenu().start(LaunchMenu.getStage());
    }


    public void backToShop(MouseEvent mouseEvent) throws Exception{
        new ShopMenu().start(LaunchMenu.getStage());
    }

    public void onToTradeList(MouseEvent mouseEvent) throws Exception {
        new TradeList().start(LaunchMenu.getStage());
    }

    public void showTradeNotification(MouseEvent mouseEvent) {
        String notification = "";
        while (!GameController.getCurrentGame().getCurrentGovernment().getOwner().getNotificationsList().isEmpty()) {
            Trade trade = GameController.getCurrentGame().getCurrentGovernment().getOwner().getNotificationsList().poll();
            notification = notification.concat("You have a new trade request from " + trade.getSender().getName() + "\n");
        }

        ButtonType foo = new ButtonType("Take Me", ButtonBar.ButtonData.OK_DONE);

        if (!notification.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Trade Notification", foo, ButtonType.CANCEL);
            alert.setHeaderText("Trade Notification");
            alert.setHeight(100.0);
            alert.setWidth(200.0);
            alert.setContentText(notification);
            alert.initOwner(LaunchMenu.getStage());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == foo) {
                try {
                    new IncomingTrades().start(LaunchMenu.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
