package view;

import controller.MainController;
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
            input = input.trim();
            if ((matcher = Commands.getOutput (input, Commands.FORGOT_PASSWORD)) != null) {
                String response = UserController.forgotPassword(matcher);
                if (response.equals("good for now")){
                    String username = matcher.group("username");
                    System.out.println(UserController.getUserByUsername(username).getPassword().getSecurityQuestion());
                    String answer = Runner.scn.nextLine();
                    response = UserController.forgotPassword2(answer, UserController.getUserByUsername(username).getPassword());
                    if (response.equals("enter password")){
                        System.out.println("Please enter your new password");
                        String firstPassword = Runner.getScn().nextLine();
                        response = UserController.forgotPassword3(firstPassword);
                        if (response.equals("go to confirmation")){
                            System.out.println("Please re-enter your password for confirmation");
                            String confirmationPassword = Runner.scn.nextLine();
                            response = UserController.forgotPassword4(confirmationPassword, firstPassword);
                            if (response.equals("good for now")){
                                response = StarterMenu.captchaFunction();
                                if (response.equals("captcha is set"))
                                    System.out.println(UserController.setForgotPassword(firstPassword, UserController.getUserByUsername(matcher.group("username")).getPassword()));
                                else {
                                    System.out.println(response);
                                    continue;
                                }
                            } else {
                                System.out.println(response);
                                continue;
                            }
                            continue;
                        } else {
                            System.out.println(response);
                            continue;
                        }
                    } else {
                        System.out.println(response);
                        continue;
                    }
                } else System.out.println(response);{
                    continue;
                }
            }
            else if (Commands.getOutput(input, Commands.BACK) != null) return;
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Forget Password Menu");
            else System.out.println("Invalid command!");
        }
    }
}
