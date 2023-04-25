package view.gameMenu;

import controller.GameController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class MapMenu {

    public static String run(){
        Matcher matcher;
        while (true){
            String command = Runner.getScn().nextLine();
            command = command.trim();
            if ((matcher = Commands.getOutput(command, Commands.SHOW_MAP_DETAILS)) != null){
                System.out.println(GameController.getBlockDetails(matcher));
            } else if (Commands.getOutput(command, Commands.CURRENT_MENU) != null) {
                System.out.println("Map Menu");
            } else if (Commands.getOutput(command, Commands.BACK) != null){
                return "";
            } else {
                System.out.println("Invalid Command");
            }
        }
    }
}
