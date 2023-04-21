package controller;

import model.enums.Validations;
import model.user.Password;
import view.ForgetPasswordMenu;
import view.StarterMenu;
import model.enums.SecurityQuestion;
import model.enums.Slogan;

import javax.sql.rowset.serial.SerialStruct;
import java.util.Locale;
import java.util.regex.Matcher;

public class UserController {
    static private SecurityQuestion securityQuestion;
    static private Slogan slogan;

    public static String run(){

        while(true){
            String response = StarterMenu.run();
            switch (response) {
                case "exit" -> {
                    return "exit";
                }
                case "forgetPassword" -> ForgetPasswordMenu.run();
                case "main menu" -> {
                    return "main menu";
                }
            }
        }
    }

    public static String registerUser(Matcher matcher){
        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        String slogan = matcher.group("slogan").trim();
        if (username == null || password == null || nickname == null || email == null ||
                (matcher.group("sloganFlag") != null && slogan == null)) return "Couldn't create user: empty field!";
        if (!Validations.check(username, Validations.VALID_USERNAME)) return "Couldn't create user: invalid username!";
        if (isUserNameAlreadyUsed(username)) return "Couldn't create user: username in use!";
        if (password.length() < 6) return "Couldn't create user: weak password(less than 6 chars)!";
        if (!Validations.check(password, Validations.STRONG_PASSWORD))
            return "Couldn't create user: weak password(doesn't have needed chars)!";
        //TODO: random s
        if (password.equals("random")) {
            password = Password.randomPassword();
            System.out.println("Your random password is: " +
                    password +
                    "\nPlease re-enter your password here:");
            passwordConfirmation = controller.Runner.getScn().nextLine();
            if (!password.equals(passwordConfirmation)) return "Couldn't create user: password confirmation failed!";
        }
        if (isEmailAlreadyUsed(email)) return "Couldn't create user: email already in use!";
        if (!Validations.check(email, Validations.VALID_EMAIL)) return "Couldn't create user: invalid email!";
        model.user.Password passwordObject = new Password();
        passwordObject.setPasswordName(password);
        new model.user.User(username, passwordObject, nickname, email);
        //TODO: JSON
    }

    public static boolean nameChecker(String name){
        return Validations.check(name, Validations.VALID_USERNAME);
    }

    public static boolean emailChecker(String email) {
        return model.enums.Validations.check(email, Validations.VALID_EMAIL);
    }

    public static boolean isUserNameAlreadyUsed(String name) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getName().equals(name)) return true;
        }
        return false;
    }
    public static boolean isEmailAlreadyUsed(String email) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getEmail().toLowerCase(Locale.ROOT).equals(email.toLowerCase())) return true;
        }
        return false;
    }

    public static String passwordChecker(String password){
        return null;
    }

    public static String loginUser(Matcher matcher){
        return null;
    }

    private static String pickSecurityQuestion(){
        return null;
    }

    public static String pickRandomSecurityQuestion(){
        return null;
    }

    public static String randomPasswordGenerator(){
        return null;
    }

    public static String randomSloganGenerator(){
        return null;
    }

    public static String randomUsernameGenerator(){
        return null;
        //This method creates a username similar to the username that user has entered and gives a recommendation
        //We have to make sure the new generated username does not exit itself
    }

    private static String emailChecker(){
        return null;
    }

    public static String forgotPassword(){
        return null;
    }

    private static void countWrongPasswordsEntered(){

    }
}
