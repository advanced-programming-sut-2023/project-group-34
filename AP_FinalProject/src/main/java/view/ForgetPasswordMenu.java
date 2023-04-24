package view;

import controller.Runner;
import controller.UserController;
import model.enums.Commands;

import java.util.regex.Matcher;

public class ForgetPasswordMenu {
    public static void run(){
        Matcher matcher;
        String input;
        while (true){
            input = Runner.getScn().nextLine();
            if ((matcher = Commands.getOutput (input, Commands.FORGOT_PASSWORD)) != null) {
                System.out.println(UserController.forgotPassword(matcher));
                return;
            }
            else if (Commands.getOutput(input, Commands.BACK) != null) return;
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Forget Password Menu");
            else System.out.println("Invalid command!");
        }
    }
}
