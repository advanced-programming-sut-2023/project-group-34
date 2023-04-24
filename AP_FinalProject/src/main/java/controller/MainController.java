package controller;

import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.map.GameMap;
import model.user.Password;
import model.user.User;
import view.MainMenu;
import view.MapEditingMenu;
import view.ProfileMenu;

import java.util.regex.Matcher;

public class MainController {

    public static GameMap currentGameMap;

    public static String run(){
        while (true){
            String response = MainMenu.run();
            switch (response) {
                case "profile menu":
                    ProfileMenu.run();
                    break;
                case "mapEditing menu":
                    MapEditingMenu.run();
                    break;
                case "game menu":
                    return "game menu";
                case "logout":
                    return "logout";
            }
        }
    }

    public static String changeUsername (Matcher matcher){
        String username = matcher.group("username");
        username = username.replaceAll("\"", "");

        if (username.isEmpty()) return "The username field is empty, changing username failed";

        if (!UserController.nameChecker(username)) return "Username's format is invalid, changing username failed";

        if (UserController.getUserByUsername(username) != null) return "Username already exists, changing username failed";

        if (User.currentUser.getName().equals(username)) return "You username is already this, changing username failed";

        User.currentUser.setName(username);
        //Info has to be changed in Json as well
        return "Username successfully changed";
    }

    public static String changeNickname (Matcher matcher){
        String nickname = matcher.group("nickname");
        nickname = nickname.replaceAll("\"", "");
        if (nickname.isEmpty()) return "The nickname field is empty, changing nickname failed";

        if (User.currentUser.getNickname().equals(nickname)) return "Your nickname is already this, changing nickname failed";

        User.currentUser.setNickname(nickname);
        //Info has to be changed in Json as well
        return "Nickname changed successfully";
    }

    public static String changePassword(Matcher matcher){
        String finalOldPass = matcher.group("oldPass");
        String finalNewPass = matcher.group("newPass");

        if (finalOldPass.isEmpty() || finalNewPass.isEmpty())
            return "The required field is empty, changing password failed";

        String response = UserController.passwordChecker(finalNewPass);
        if (!response.isEmpty()) return response;

        if (!User.currentUser.getPassword().getPasswordName().equals(finalOldPass))
            return "Incorrect current password, changing password failed";

        if (User.currentUser.getPassword().getPasswordName().equals(finalNewPass))
            return "Your new password has to be different from your current password, changing password failed";

        System.out.println("Please renter your new password for confirmation");
        String finalNewPass1 = Runner.getScn().nextLine();

        if (!finalNewPass1.equals(finalNewPass))
            return "Confirmation failed, changing password failed";

        //Info has to be changed in Json as well
        User.currentUser.getPassword().setPasswordName(finalNewPass1);
        return "Password changed successfully";
    }

    public static String changePasswordRandomly(Matcher matcher){

        String finalOldPass = matcher.group("oldPass");

        if (finalOldPass.isEmpty()) return "The required field is empty, changing password failed";

        if (!User.currentUser.getPassword().getPasswordName().equals(finalOldPass))
            return "Incorrect current password, changing password failed";

        String finalNewPass = Password.randomPasswordGenerator();

        while(finalNewPass.equals(finalOldPass)) {
            finalNewPass = Password.randomPasswordGenerator();
        }

        //Info has to be changed in Json as well
        User.currentUser.getPassword().setPasswordName(finalNewPass);
        return "Password changed successfully";
    }

    public static String changeEmail(Matcher matcher){
        String email = matcher.group("email");
        if (email.isEmpty()) return "The email field is empty, changing email failed";

        if (!UserController.emailChecker(email)) return "Email's format is invalid, changing email failed";

        if (UserController.isEmailAlreadyUsed(email)) return "Email already exists, changing email failed";

        if (User.currentUser.getEmail().equals(email)) return "Your email is already this, changing email failed";

        //Info has to be changed in Json as well
        User.currentUser.setEmail(email);
        return "Email changed successfully";
    }

    public static String changeSlogan(Matcher matcher){
        String slogan = matcher.group("slogan");
        slogan = slogan.replaceAll("\"", "");
        if (slogan.isEmpty()) return "The slogan field is empty, changing slogan failed";

        if (!User.currentUser.getSlogan().isEmpty() && User.currentUser.getSlogan().equals(slogan))
            return "Your slogan is already this, changing slogan failed";

        //Info has to be changed in Json as well
        User.currentUser.setSlogan(slogan);
        return "Slogan changed successfully";
    }

    public static String changeSloganRandomly(Matcher matcher){
        String newSlogan =  UserController.randomSloganGenerator();
        User.currentUser.setSlogan(newSlogan);
        return "Slogan changed successfully";
    }

    public static String removeSlogan(Matcher matcher){
        if (User.currentUser.getSlogan().isEmpty()) return "Your slogan field is already empty, removing slogan failed";

        //Info has to be changed in Json as well
        User.currentUser.setSlogan(null);
        return "Slogan removed successfully";
    }

    public static String displaySlogan(){
        if (User.currentUser.getSlogan().isEmpty()) return "Slogan is empty!";
        else return User.currentUser.getSlogan();
    }

    public static String showProfile(){
        String theWholeProfile = "";
        theWholeProfile = theWholeProfile.concat("Username: " + User.currentUser.getName() + "\n");
        theWholeProfile = theWholeProfile.concat("Password: " + User.currentUser.getPassword().getPasswordName() + "\n");
        theWholeProfile = theWholeProfile.concat("Nickname: " + User.currentUser.getNickname() + "\n");
        theWholeProfile = theWholeProfile.concat("Email: " + User.currentUser.getEmail() + "\n");
        if (!User.currentUser.getSlogan().isEmpty())
            theWholeProfile = theWholeProfile.concat("Slogan: " + User.currentUser.getSlogan() + "\n");
        theWholeProfile = theWholeProfile.concat("Highscore: " + User.currentUser.getScore() + "\n");
        return theWholeProfile;
    }

    public static String changeBlockFloorType(Matcher matcher){
        BlockType blockType = BlockType.stringToBlockType(matcher.group("type"));
        if (blockType == null) return "Invalid texture!";
        String x = matcher.group("singleX");
        if (x == null) {
            int x1 = Integer.parseInt(matcher.group("x1"));
            int x2 = Integer.parseInt(matcher.group("x2"));
            int y1 = Integer.parseInt(matcher.group("y1"));
            int y2 = Integer.parseInt(matcher.group("y2"));
            return currentGameMap.setRectangleTexture(x1, x2, y1, y2, blockType);
        }
        else {
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(matcher.group("singleY"));
            return currentGameMap.setRectangleTexture(x1, x1, y1, y1, blockType);
        }
    }

    public static String clearBlock(Matcher matcher){
        int i = Integer.parseInt(matcher.group("yAxis"));
        int j = Integer.parseInt(matcher.group("xAxis"));
        return currentGameMap.clearBlock(i, j);
    }

    public static String dropRock(Matcher matcher){
        String direction = matcher.group("direction");
        int y = Integer.parseInt(matcher.group("yAxis"));
        int x = Integer.parseInt(matcher.group("xAxis"));
        return switch (direction) {
            case "north" -> currentGameMap.setRectangleTexture(x, x, y, y, BlockType.NORTH_BOULDER);
            case "south" -> currentGameMap.setRectangleTexture(x, x, y, y, BlockType.SOUTH_BOULDER);
            case "west" -> currentGameMap.setRectangleTexture(x, x, y, y, BlockType.WEST_BOULDER);
            case "east" -> currentGameMap.setRectangleTexture(x, x, y, y, BlockType.EAST_BOULDER);
            case "random" -> currentGameMap.setRectangleTexture(x, x, y, y, BlockType.values()[Runner.getRandomNumber(4)]);
            default -> "Invalid direction!";
        };
    }

    public static String dropTree(Matcher matcher){
        BlockFillerType blockFillerType = BlockFillerType.stringToType(matcher.group("type"));
        if (blockFillerType == null) return "Invalid type!";
        int i = Integer.parseInt(matcher.group("yIndex"));
        int j = Integer.parseInt(matcher.group("xIndex"));
        if (!currentGameMap.checkBounds(i, j)) return "Out of bounds!";
        if (!(currentGameMap.getMap()[i][j].getBlockType().equals(BlockType.GRASS) || currentGameMap.getMap()[i][j].getBlockType().equals(BlockType.MEADOW) || currentGameMap.getMap()[i][j].getBlockType().equals(BlockType.DENSE_MEADOW) || currentGameMap.getMap()[i][j].getBlockType().equals(BlockType.GROUND))) return "Can't put a tree here!";
        currentGameMap.getMap()[i][j].setBLockFiller(blockFillerType);
        return "Success!";
    }

    public static String dropBuilding(Matcher matcher){
        return null;
        //TODO: Arshia joon
    }

    public static String dropUnit(Matcher matcher){
        return null;
    }

}
