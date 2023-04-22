package view.gameMenu;

import controller.GameController;
import controller.MainController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class TradeMenu {
    public static String run(){
        Matcher matcher;
        GameController.showTradeNotification();
        while (true) {
            String command = Runner.getScn().nextLine();
            if (Commands.getOutput(command, Commands.CURRENT_MENU) != null) {
                System.out.println("Trade Menu");
            } else if (Commands.getOutput(command, Commands.SHOW_TRADE_LIST) != null) {
                System.out.println(GameController.showTradeList());
            } else if (Commands.getOutput(command, Commands.SHOW_TRADE_HISTORY) != null) {
                System.out.println(GameController.showTradeHistory());
            } else if ((matcher = Commands.getOutput(command, Commands.ACCEPT_TRADE)) != null){
                System.out.println(GameController.acceptTradeItem(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.TRADE)) != null){
                System.out.println(GameController.trade(matcher));
            }
        }
    }
}
