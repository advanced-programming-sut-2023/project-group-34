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

    private static boolean nameChecker(String name){
        return true;
    }

    private static boolean emailChecker(String email) {
        return true;
    }

    private static boolean isUserNameAlreadyUsed(String name) {
        return false;
    }
    private static boolean isEmailAlreadyUsed(String email) {
        return false;
    }

    private static boolean passwordChecker(String password){
        return true;
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

    private static String randomSloganGenerator(){
        return null;
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
