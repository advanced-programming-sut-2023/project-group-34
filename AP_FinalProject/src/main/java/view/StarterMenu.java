package view;

import controller.Runner;
import controller.UserController;
import model.enums.Commands;
import model.user.Password;

import java.util.regex.Matcher;

import static controller.UserController.passwordChecker;
import static controller.UserController.randomUsernameGenerator;

public class StarterMenu {


    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = controller.Runner.getScn ().nextLine ();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput (input, Commands.CREATE_USER)) != null) {
                String username;
                String password;
                String response = UserController.registerUserPart1(matcher);
                if (response != null) return response;
                else {
                    if ((username = usernameCheck(matcher)).equals("Couldn't create user: username in use!"))
                        System.out.println("Couldn't create user: username in use!");
                    else {
                        if ((password = passwordCheck(matcher)).equals(//errors)) sout(password);
                    }
                }

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

    public static String passwordCheck(Matcher matcher) {
        String password = matcher.group("password");
        if (password != null) password = password.replaceAll("\"", "");
        if (password.equals("random")) {
            password = Password.randomPassword();
            System.out.println("Your random password is: " +
                    password +
                    "\nPlease re-enter your password here:");
            if (!Runner.getScn().nextLine().equals(password)) return "Couldn't create user: confirmation failed!";
        }
        if (passwordChecker(password) != null) return passwordChecker(password);
        return password;
    }

    public static String usernameCheck(Matcher matcher) {
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        if (username.equals("random")) {
            username = randomUsernameGenerator(username);
            System.out.println("Username already used! do you like to use\"" +
                    username +
                    "\" instead?(Yes/No)");
            if (Runner.getScn().nextLine().equals("No")) return "Couldn't create user: username in use!";
            return username;
        }
    }
}
