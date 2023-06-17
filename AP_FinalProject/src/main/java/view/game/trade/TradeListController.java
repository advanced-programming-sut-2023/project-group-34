package view.game.trade;

import javafx.scene.input.MouseEvent;
import view.LaunchMenu;

public class TradeListController {
    public void backToMainTradeMenu(MouseEvent mouseEvent) throws Exception{
        new MainTradeMenu().start(LaunchMenu.getStage());
    }

    public void displayTradeSent(MouseEvent mouseEvent) throws Exception{
        new TradeSentMenu().start(LaunchMenu.getStage());
    }

    public void onToReceivedTrades(MouseEvent mouseEvent) throws Exception{
        new IncomingTrades().start(LaunchMenu.getStage());
    }
}
