package view.gameMenu;

import controller.GameController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class ShopMenu {
    public static String run(){

        Matcher matcher;
        while (true) {
            String command = Runner.getScn().nextLine();
            command = command.trim();

            if (Commands.getOutput(command, Commands.SHOW_PRICE_LIST) != null){
                System.out.println(GameController.showPriceList());
            } else if (Commands.getOutput(command, Commands.CURRENT_MENU) != null){
                System.out.println("Shop Menu");
            } else if ((matcher = Commands.getOutput(command, Commands.BUY_ITEM)) != null){
                System.out.println(GameController.buyItems(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.SELL_ITEM)) != null){
                System.out.println(GameController.sellItems(matcher));
            } else if (command.equals("back")) {
                GameController.deselectBuilding();
                return "back";
            } else
                System.out.println("Invalid command!");
        }
    }
}
