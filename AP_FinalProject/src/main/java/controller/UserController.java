package controller;

import view.ForgetPasswordMenu;
import view.StarterMenu;
import model.enums.SecurityQuestion;
import model.enums.Slogan;

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
        return null;
    }

    public static boolean nameChecker(String name){
        return true;
    }

    public static boolean emailChecker(String email) {
        return true;
    }

    public static boolean isUserNameAlreadyUsed(String name) {
        return false;
    }
    public static boolean isEmailAlreadyUsed(String email) {
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
