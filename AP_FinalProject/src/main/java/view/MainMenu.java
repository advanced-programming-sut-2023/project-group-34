package view;

import controller.GameController;
import controller.MainController;
import model.enums.Commands;
import model.map.GameMap;
import model.user.User;

import java.util.regex.Matcher;

public class MainMenu {
    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = controller.Runner.getScn ().nextLine ();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput(input, Commands.LOGOUT)) != null) {
                System.out.println("User logged out successfully!");
                User.logout();
                return "logout";
            } else if (Commands.getOutput(input, Commands.PROFILE_MENU) != null){
                System.out.println("You just entered profile menu");
                return "profile menu";
            } else if (Commands.getOutput(input, Commands.MAP_EDITING_MENU) != null){
                System.out.println("You just entered map editing menu");
                return "mapEditing menu";
            } else if ((matcher = Commands.getOutput(input, Commands.NEW_GAME)) != null) {
                String mapName = matcher.group("mapName");
                if (mapName != null) mapName = mapName.replaceAll("\"", "");
                String playersCountString = matcher.group("playersCount");
                if (mapName == null || mapName.equals("") || playersCountString == null) {
                    System.out.println("Empty field!");
                    continue;
                }
                MainController.setCurrentGameMap(new GameMap(User.currentUser.getMapByName(mapName)));
                int numberOfPlayers = Integer.parseInt(playersCountString);
                //TODO: Complete with Arshia! Drop keeps.
                GameController.run();
            } else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null){
                System.out.println("Main Menu");
            } else {
                System.out.println("Invalid Command");
            }
            //TODO The part that starts a game with multiple users needs to be implemented
        }
    }
}
