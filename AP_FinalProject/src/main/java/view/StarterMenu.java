package view;

import controller.UserController;
import model.enums.Commands;
import model.user.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StarterMenu {


    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = controller.Runner.getScn ().nextLine ();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput (input, Commands.CREATE_USER)) != null) {
                System.out.println(UserController.registerUser(matcher));
            }
            else if ((matcher = Commands.getOutput (input, Commands.LOGIN)) != null) {
                System.out.println(UserController.loginUser(matcher));
            }
            else if ((matcher = Commands.getOutput (input, Commands.FORGOT_PASSWORD)) != null) {
                System.out.println(UserController.forgotPassword(matcher));
            }
            else {
                System.out.println("Invalid Command");
            }
        }
    }


}
