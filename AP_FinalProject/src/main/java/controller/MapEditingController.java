package controller;

import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.map.GameMap;

import java.util.regex.Matcher;

public class MapEditingController {
    private static GameMap currentGameMap;
    
    public static GameMap getCurrentGameMap () {
        return currentGameMap;
    }
    
    public static void setCurrentGameMap (GameMap currentGameMap) {
        MapEditingController.currentGameMap = currentGameMap;
    }
    
    public static String changeBlockFloorType (Matcher matcher) {
        BlockType blockType = BlockType.stringToBlockType(matcher.group("type"));
        if (blockType == null) return "Invalid texture!";
        String x = matcher.group("singleX");
        if (x == null) {
            int x1 = Integer.parseInt(matcher.group("x1"));
            int x2 = Integer.parseInt(matcher.group("x2"));
            int y1 = Integer.parseInt(matcher.group("y1"));
            int y2 = Integer.parseInt(matcher.group("y2"));
            return getCurrentGameMap().setRectangleTexture(x1, x2, y1, y2, blockType);
        } else {
            int x1 = Integer.parseInt(x);
            int y1 = Integer.parseInt(matcher.group("singleY"));
            return getCurrentGameMap().setRectangleTexture(x1, x1, y1, y1, blockType);
        }
    }
    
    public static String clearBlock (Matcher matcher) {
        int i = Integer.parseInt(matcher.group("yAxis"));
        int j = Integer.parseInt(matcher.group("xAxis"));
        return getCurrentGameMap().clearBlock(i, j);
    }
    
    public static String dropRock (Matcher matcher) {
        String direction = matcher.group("direction");
        int y = Integer.parseInt(matcher.group("yAxis"));
        int x = Integer.parseInt(matcher.group("xAxis"));
        return switch (direction) {
            case "north" -> getCurrentGameMap().setRectangleTexture(x, x, y, y, BlockType.NORTH_ROCK);
            case "south" -> getCurrentGameMap().setRectangleTexture(x, x, y, y, BlockType.SOUTH_ROCK);
            case "west" -> getCurrentGameMap().setRectangleTexture(x, x, y, y, BlockType.WEST_ROCK);
            case "east" -> getCurrentGameMap().setRectangleTexture(x, x, y, y, BlockType.EAST_ROCK);
            case "random" ->
                    getCurrentGameMap().setRectangleTexture(x, x, y, y, BlockType.values()[Runner.getRandomNumber(4)]);
            default -> "Invalid direction!";
        };
    }
    
    public static String dropTree (Matcher matcher) {
        BlockFillerType blockFillerType = BlockFillerType.stringToType(matcher.group("type"));
        if (blockFillerType == null) return "Invalid type!";
        int i = Integer.parseInt(matcher.group("yIndex"));
        int j = Integer.parseInt(matcher.group("xIndex"));
        if (!getCurrentGameMap().checkBounds(i, j)) return "Out of bounds!";
        if (!(getCurrentGameMap().getMap()[i][j].getBlockType().equals(BlockType.GRASS) || getCurrentGameMap().getMap()[i][j].getBlockType().equals(BlockType.MEADOW) || getCurrentGameMap().getMap()[i][j].getBlockType().equals(BlockType.DENSE_MEADOW) || getCurrentGameMap().getMap()[i][j].getBlockType().equals(BlockType.GROUND)))
            return "Can't put a tree here!";
        getCurrentGameMap().getMap()[i][j].setBLockFiller(blockFillerType);
        return "Success!";
    }
    
    public static void resetCurrentMap () {
        setCurrentGameMap(null);
    }
}
