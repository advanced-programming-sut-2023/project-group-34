package controller;

import model.Captcha;
import model.user.User;
import model.enums.Commands;
import model.enums.Validations;
import model.user.Password;
import view.ForgetPasswordMenu;
import view.StarterMenu;
import model.enums.SecurityQuestion;
import model.enums.Slogan;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

public class UserController {
    static private SecurityQuestion securityQuestion;
    static private Slogan slogan;
    
    public static String run () {
        
        while (true) {
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
    
    public static String registerUserPart1 (Matcher matcher) {
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        String password = matcher.group("password");
        if (password != null) password = password.replaceAll("\"", "");
        String email = matcher.group("email");
        if (email != null) email = email.replaceAll("\"", "");
        String nickname = matcher.group("nickname");
        if (nickname != null) nickname = nickname.replaceAll("\"", "");
        String slogan = matcher.group("slogan");
        if (slogan != null) slogan = slogan.replaceAll("\"", "");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        if (passwordConfirmation != null) passwordConfirmation = passwordConfirmation.replaceAll("\"", "");
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || nickname == null ||
                nickname.isEmpty() || email == null || email.equals("") ||
                (passwordConfirmation == null && !password.equals("random")) ||
                (passwordConfirmation.isEmpty() && !password.equals("random")) ||
                (matcher.group("sloganFlag") != null && (slogan == null || slogan.isEmpty())))
            return "Couldn't create user: empty field!";
        if (!Validations.check(username, Validations.VALID_USERNAME)) return "Couldn't create user: invalid username!";
        if (isEmailAlreadyUsed(email)) return "Couldn't create user: email already in use!";
        if (!Validations.check(email, Validations.VALID_EMAIL)) return "Couldn't create user: invalid email format!";
        if (!password.equals(passwordConfirmation) && !password.equals("random"))
            return "Password and its confirmation do not match";
        return null;
    }
    
    public static String registerUser (String username, Password password, String email, String nickname, String slogan) {
        User user = new model.user.User(username, password, nickname, email);
        if (slogan != null) user.setSlogan(slogan);
        User.updateDataBase();
        return "User created successfully!";
    }
    
    public static String sloganHandler (Matcher matcher) {
        String slogan = matcher.group("slogan");
        if (slogan != null && slogan.equals("random")) slogan = randomSloganGenerator();
        return slogan;
    }
    
    public static String pickSecurityQuestion (Password password, Matcher matcher) {
        if (matcher == null) return "Picking security Question failed: Invalid command!";
        String securityQuestionNumberString = matcher.group("questionNumber");
        if (securityQuestionNumberString != null)
            securityQuestionNumberString = securityQuestionNumberString.replaceAll("\"", "");
        else return "empty field";
        if (securityQuestionNumberString.equals("")) return "empty field";
        if (matcher.group("answer") == null || matcher.group("answer").isEmpty()) return "empty field";
        if (matcher.group("answerConfirm") == null || matcher.group("answerConfirm").isEmpty()) return "empty field";
        int secQNum = Integer.parseInt(securityQuestionNumberString);
        if (secQNum > 3) return "Picking security Question failed: Invalid security Question!";
        String answer = matcher.group("answer");
        String answerConfirmation = matcher.group("answerConfirm");
        if (!Objects.equals(answer, answerConfirmation))
            return "Picking security Question failed: Confirmation failed!";
        password.setSecurityQuestion(SecurityQuestion.values()[secQNum - 1]);
        password.setAnswer(answer);
        return null;
    }
    
    public static boolean nameChecker (String name) {
        return Validations.check(name, Validations.VALID_USERNAME);
    }
    
    public static boolean emailChecker (String email) {
        return model.enums.Validations.check(email, Validations.VALID_EMAIL);
    }
    
    public static model.user.User getUserByUsername (String name) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }
    
    public static boolean isEmailAlreadyUsed (String email) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getEmail().toLowerCase(Locale.ROOT).equals(email.toLowerCase())) return true;
        }
        return false;
    }
    
    public static String passwordChecker (String password) {
        if (password.length() < 6) return "weak password(less than 6 chars)!";
        if (!Validations.check(password, Validations.STRONG_PASSWORD))
            return "weak password(doesn't have needed chars)!";
        return null;
    }
    
    public static String loginUser (Matcher matcher) {
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        else return "empty field";
        String password = matcher.group("password");
        if (password != null) password = password.replaceAll("\"", "");
        else return "empty field";
        if (password.equals("") || username.equals("")) return "empty field";
        model.user.User user;
        if ((user = getUserByUsername(username)) == null) return "No user with the given username!";
        if (!user.getPassword().checkPassword(password)) return "Username and password did not match!";
        model.user.User.setCurrentUser(user);
        if (matcher.group("flag") != null) model.user.User.stayLoggedIn();
        wrongPasswordsCount = 0;
        return "User logged in";
    }
    
    public static String randomSloganGenerator () {
        return Slogan.values()[Runner.getRandomNumber(3)].slogan;
    }
    
    public static String randomUsernameGenerator (String currentUsername) {
        if (getUserByUsername(currentUsername) == null) return currentUsername;
        else return randomUsernameGenerator(currentUsername + "1");
    }
    
    public static String forgotPassword (Matcher matcher) {
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        else return "empty field";
        if (username.equals("")) return "empty field";
        User user = getUserByUsername(username);
        if (user == null) return "No user with the id given!";
        return "good for now";
    }
    
    public static String forgotPassword2 (String answer, Password password) {
        if (!password.checkAnswer(answer)) return "Wrong answer!";
        return "enter password";
    }
    
    public static String randomPassword = "a";
    public static String forgotPassword3 (String newPassword) {
        if (newPassword.equals("random")) {
            newPassword = Password.randomPassword();
            randomPassword = newPassword;
            System.out.println("Your random password is: " + newPassword);
        }
        if (!Validations.check(newPassword, Validations.STRONG_PASSWORD)) return "Weak password!";
        return "go to confirmation";
    }
    
    public static String forgotPassword4 (String confirm, String newPassword) {
        if (!confirm.equals(newPassword)) return "confirmation failed!";
        return "good for now";
    }
    
    public static String setForgotPassword (String newPass, Password password) {
        password.setPasswordName(newPass);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Password changed successfully";
    }
    
    
    private static int wrongPasswordsCount = 0;
    
    public static void wrongPasswordsEntered () {
        wrongPasswordsCount++;
        if (wrongPasswordsCount % 5 == 0) {
            System.out.println("you are locked!");
            try {
                Thread.sleep(wrongPasswordsCount * 2000L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
