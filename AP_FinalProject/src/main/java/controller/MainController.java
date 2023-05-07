package controller;

import model.Dictionaries;
import model.Game;
import model.building.*;
import model.building.DrawBridgeType;
import model.building.GeneralBuildingsType;
import model.enums.BlockFillerType;
import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.building.BuildingType;
import model.building.MakerType;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
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
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.regex.Matcher;

import static controller.MapEditingController.getCurrentGameMap;
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

    public static String changeUsername (Matcher matcher){

        if (matcher.group("username") == null || matcher.group("username").isEmpty()){
            return "The username field is empty, changing username failed";
        }

        String username = matcher.group("username");
        if (username != null)
            username = username.replaceAll("\"", "");

        if (!UserController.nameChecker(username)) return "Username's format is invalid, changing username failed";

        if (UserController.getUserByUsername(username) != null && !username.equals(User.currentUser.getName())) return "Username already exists";

        if (User.currentUser.getName().equals(username)) return "Your username is already this, changing username failed";

        User.currentUser.setName(username);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Username successfully changed";
    }

    public static String changeNickname (Matcher matcher){
        if (matcher.group("nickname") == null || matcher.group("nickname").isEmpty()){
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

    public static String changePasswordPart1(Matcher matcher){
        if (matcher.group("oldPass") == null || matcher.group("oldPass").isEmpty())
            return "The old password filed is empty, changing password failed";

        if (matcher.group("newPass") == null || matcher.group("newPass").isEmpty())
            return "The new password filed is empty, changing password failed";

        String finalOldPass = matcher.group("oldPass");
        String finalNewPass = matcher.group("newPass");

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

    public static String changePasswordPart2(String givenPassword, String confirmationPassword){
        if (!givenPassword.equals(confirmationPassword)) return "confirmation password does not match the initial password, changing password failed";

        User.currentUser.getPassword().setPasswordName(givenPassword);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Password changed successfully";
    }



    public static String changePasswordRandomly1(Matcher matcher) {

        if (matcher.group("oldPass") == null || matcher.group("oldPass").isEmpty())
            return "The required field is empty, changing password failed";

        String finalOldPass = matcher.group("oldPass");

        if (!User.currentUser.getPassword().checkPassword(finalOldPass))
            return "Incorrect current password, changing password failed";

        return "good for now";
    }

    public static String changePasswordRandomly2(String givenPass, String confirmationPass){
        if (!givenPass.equals(confirmationPass)) return "Confirmation failed";

        User.currentUser.getPassword().setPasswordName(givenPass);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Password changed successfully";
    }



    public static String changeEmail(Matcher matcher){
        if (matcher.group("email") == null || matcher.group("email").isEmpty())
            return "The required field is empty, changing email failed";

        String email = matcher.group("email");

        if (!UserController.emailChecker(email)) return "Email's format is invalid, changing email failed";

        if (UserController.isEmailAlreadyUsed(email) && !User.currentUser.getEmail().equals(email)) return "Email already exists, changing email failed";

        if (User.currentUser.getEmail().equals(email)) return "Your email is already this, changing email failed";

        User.currentUser.setEmail(email);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Email changed successfully";
    }

    public static String changeSlogan(Matcher matcher) {
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

    public static String changeSloganRandomly(Matcher matcher){
        String newSlogan =  UserController.randomSloganGenerator();
        while (newSlogan.equals(User.currentUser.getSlogan())){
            newSlogan = UserController.randomSloganGenerator();
        }

        User.currentUser.setSlogan(newSlogan);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Slogan changed successfully";
    }

    public static String removeSlogan(Matcher matcher){
        if (User.currentUser.getSlogan() == null || User.currentUser.getSlogan().isEmpty()) return "Your slogan field is already empty, removing slogan failed";

        User.currentUser.setSlogan(null);
        User.updateDataBase();
        User.stayLoggedIn();
        return "Slogan removed successfully";
    }

    public static String displaySlogan(){
        if (User.currentUser.getSlogan() == null || User.currentUser.getSlogan().isEmpty()) return "Slogan is empty!";
        else return User.currentUser.getSlogan();
    }

    public static String showProfile() {
        String theWholeProfile = "";
        theWholeProfile = theWholeProfile.concat("Username: " + User.currentUser.getName() + "\n");
        theWholeProfile = theWholeProfile.concat("Nickname: " + User.currentUser.getNickname() + "\n");
        theWholeProfile = theWholeProfile.concat("Email: " + User.currentUser.getEmail() + "\n");
        if (User.currentUser.getSlogan() != null && !User.currentUser.getSlogan().isEmpty())
            theWholeProfile = theWholeProfile.concat("Slogan: " + User.currentUser.getSlogan() + "\n");
        theWholeProfile = theWholeProfile.concat("High score: " + User.currentUser.getScore());
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
        if(buildingType.equals(DrawBridgeType.DRAW_BRIDGE)) {
            ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.SEA , BlockType.RIVER , BlockType.DITCH , BlockType.LAKE));
            if(!(goodBlockTypes.contains(block.getBlockType()))) {
                return "you can not put a draw bridge on that block!";
            }
        }
        if (buildingType.equals(GeneralBuildingsType.FOOD_STORAGE)) {
            Block tempBlock;
            boolean flag = false;
            outer:
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= j + 1; j++) {
                    if(i < 0 || j < 0 || i > 399 || j > 399) {
                        continue;
                    }
                    tempBlock = map.getABlock(i , j);
                    if(GameMap.getDistanceBetweenTwoBlocks(tempBlock , block) == 1 && tempBlock.containsThisBuilding(buildingType)) {
                        flag = true;
                        break outer;
                    }
                }
            }
            if(!flag) {
                return "you have to put a food storage near other food storages";
            }
        }
        boolean isGameStarted = User.currentUser.getGovernment() != null;
        if (isGameStarted) {
            for (Map.Entry<Resources, Integer> entry : buildingType.getCost().entrySet()) {
                if (entry.getValue() > entry.getKey().getAmount(User.currentUser.getGovernment())) {
                    return "You dont have enough " + entry.getKey().toString() + " to make this building";
                }
                entry.getKey().use(entry.getValue(), User.currentUser.getGovernment());
            }
        }
        else buildingType.create(null , block);
        block.setPassable(false);
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
    
    public static String resource (Block block) {
        BlockFillerType blockFiller = block.getBLockFiller();
        BlockType blockType = block.getBlockType();
        if (blockFiller != null) return blockFiller.toString();
        else if (blockType.equals(BlockType.IRON)) return "iron";
        else if (blockType.equals(BlockType.BOULDER)) return "stone";
        else if (blockType.equals(BlockType.OIL)) return "oil";
        else return null;
    }
    
    //TODO functions with String output go out side of model
    public static String showDetails(GameMap gameMap, int x, int y) {
        if (x >= gameMap.getSize() || y >= gameMap.getSize() || x < 0 || y < 0) return "Out of bounds!";
        Block block = gameMap.getMap()[y][x];
        StringBuilder details = new StringBuilder();
        HashMap<String, Integer> troops = block.troops();
        Set<String> troopTypes = troops.keySet();
        details.append("details:");
        details.append("\n" +
                "block type: ");
        details.append(block.getBlockType().toString());
        details.append("\n" +
                "resource: ");
        details.append(resource(block));
        details.append("\n" +
                "amount: ");
        details.append(block.getResourcesCapacity());
        details.append("\n" +
                "troops:");
        for (String troopType : troopTypes) {
            details.append("\n")
                    .append(troopType)
                    .append(": ")
                    .append(troops.get(troopType));
        }
        details.append("\n" +
                "buildings:");
        for (Building building : block.getBuilding())
        {
            details.append("\n")
                    .append(building.toString());
            //TODO: toString is gonna print a camelCase name!!!
        }
        return details.toString();
    }
}
