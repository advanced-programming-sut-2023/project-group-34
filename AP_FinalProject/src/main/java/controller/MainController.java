package controller;

import model.Dictionaries;
import model.Game;
import model.building.*;
import model.enums.BlockType;
import model.government.Government;
import model.map.Block;
import model.map.GameMap;
import model.user.Password;
import model.user.User;
import view.MainMenu;
import view.MapEditingMenu;
import view.ProfileMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

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
        String username = matcher.group("username");
        username = username.replaceAll("\"", "");
        
        if (username.isEmpty()) return "The username field is empty, changing username failed";
        
        if (!UserController.nameChecker(username)) return "Username's format is invalid, changing username failed";
        
        if (UserController.getUserByUsername(username) != null)
            return "Username already exists, changing username failed";
        
        if (User.currentUser.getName().equals(username))
            return "You username is already this, changing username failed";
        
        User.currentUser.setName(username);
        User.updateDataBase();
        return "Username successfully changed";
    }
    
    public static String changeNickname (Matcher matcher) {
        String nickname = matcher.group("nickname");
        nickname = nickname.replaceAll("\"", "");
        if (nickname.isEmpty()) return "The nickname field is empty, changing nickname failed";
        
        if (User.currentUser.getNickname().equals(nickname))
            return "Your nickname is already this, changing nickname failed";
        
        User.currentUser.setNickname(nickname);
        User.updateDataBase();
        return "Nickname changed successfully";
    }
    
    public static String changePassword (Matcher matcher) {
        String finalOldPass = matcher.group("oldPass");
        String finalNewPass = matcher.group("newPass");
        
        if (finalOldPass.isEmpty() || finalNewPass.isEmpty())
            return "The required field is empty, changing password failed";
        
        String response = UserController.passwordChecker(finalNewPass);
        if (!response.isEmpty()) return response;
        
        if (User.currentUser.getPassword().checkPassword(finalOldPass))
            return "Incorrect current password, changing password failed";
        
        if (User.currentUser.getPassword().checkPassword(finalNewPass))
            return "Your new password has to be different from your current password, changing password failed";
        
        System.out.println("Please renter your new password for confirmation");
        String finalNewPass1 = Runner.getScn().nextLine();
        
        if (!finalNewPass1.equals(finalNewPass)) return "Confirmation failed, changing password failed";
        
        User.currentUser.getPassword().setPasswordName(finalNewPass1);
        User.updateDataBase();
        return "Password changed successfully";
    }
    
    public static String changePasswordRandomly (Matcher matcher) {
        
        String finalOldPass = matcher.group("oldPass");
        
        if (finalOldPass.isEmpty()) return "The required field is empty, changing password failed";
        
        if (!User.currentUser.getPassword().checkPassword(finalOldPass))
            return "Incorrect current password, changing password failed";
        
        String finalNewPass = Password.randomPasswordGenerator();
        
        while (finalNewPass.equals(finalOldPass)) {
            finalNewPass = Password.randomPasswordGenerator();
        }
        
        
        User.currentUser.getPassword().setPasswordName(finalNewPass);
        User.updateDataBase();
        return "Password changed successfully";
    }
    
    public static String changeEmail (Matcher matcher) {
        String email = matcher.group("email");
        if (email.isEmpty()) return "The email field is empty, changing email failed";
        
        if (!UserController.emailChecker(email)) return "Email's format is invalid, changing email failed";
        
        if (UserController.isEmailAlreadyUsed(email)) return "Email already exists, changing email failed";
        
        if (User.currentUser.getEmail().equals(email)) return "Your email is already this, changing email failed";
        
        
        User.currentUser.setEmail(email);
        User.updateDataBase();
        return "Email changed successfully";
    }
    
    public static String changeSlogan (Matcher matcher) {
        String slogan = matcher.group("slogan");
        slogan = slogan.replaceAll("\"", "");
        if (slogan.isEmpty()) return "The slogan field is empty, changing slogan failed";
        
        if (!User.currentUser.getSlogan().isEmpty() && User.currentUser.getSlogan().equals(slogan))
            return "Your slogan is already this, changing slogan failed";
        
        //Info has to be changed in Json as well
        User.currentUser.setSlogan(slogan);
        User.updateDataBase();
        return "Slogan changed successfully";
    }
    
    public static String changeSloganRandomly (Matcher matcher) {
        String newSlogan = UserController.randomSloganGenerator();
        User.currentUser.setSlogan(newSlogan);
        User.updateDataBase();
        return "Slogan changed successfully";
    }
    
    public static String removeSlogan (Matcher matcher) {
        if (User.currentUser.getSlogan().isEmpty()) return "Your slogan field is already empty, removing slogan failed";
        
        User.currentUser.setSlogan(null);
        User.updateDataBase();
        return "Slogan removed successfully";
    }
    
    public static String displaySlogan () {
        if (User.currentUser.getSlogan().isEmpty()) return "Slogan is empty!";
        else return User.currentUser.getSlogan();
    }
    
    public static String showProfile () {
        String theWholeProfile = "";
        theWholeProfile = theWholeProfile.concat("Username: " + User.currentUser.getName() + "\n");
        theWholeProfile = theWholeProfile.concat("Nickname: " + User.currentUser.getNickname() + "\n");
        theWholeProfile = theWholeProfile.concat("Email: " + User.currentUser.getEmail() + "\n");
        if (!User.currentUser.getSlogan().isEmpty())
            theWholeProfile = theWholeProfile.concat("Slogan: " + User.currentUser.getSlogan() + "\n");
        theWholeProfile = theWholeProfile.concat("High score: " + User.currentUser.getScore() + "\n");
        return theWholeProfile;
    }
    
    public static String setKeep (String username, int x, int y) {
        if (!GameController.getGame().getMap().checkBounds(y, x)) return "Keep out of bounds!";
        User currentPlayer = User.getUserByUsername(username);
        if (currentPlayer == null) return "No user with the id given!";
        if (GameController.getCurrentGame().getPlayers().contains(currentPlayer)) return "User already added!";
        //todo: add color enum
        Block block = GameController.getGame().getMap().getABlock(y, x);
        String response = checkBlockType(block, GateType.KEEP);
        if (!response.equals("OK")) return response;
        currentPlayer.setGovernment(new Government(currentPlayer, ""));
        GameController.getCurrentGame().addPlayer(currentPlayer);
        GateType.KEEP.create(currentPlayer.getGovernment(), block);
        return null;
        //TODO: Current map?!
        //TODO: Separate different controllers
    }
    
    public static String createNewGame (Matcher matcher) {
        String mapName = matcher.group("mapName").trim().replaceAll("\"", "");
        if (mapName.isEmpty()) return "Empty field!";
        GameController.setCurrentGame(new Game(new GameMap(User.currentUser.getMapByName(mapName))));
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
    
    private static String checkBlockType (Block block, BuildingType buildingType) {
        if ((buildingType.equals(MakerType.HOP_FARM) || buildingType.equals(MakerType.WHEAT_FARM)) && (!block.getBlockType().equals(BlockType.GRASS) && !block.getBlockType().equals(BlockType.DENSE_MEADOW))) {
            return "You can't put farm on that ground!";
        }
        if ((buildingType.equals(MakerType.QUARRY) && !block.getBlockType().equals(BlockType.BOULDER))) {
            return "You only can put quarry on rocks!";
        }
        if (buildingType.equals(MakerType.IRON_MINE) && !block.getBlockType().equals(BlockType.IRON)) {
            return "You can only put iron mine on iron!";
        }
        if (buildingType.equals(MakerType.PITCH_RIG) && !block.getBlockType().equals(BlockType.PLAIN)) {
            return "You can only put pitch rig on plains!";
        }
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND, BlockType.GRASS, BlockType.MEADOW, BlockType.DENSE_MEADOW));
        if (!goodBlockTypes.contains(block.getBlockType())) {
            return "You can put anything on that block!";
        }
        return "OK";
    }
    
    public static String dropBuilding (GameMap map, Matcher matcher) {
        
        int x = Integer.parseInt(matcher.group("xIndex"));
        int y = Integer.parseInt(matcher.group("yIndex"));
        String type = matcher.group("type");
        
        if (!map.checkBounds(x, y)) {
            return "Index out of bound! try between 0 and 399";
        }
        if (!Dictionaries.buildingDictionary.containsKey(type)) {
            return "Invalid building name!";
        }
        Block block = map.getABlock(x, y);
        BuildingType buildingType = Dictionaries.buildingDictionary.get(type);
        if (!checkBlockType(block, buildingType).equals("OK")) {
            return checkBlockType(block, buildingType);
        }
        buildingType.create(User.currentUser.getGovernment(), block);
        return "Building created successfully!";
    }
    
    public static String dropUnit (GameMap map, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xIndex"));
        int y = Integer.parseInt(matcher.group("yIndex"));
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        if (!map.checkBounds(x, y)) {
            return "Index out of bound! try between 0 and 399";
        }
        //TODO add dictionary for troops;
        Block block = map.getABlock(x, y);
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND, BlockType.GRASS, BlockType.MEADOW, BlockType.DENSE_MEADOW));
        if (!goodBlockTypes.contains(block.getBlockType())) {
            return "You can put anything on that block!";
        }
        return "Units added successfully!";
    }
}
