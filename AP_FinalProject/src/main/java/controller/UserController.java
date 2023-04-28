package controller;

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
import java.util.Random;
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
        if (username != null) username = username.replace("\"", "");
        String password = matcher.group("password");
        if (password != null) password = password.replace("\"", "");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        if (passwordConfirmation != null) passwordConfirmation = passwordConfirmation.replace("\"", "");
        String email = matcher.group("email");
        if (email != null) email = email.replace("\"", "");
        String nickname = matcher.group("nickname");
        if (nickname != null) nickname = nickname.replace("\"", "");
        String slogan = matcher.group("slogan");
        if (slogan != null) slogan = slogan.replace("\"", "");
        if (username == null ||
                username.equals("") ||
                password == null ||
                password.equals("") ||
                nickname == null ||
                nickname.equals("") ||
                email == null ||
                email.equals("") ||
                (matcher.group("sloganFlag") != null && (slogan == null || slogan.equals("")))) return "Couldn't create user: empty field!";
        if (!Validations.check(username, Validations.VALID_USERNAME)) return "Couldn't create user: invalid username!";
        if (getUserByUsername(username) != null) {
            username = randomUsernameGenerator(username);
            System.out.println("Username already used! do you like to use\"" +
                    username +
                    "\" instead?(Yes/No)");
            String response = Runner.getScn().nextLine().trim();
            if (response.equals("No")) return "Couldn't create user: username in use!";
        }
        if (password.equals("random")) {
            password = Password.randomPassword();
            System.out.println("Your random password is: " +
                    password +
                    "\nPlease re-enter your password here:");
            passwordConfirmation = controller.Runner.getScn().nextLine();
        }
        if (passwordChecker(password) != null) return passwordChecker(password);
        if (slogan.equals("random")) slogan = randomSloganGenerator();
        if (!password.equals(passwordConfirmation)) return "Couldn't create user: password confirmation failed!";
        if (isEmailAlreadyUsed(email)) return "Couldn't create user: email already in use!";
        if (!Validations.check(email, Validations.VALID_EMAIL)) return "Couldn't create user: invalid email!";
        model.user.Password passwordObject = new Password(password);
        String result = pickSecurityQuestion(passwordObject);
        if (result != null) return result;
        User user = new model.user.User(username, passwordObject, nickname, email);
        if (slogan != null) user.setSlogan(slogan);
        //TODO: JSON
        return "User created successfully!";
    }

    public static String pickSecurityQuestion(Password password) {
        String input = controller.Runner.getScn().nextLine();
        Matcher matcher = model.enums.Commands.getOutput(input, Commands.PICK_QUESTION);
        if (matcher == null) return "Picking security Question failed: Invalid command!";
        int secQNum = Integer.parseInt(matcher.group("questionNumber"));
        if (secQNum > 3) return "Picking security Question failed: Invalid security Question!";
        String answer = matcher.group("answer");
        String answerConfirmation = matcher.group("answerConfirm");
        if (!Objects.equals(answer, answerConfirmation)) return "Picking security Question failed: Confirmation failed!";
        password.setSecurityQuestion(SecurityQuestion.values()[secQNum - 1]);
        password.setAnswer(answer);
        return null;
    }

    public static boolean nameChecker(String name){
        return Validations.check(name, Validations.VALID_USERNAME);
    }

    public static boolean emailChecker(String email) {
        return model.enums.Validations.check(email, Validations.VALID_EMAIL);
    }

    public static model.user.User getUserByUsername(String name) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    public static boolean isEmailAlreadyUsed(String email) {
        for (model.user.User user : model.user.User.getUsers()) {
            if (user.getEmail().toLowerCase(Locale.ROOT).equals(email.toLowerCase())) return true;
        }
        return false;
    }

    public static String passwordChecker(String password){
        if (password.length() < 6) return "Couldn't create user: weak password(less than 6 chars)!";
        if (!Validations.check(password, Validations.STRONG_PASSWORD))
            return "Couldn't create user: weak password(doesn't have needed chars)!";
        return null;
    }

    public static String loginUser(Matcher matcher){
        String username = matcher.group("username");
        if (username != null) username = username.replace("\"", "");
        String password = matcher.group("password");
        if (password != null) password = password.replace("\"", "");
        model.user.User user;
        if ((user = getUserByUsername(username)) == null) return "No user with the given username!";
        if (!user.getPassword().checkPassword(password)) return "Username and password didn’t match!";
        model.user.User.setCurrentUser(user);
        if (matcher.group("flag") != null) model.user.User.stayLoggedIn();
        wrongPasswordsCount = 0;
        return "User logged in";
    }

    public static String randomSloganGenerator(){
        Random random = new Random();
        return Slogan.values()[random.nextInt(0, 3)].slogan;
    }

    public static String randomUsernameGenerator(String currentUsername){
        if (getUserByUsername(currentUsername) == null) return currentUsername;
        else return randomUsernameGenerator(currentUsername + "1");
    }

    public static String forgotPassword(Matcher matcher){
        String username = matcher.group("username");
        User user = getUserByUsername(username);
        if (user == null) return "No user with the id given!";
        Password password = user.getPassword();
        System.out.println(password.getSecurityQuestion());
        String answer = Runner.getScn().nextLine();
        if (!password.checkAnswer(answer)) return "Wrong answer!";
        System.out.println("Enter new password:");
        String newPassword = Runner.getScn().nextLine();
        if (newPassword.equals("random")) {
            newPassword = Password.randomPassword();
            System.out.println("Your random password is: " +
                    newPassword);
        }
        if (!Validations.check(newPassword, Validations.STRONG_PASSWORD)) return "Weak password!";
        System.out.println("Re-enter your password:");
        String confirm = Runner.getScn().nextLine();
        if (!confirm.equals(newPassword)) return "confirmation failed!";
        password.setPasswordName(newPassword);
        return "success!";
    }

    private static int wrongPasswordsCount = 0;

    public static void wrongPasswordsEntered(){
        //TODO: improve mechanism
        wrongPasswordsCount++;
        if (wrongPasswordsCount % 5 != 0) {
            System.out.println("you are locked!");
            try {
                Thread.sleep(wrongPasswordsCount * 2000L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
