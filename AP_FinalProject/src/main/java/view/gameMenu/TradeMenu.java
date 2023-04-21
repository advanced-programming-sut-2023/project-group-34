package view.gameMenu;

import controller.GameController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class TradeMenu {
    public static String run(){
        Matcher matcher;
        while (true){
            String command = Runner.getScn().nextLine();
            command = command.trim();

            System.out.println(GameController.showTradeNotification());
            if (Commands.getOutput(command, Commands.CURRENT_MENU) != null){
                System.out.println("Trade Menu");
            }
        }
    }
}
