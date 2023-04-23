package controller;

import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.map.GameMap;
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
        if (nickname.isEmpty()) return "The nickname field is empty, changing nickname failed";

        if (User.currentUser.getNickname().equals(nickname)) return "Your nickname is already this, changing nickname failed";

        User.currentUser.setNickname(nickname);
        //Info has to be changed in Json as well
        return "Nickname changed successfully";
    }

    public static String changePassword(Matcher matcher){
        String oldPass = matcher.group("oldPass");
        String oldPass1 = matcher.group("oldPass1");
        String newPass = matcher.group("newPass");
        String newPass1 = matcher.group("newPass1");

        String finalOldPass;
        if (!oldPass.isEmpty())
            finalOldPass = oldPass;
        else
            finalOldPass = oldPass1;

        String finalNewPass;
        if (!newPass.isEmpty())
            finalNewPass = newPass;
        else
            finalNewPass = newPass1;

        if (finalOldPass.isEmpty() || finalNewPass.isEmpty()) return "The required field is empty, changing password failed";

        String response = UserController.passwordChecker(newPass1);
        if (!response.isEmpty()) return response;

        if (!User.currentUser.getPassword().checkPassword(finalOldPass)) return "Incorrect current password, changing password failed";

        return null;

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

    public static String displayScore(){ return null; }

    public static String displayRank(){ return null; }

    public static String displaySlogan(){ return null;}

    public static String showProfile(Matcher matcher){
        return null;
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
