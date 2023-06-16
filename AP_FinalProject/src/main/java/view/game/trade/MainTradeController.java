package view.game.trade;

import javafx.scene.input.MouseEvent;
import view.LaunchMenu;

public class MainTradeController {
    public void onToNewRequest(MouseEvent mouseEvent) throws Exception{
        new ChoosingTradePartnerMenu().start(LaunchMenu.getStage());
    }


}
