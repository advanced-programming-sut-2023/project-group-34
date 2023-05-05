package view;

import controller.MainController;
import controller.Runner;
import controller.UserController;
import model.enums.Commands;
import model.user.Password;
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
                if (response.equals("Username already exists")){
                    System.out.println(response);
                    response = StarterMenu.usernameCheck(matcher);
                    String newRandomUsername = null;
                    if (response.equals("Couldn't create user: username in use!"))
                        System.out.println("Couldn't create user: username in use!");
                    else
                        newRandomUsername = response;
                    if (newRandomUsername != null) {
                        User.currentUser.setName(newRandomUsername);
                        System.out.println("Username successfully changed");
                        User.updateDataBase();
                    }
                } else {
                    System.out.println(response);
                }
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_NICKNAME)) != null){
                System.out.println(MainController.changeNickname(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_EMAIL)) != null){
                System.out.println(MainController.changeEmail(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_SLOGAN_RANDOMLY)) != null){
                System.out.println(MainController.changeSloganRandomly(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.REMOVE_SLOGAN)) != null){
                System.out.println(MainController.removeSlogan(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_SLOGAN)) != null){
                System.out.println(MainController.changeSlogan(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_PASSWORD_RANDOMLY)) != null){
                String response = MainController.changePasswordRandomly1(matcher);
                if (response.equals("good for now")){
                    String newPass = Password.randomPassword();
                    System.out.println("Your new password is " + newPass + "\n" + "Please renter your new password for confirmation");
                    response = Runner.getScn().nextLine();
                    System.out.println(MainController.changePasswordRandomly2(newPass, response));
                } else
                    System.out.println(response);
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_PASSWORD)) != null){
                String response = MainController.changePasswordPart1(matcher);
                if (!response.equals("good for now"))
                    System.out.println(response);
                else {
                    String newPass = matcher.group("newPass");
                    System.out.println("Please renter your new password for confirmation");
                    String confirmationPassword = Runner.getScn().nextLine();
                    System.out.println(MainController.changePasswordPart2(newPass, confirmationPassword));
                }
            } else if (Commands.getOutput(command, Commands.DISPLAY_HIGHS_CORE) != null){
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
