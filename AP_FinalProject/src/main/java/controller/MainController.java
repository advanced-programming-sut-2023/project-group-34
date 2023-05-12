package controller;

import model.Game;
import model.map.GameMap;
import model.user.User;
import view.MainMenu;
import view.MapEditingMenu;

import java.util.regex.Matcher;

import view.*;

import static controller.MapEditingController.setCurrentGameMap;

public class MainController {
    
    public static String run () {
        while (true) {
            String response = MainMenu.run();
            switch (response) {
                case "profile menu" -> ProfileMenu.run();
                case "mapEditing menu" -> MapEditingMenu.run();
                case "game menu" -> {
                    return "game menu";
                }
                case "logout" -> {
                    return "logout";
                }
            }
        }
    }
    
    public static String changeUsername (Matcher matcher) {
        
        if (matcher.group("username") == null || matcher.group("username").replaceAll("\"", "").isEmpty()) {
            return "The username field is empty, changing username failed";
        }
        
        String username = matcher.group("username");
        if (username != null) username = username.replaceAll("\"", "");
        
        if (!UserController.nameChecker(username)) return "Username's format is invalid, changing username failed";
        
        if (UserController.getUserByUsername(username) != null && !username.equals(User.currentUser.getName()))
            return "Username already exists";
        
        if (User.currentUser.getName().equals(username))
            return "Your username is already this, changing username failed";
        
        User.currentUser.setName(username);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Username successfully changed";
    }
    
    public static String changeNickname (Matcher matcher) {
        if (matcher.group("nickname") == null || matcher.group("nickname").isEmpty()) {
            return "The nickname field is empty, changing nickname failed";
        }
        
        String nickname = matcher.group("nickname");
        nickname = nickname.replaceAll("\"", "");
        
        if (User.currentUser.getNickname().equals(nickname))
            return "Your nickname is already this, changing nickname failed";
        
        User.currentUser.setNickname(nickname);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Nickname changed successfully";
    }
    
    public static String changePasswordPart1 (Matcher matcher) {
        if (matcher.group("oldPass") == null || matcher.group("oldPass").isEmpty())
            return "The old password filed is empty, changing password failed";
        
        if (matcher.group("newPass") == null || matcher.group("newPass").isEmpty())
            return "The new password filed is empty, changing password failed";
        
        String finalOldPass = matcher.group("oldPass").replaceAll("\"", "");
        String finalNewPass = matcher.group("newPass").replaceAll("\"", "");
        
        if (finalOldPass.isEmpty() || finalNewPass.isEmpty())
            return "The required field is empty, changing password failed";
        
        String response = UserController.passwordChecker(finalNewPass);
        if (response != null && !response.isEmpty()) return response;
        
        if (!User.currentUser.getPassword().checkPassword(finalOldPass))
            return "Incorrect current password, changing password failed";
        
        if (User.currentUser.getPassword().checkPassword(finalNewPass))
            return "Your new password has to be different from your current password, changing password failed";
        return "good for now";
    }
    
    public static String changePasswordPart2 (String givenPassword, String confirmationPassword) {
        if (!givenPassword.equals(confirmationPassword))
            return "confirmation password does not match the initial password, changing password failed";
        return "good for now";
    }
    
    public static String setChangePassword (String newPass) {
        User.currentUser.getPassword().setPasswordName(newPass);
        User.updateDataBase();
        User.stayLoggedIn();
        return "password changed successfully";
    }
    
    
    public static String changePasswordRandomly1 (Matcher matcher) {
        
        if (matcher.group("oldPass") == null || matcher.group("oldPass").isEmpty())
            return "The required field is empty, changing password failed";
        
        String finalOldPass = matcher.group("oldPass");
        
        if (!User.currentUser.getPassword().checkPassword(finalOldPass))
            return "Incorrect current password, changing password failed";
        
        return "good for now";
    }
    
    public static String changePasswordRandomly2 (String givenPass, String confirmationPass) {
        if (!givenPass.equals(confirmationPass)) return "Confirmation failed";
        return "good for now";
    }
    
    public static String changePasswordRandomly3 (String newPass) {
        User.currentUser.getPassword().setPasswordName(newPass);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Password changed successfully";
    }
    
    
    public static String changeEmail (Matcher matcher) {
        if (matcher.group("email") == null || matcher.group("email").isEmpty())
            return "The required field is empty, changing email failed";
        
        String email = matcher.group("email");
        
        if (!UserController.emailChecker(email)) return "Email's format is invalid, changing email failed";
        
        if (UserController.isEmailAlreadyUsed(email) && !User.currentUser.getEmail().equals(email))
            return "Email already exists, changing email failed";
        
        if (User.currentUser.getEmail().equals(email)) return "Your email is already this, changing email failed";
        
        User.currentUser.setEmail(email);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Email changed successfully";
    }
    
    public static String changeSlogan (Matcher matcher) {
        String slogan = matcher.group("slogan");
        slogan = slogan.replaceAll("\"", "");
        if (slogan.isEmpty()) return "The slogan field is empty, changing slogan failed";
        
        if (User.currentUser.getSlogan() != null && !User.currentUser.getSlogan().isEmpty() && User.currentUser.getSlogan().equals(slogan))
            return "Your slogan is already this, changing slogan failed";
        
        User.currentUser.setSlogan(slogan);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Slogan changed successfully";
    }
    
    public static String changeSloganRandomly (Matcher matcher) {
        String newSlogan = UserController.randomSloganGenerator();
        while (newSlogan.equals(User.currentUser.getSlogan())) {
            newSlogan = UserController.randomSloganGenerator();
        }
        
        User.currentUser.setSlogan(newSlogan);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Slogan changed successfully";
    }
    
    public static String removeSlogan (Matcher matcher) {
        if (User.currentUser.getSlogan() == null || User.currentUser.getSlogan().isEmpty())
            return "Your slogan field is already empty, removing slogan failed";
        
        User.currentUser.setSlogan(null);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Slogan removed successfully";
    }
    
    public static String displaySlogan () {
        if (User.currentUser.getSlogan() == null || User.currentUser.getSlogan().isEmpty()) return "Slogan is empty!";
        else return User.currentUser.getSlogan();
    }
    
    public static String showProfile () {
        String theWholeProfile = "";
        theWholeProfile = theWholeProfile.concat("Username: " + User.currentUser.getName() + "\n");
        theWholeProfile = theWholeProfile.concat("Nickname: " + User.currentUser.getNickname() + "\n");
        theWholeProfile = theWholeProfile.concat("Email: " + User.currentUser.getEmail() + "\n");
        if (User.currentUser.getSlogan() != null && !User.currentUser.getSlogan().isEmpty())
            theWholeProfile = theWholeProfile.concat("Slogan: " + User.currentUser.getSlogan() + "\n");
        theWholeProfile = theWholeProfile.concat("High score: " + User.currentUser.getScore());
        return theWholeProfile;
    }
    
    public static String createNewGame (Matcher matcher) {
        User.currentUser.loadUserMapsFromDataBase();
        String mapName = matcher.group("mapName").trim().replaceAll("\"", "");
        if (mapName.isEmpty()) return "Empty field!";
        if (User.currentUser.getMapByName(mapName) == null) {
            return "that map does not exist!";
        }
        GameController.setCurrentGame(new Game(new GameMap(User.currentUser.getMapByName(mapName))));
        GameController.currentGame.setCurrentGovernment(User.currentUser.getGovernment());
        return null;
    }
    
    public static String newMap (Matcher matcher) {
        String name = matcher.group("mapName");
        if (name != null) name = name.replaceAll("\"", "");
        if (name == null || name.equals("")) return "Empty field!";
        for (GameMap customMap : User.currentUser.getCustomMaps()) {
            if (customMap.name.equals(name)) return "You already hava a map with the name given!";
        }
        GameMap customMap = new GameMap(name);
        User.currentUser.getCustomMaps().add(customMap);
        setCurrentGameMap(customMap);
        return null;
    }
    
    public static String editMap (Matcher matcher) {
        String name = matcher.group("mapName");
        if (name != null) name = name.replaceAll("\"", "");
        if (name == null || name.equals("")) return "Empty field!";
        for (GameMap customMap : User.currentUser.getCustomMaps()) {
            if (customMap.name.equals(name)) {
                setCurrentGameMap(customMap);
                return null;
            }
        }
        return "No map with the name given!";
    }
    
    public static String showUserMaps () {
        StringBuilder output = new StringBuilder();
        output.append(User.getCurrentUser().getName()).append(" maps:");
        int i = 0;
        for (; i < User.getCurrentUser().getCustomMaps().size(); i++) {
            output.append('\n');
            output.append(i + 1);
            output.append(" )--Name--> ");
            output.append(User.getCurrentUser().getCustomMaps().get(i).name);
            output.append(" | --Size--> ");
            output.append(User.getCurrentUser().getCustomMaps().get(i).getSize());
            output.append(" X ");
            output.append(User.getCurrentUser().getCustomMaps().get(i).getSize());
        }
        if (i == 0) return "User has no custom maps yet!";
        return output.toString();
    }
    
}
