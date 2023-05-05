package view;

import controller.GameController;
import controller.MainController;
import controller.Runner;
import model.enums.Commands;
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
                String response = MainController.createNewGame(matcher);
                if (response != null) {
                    System.out.println(response);
                    continue;
                }
                setKeeps();
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
    
    private static void setKeeps () {
        int playersCount;
        System.out.println("Please enter the number of players:");
        playersCount = Runner.getScn().nextInt();
        String response;
        while (true) {
            System.out.println("Please enter your location x axis:");
            int x = Runner.getScn().nextInt();
            System.out.println("Please enter your location y axis:");
            int y = Runner.getScn().nextInt();
            response = MainController.setKeep(User.currentUser.getName(), x, y);
            if (response != null) System.out.println(response);
            else break;
        }
        for (int i = 2; i <= playersCount; i++) {
            while (true) {
                System.out.println("Please enter player " + i + " username:");
                String username = Runner.getScn().nextLine().trim();
                System.out.println("Please enter player " + i + " location x axis:");
                int x = Runner.getScn().nextInt();
                System.out.println("Please enter player " + i + " location y axis:");
                int y = Runner.getScn().nextInt();
                response = MainController.setKeep(username, x, y);
                if (response != null) System.out.println(response);
                else break;
            }
        }
    }
}
