package view.gameMenu;

import controller.Runner;
import controller.TradeController;
import model.enums.Commands;

import java.util.regex.Matcher;

public class TradeMenu {
    public static String run(){
        Matcher matcher;
        System.out.println(TradeController.showTradeNotification());
        while (true) {
            String command = Runner.getScn().nextLine();
            if (Commands.getOutput(command, Commands.CURRENT_MENU) != null) {
                System.out.println("Trade Menu");
            } else if (Commands.getOutput(command, Commands.SHOW_TRADE_LIST) != null) {
                System.out.println(TradeController.showTradeList());
            } else if (Commands.getOutput(command, Commands.SHOW_TRADE_HISTORY) != null) {
                System.out.println(TradeController.showTradeHistory());
            } else if ((matcher = Commands.getOutput(command, Commands.ACCEPT_TRADE)) != null){
                System.out.println(TradeController.acceptTradeItem(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.TRADE)) != null){
                System.out.println(TradeController.trade(matcher));
            } else if (command.equals("back")){
                return "back";
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
}
