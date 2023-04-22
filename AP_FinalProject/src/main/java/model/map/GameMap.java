package model.map;

import model.enums.Direction;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GameMap {
    private final Block[][] map;

    private final int maxSize;

    private Block UpLeftCornerBlock;

    GameMap(int size) {
        map = new Block[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                map[i][j] = new Block(i , j);
            }
        }
        maxSize = size;
    }
    public Block[][] getMap() {
        return map;
    }
    public ArrayList<Direction> findRoute(Block beginning , Block destination) {
        //TODO
         return null;
     }
     public void moveMiniMap(Direction direction , int number) {
        switch (direction) {
            case NORTH:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationI() != 0)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI() - 1][UpLeftCornerBlock.getLocationJ()];
                }
                break;
            case EAST:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationJ() != maxSize - 11)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() + 1];
                }
                break;
            case SOUTH:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationI() != maxSize - 11)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI() + 1][UpLeftCornerBlock.getLocationJ()];
                }
                break;
            case WEST:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationJ() != 0)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() - 1];
                }
                break;
            case NORTH_EAST:
                moveMiniMap(Direction.NORTH, number);
                moveMiniMap(Direction.EAST, number);
                break;
            case NORTH_WEST:
                moveMiniMap(Direction.NORTH, number);
                moveMiniMap(Direction.WEST, number);
                break;
            case SOUTH_EAST:
                moveMiniMap(Direction.SOUTH, number);
                moveMiniMap(Direction.EAST, number);
                break;
            case SOUTH_WEST:
                moveMiniMap(Direction.SOUTH, number);
                moveMiniMap(Direction.WEST, number);
                break;
        }
     }

     public void getMiniMap() {
         Block[][] mini = new Block[10][10];
         for (int i = UpLeftCornerBlock.getLocationI(); i < 10 + UpLeftCornerBlock.getLocationI(); i++) {
             for (int j = UpLeftCornerBlock.getLocationJ(); j < 10 + UpLeftCornerBlock.getLocationJ(); j++) {
                 mini[i - UpLeftCornerBlock.getLocationI()][j - UpLeftCornerBlock.getLocationJ()] = map[i][j];
             }
         }
     }

     public Block getUpLeftCornerBlock() {
         return UpLeftCornerBlock;
     }

    public void setUpLeftCornerBlock(Block upLeftCornerBlock) {
        UpLeftCornerBlock = upLeftCornerBlock;
    }

    public String showDetails(int x, int y) {
        Block block = map[y][x];
        StringBuilder details = new StringBuilder();
        HashMap<String, Integer> troops = block.troops();
        Set<String> troopTypes = troops.keySet();
        details.append("details:");
        details.append("\n");
        details.append(block.getBlockType().toString());
        for (String troopType : troopTypes) {
            details.append("\n")
                    .append(troopType)
                    .append(": ")
                    .append(troops.get(troopType));
        }
        return details.toString();
    }
}
