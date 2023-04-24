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
                String result = UserController.loginUser(matcher);
                System.out.println(result);
                if (result.equals("Username and password did not match!")) UserController.wrongPasswordsEntered();
                else if (result.equals("User logged in")) return result;
            }
            else if (Commands.getOutput (input, Commands.ENTER_FORGOT_PASSWORD_MENU) != null) {
                System.out.println("Entered forgot password menu!");
                return "forgetPassword";
            }
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Starter Menu");
            else
                System.out.println("Invalid Command");
        }
    }
}
