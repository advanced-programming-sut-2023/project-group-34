package view.game.trade;

import javafx.scene.input.MouseEvent;
import view.LaunchMenu;
import view.game.shop.ShopMenu;

public class MainTradeController {
    public void onToNewRequest(MouseEvent mouseEvent) throws Exception{
        new ChoosingTradePartnerMenu().start(LaunchMenu.getStage());
    }


    public void backToShop(MouseEvent mouseEvent) throws Exception{
        new ShopMenu().start(LaunchMenu.getStage());
    }

    public void onToTradeList(MouseEvent mouseEvent) throws Exception {
        new TradeList().start(LaunchMenu.getStage());
    }
}
