package view;

import controller.*;
import model.enums.Commands;
import model.user.User;

import java.util.regex.Matcher;

public class MapEditingMenu {
    public static String run(){
        User.currentUser.loadUserMapsFromDataBase();
        String input;
        Matcher matcher;
        if (!MainController.showUserMaps().equals("User has no custom maps yet!")) System.out.println(MainController.showUserMaps());
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
            else if (Commands.getOutput(input, Commands.BACK) != null)
                return "";

            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Map Editing Menu");

            else System.out.println("Invalid command!");
        }
        while (true){
            input = Runner.getScn().nextLine().trim();
            if ((matcher = Commands.getOutput(input, Commands.SET_TEXTURE)) != null)
                System.out.println(MapEditingController.changeBlockFloorType(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.CLEAR)) != null)
                System.out.println(MapEditingController.clearBlock(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_ROCK)) != null)
                System.out.println(MapEditingController.dropRock(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_TREE)) != null)
                System.out.println(MapEditingController.dropTree(matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_BUILDING)) != null)
                System.out.println(BuildingController.dropBuilding(MapEditingController.getCurrentGameMap(), matcher));
            else if ((matcher = Commands.getOutput(input, Commands.DROP_UNIT)) != null)
                System.out.println(TroopsController.dropUnit(MapEditingController.getCurrentGameMap(), matcher));
            else if (Commands.getOutput(input, Commands.BACK) != null) {
                System.out.println("Back to main menu");
                MapEditingController.resetCurrentMap();
                User.getCurrentUser().saveUserMaps();
                return "main menu";
            }
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Map Editing Menu");

            else System.out.println("Invalid command!");
        }
    }
}
