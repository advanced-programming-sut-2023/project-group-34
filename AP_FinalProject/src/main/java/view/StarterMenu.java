package view;

import controller.Runner;
import controller.UserController;
import model.Captcha;
import model.enums.Commands;
import model.user.Password;

import java.util.regex.Matcher;

import static controller.UserController.*;

public class StarterMenu {


    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = Runner.getScn().nextLine();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput (input, Commands.CREATE_USER)) != null) {
                String username;
                String password;
                String email = matcher.group("email");
                String response = UserController.registerUserPart1(matcher);
                String slogan = matcher.group("slogan");
                String nickname = matcher.group("nickname");
                boolean flag = false;
                if (slogan != null) {
                    flag = slogan.equals("random");
                }
                Password passwordObject;
                if (response != null) {
                    System.out.println(response);
                    continue;
                }
                else {
                    if ((username = usernameCheck(matcher)).equals("Couldn't create user: username in use!"))
                        System.out.println("Couldn't create user: username in use!");
                    else {
                        slogan = sloganHandler(matcher);
                        if (flag)System.out.println("Your slogan is: \"" +
                                slogan +
                                "\"");
                        flag = false;
                        if ((password = passwordCheck(matcher)).equals("weak password(doesn't have needed chars)!") ||
                                password.equals("weak password(less than 6 chars)!") ||
                                password.equals("confirmation failed!"))
                            System.out.println(password);
                        else {
                            passwordObject = new Password(password);
                            slogan = sloganHandler(matcher);
                            if (flag)System.out.println("Your slogan is: \"" +
                                    slogan +
                                    "\"");
                            System.out.println("Pick a security question:" + "\n");
                            System.out.println("1. What is my father's name?");
                            System.out.println("2. What is my first pet's name?");
                            System.out.println("3. What is my mother's last name?");
                            response = pickSecurityQuestion(passwordObject, Commands.getOutput(Runner.getScn().nextLine(), Commands.PICK_QUESTION));
                            if (response != null) System.out.println(response);
                            else {
                                response = captchaFunction();
                                if (response.equals("captcha is set")) {
                                    System.out.println(registerUser(username, passwordObject, email, nickname, slogan));
                                } else
                                    System.out.println(response);
                            }
                        }
                    }
                }
            }
            else if ((matcher = Commands.getOutput (input, Commands.LOGIN)) != null) {
                String result = UserController.loginUser(matcher);
                if (!result.equals("User logged in"))
                    System.out.println(result);
                if (result.equals("Username and password did not match!")) UserController.wrongPasswordsEntered();
                if (result.equals("User logged in")) {
                    result = captchaFunction();
                    if (result.equals("captcha is set")) {
                        System.out.println("User logged in");
                        return "main menu";
                    }
                    else
                        System.out.println(result);
                }
            }
            else if (Commands.getOutput (input, Commands.ENTER_FORGOT_PASSWORD_MENU) != null) {
                System.out.println("Entered forgot password menu!");
                return "forgetPassword";
            }
            else if (Commands.getOutput(input, Commands.CURRENT_MENU) != null)
                System.out.println("Starter Menu");
            else if (input.equals("exit")){
                return "exit";
            } else
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
            if (!Runner.getScn().nextLine().equals(password)) return "confirmation failed!";
        }
        if (passwordChecker(password) != null) return passwordChecker(password);
        return password;
    }

    public static String usernameCheck(Matcher matcher) {
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        if (UserController.getUserByUsername(username) != null) {
            username = randomUsernameGenerator(username);
            System.out.println("Username already used! do you like to use \"" +
                    username +
                    "\" instead?(Yes/No)");
            if (Runner.getScn().nextLine().equals("No")) return "Couldn't create user: username in use!";
        }
        return username;
    }

    public static String captchaFunction(){
        Captcha captcha = new Captcha();
        int code = captcha.getTheOriginalCode();
        System.out.println(captcha.generateCaptcha());
        System.out.println("PLease enter this captcha to finish your work");
        String codeResponse = Runner.getScn().nextLine();
        int codeResponse1;
        if (codeResponse.equals("back")) return "";
        try{
            codeResponse1 = Integer.parseInt(codeResponse);
        } catch (NumberFormatException ex){
            return "invalid command";
        }
        while(codeResponse1 != code) {
            System.out.println("Wrong number please try again");
            captcha = new Captcha();
            System.out.println(captcha.generateCaptcha());
            System.out.println("PLease enter this captcha to finish your work");
            code = captcha.getTheOriginalCode();
            String codeResponse2 = Runner.getScn().nextLine();
            if (codeResponse2.equals("back")) return "";
            try{
                codeResponse1 = Integer.parseInt(codeResponse2);
            } catch (NumberFormatException ex){
                return "invalid command";
            }

        }
        return "captcha is set";
    }
}
