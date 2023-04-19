package controller;

import model.map.GameMap;
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
        return null;
    }

    public static String changeNickname (Matcher matcher){
        return null;
    }

    public static String changePassword(Matcher matcher){
        return null;
    }

    public static String changeEmail(){
        return null;
    }

    public static String changeSlogan(Matcher matcher){
        return null;
    }

    public static String removeSlogan(Matcher matcher){
        return null;
    }

    public static String displayScore(){ return null; }

    public static String displayRank(){ return null; }

    public static String displaySlogan(){ return null;}

    public static String showProfile(Matcher matcher){
        return null;
    }

    public static String changeBlockFloorType(Matcher matcher){
        return null;
    }

    public static String changeMultipleBlockFloorType(Matcher matcher){
        return null;
    }

    public static String clearBlock(Matcher matcher){
        return null;
    }

    public static String dropRock(Matcher matcher){
        return null;
    }

    public static String dropTree(Matcher matcher){
        return null;
    }

    public static String dropBuilding(Matcher matcher){
        return null;
    }

    public static String dropUnit(Matcher matcher){
        return null;
    }

}
