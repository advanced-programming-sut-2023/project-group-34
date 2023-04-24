package view;

import controller.MainController;
import controller.Runner;
import controller.UserController;
import model.enums.Commands;
import model.user.User;

import java.util.regex.Matcher;

public class ProfileMenu {
    public static String run(){
        Matcher matcher;
        while (true){
            String command = Runner.getScn().nextLine();
            command = command.trim();
            if ((matcher = Commands.getOutput(command, Commands.CHANGE_USER)) != null){
                String response = MainController.changeUsername(matcher);
                System.out.println(MainController.changeUsername(matcher));
                if (response.equals("Username already exists, changing username failed")){
                    System.out.println(UserController.randomUsernameGenerator(matcher.group("username")));
                }
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_NICKNAME)) != null){
                System.out.println(MainController.changeNickname(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_EMAIL)) != null){
                System.out.println(MainController.changeEmail(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_SLOGAN)) != null){
                System.out.println(MainController.changeSlogan(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.REMOVE_SLOGAN)) != null){
                System.out.println(MainController.removeSlogan(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_SLOGAN_RANDOMLY)) != null){
                System.out.println(MainController.changeSloganRandomly(matcher));
                //There might be a conflict here
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_PASSWORD)) != null){
                System.out.println(MainController.changePassword(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_PASSWORD_RANDOMLY)) != null){
                System.out.println(MainController.changePasswordRandomly(matcher));
            } else if (Commands.getOutput(command, Commands.DISPLAY_HIGHSCORE) != null){
                System.out.println(User.currentUser.getScore());
            } else if (Commands.getOutput(command, Commands.DISPLAY_SLOGAN) != null){
                System.out.println(MainController.displaySlogan());
            } else if (Commands.getOutput(command, Commands.DISPLAY_RANK) != null){
                System.out.println("Your current rank is: " + User.currentUser.getPlayerRank());
            } else if (Commands.getOutput(command, Commands.DISPLAY_PROFILE) != null){
                System.out.println(MainController.showProfile());
            } else if (Commands.getOutput(command, Commands.CURRENT_MENU) != null){
                System.out.println("Profile Menu");
            } else if (command.equals("back")){
                return "";
            } else {
                System.out.println("Invalid Command");
            }
        }
    }
}
