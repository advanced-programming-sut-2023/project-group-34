package view;

import com.sun.tools.javac.Main;
import controller.MainController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class MapEditingMenu {
    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = Runner.getScn().nextLine().trim();
            if ((matcher = Commands.getOutput(input, Commands.NEW_MAP)) != null) {
                String response = MainController.newMap(matcher);
                if (response == null) {
                    System.out.println("Map created successfully. You can start editing!");
                    break;
                }
                System.out.println(response);
            }
            else if ((matcher = Commands.getOutput(input, Commands.EDIT_OLD_MAP)) != null) {
                String response = MainController.editMap(matcher);
                if (response == null) {
                    System.out.println("Map loaded successfully. You can start editing!");
                    break;
                }
                System.out.println(response);
            }
            else System.out.println("Invalid command!");
        }
        while (true){
            input = Runner.getScn().nextLine().trim();
            if ((matcher = Commands.getOutput(input, Commands.SET_TEXTURE)) != null)
                System.out.println(MainController.changeBlockFloorType(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.CLEAR)) != null)
                System.out.println(MainController.clearBlock(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_ROCK)) != null)
                System.out.println(MainController.dropRock(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_TREE)) != null)
                System.out.println(MainController.dropTree(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_BUILDING)) != null)
                System.out.println(MainController.dropBuilding(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_UNIT)) != null)
                System.out.println(MainController.dropUnit(matcher));
            else if (Commands.getOutput(input, Commands.BACK) != null) {
                System.out.println("Back to main menu");
                MainController.resetCurrentMap();
                return "main menu";
            }
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Map Editing Menu");

            else System.out.println("Invalid command!");
        }
    }
}
