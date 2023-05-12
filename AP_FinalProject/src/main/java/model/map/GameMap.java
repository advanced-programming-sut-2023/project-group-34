package model.map;

import model.building.Building;
import model.enums.BlockType;
import model.enums.Direction;
import model.human.Human;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GameMap {
    public final String name;
    private final Block[][] map;

    private final int size = 400;

    private Block UpLeftCornerBlock;

    public GameMap(String name) {
        map = new Block[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Block(i , j);
            }
        }
        this.name = name;
    }

    public GameMap(GameMap sample) {
        map = new Block[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Block(sample.getABlock(i, j));
            }
        }
        this.name = sample.name;
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
                    if (UpLeftCornerBlock.getLocationJ() != size - 41)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() + 1];
                }
                break;
            case SOUTH:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationI() != size - 41)
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
    
    public int getSize () {
        return size;
    }
    
    
    public Block[][] getMiniMap() {
         Block[][] mini = new Block[40][40];
         for (int i = UpLeftCornerBlock.getLocationI(); i < 40 + UpLeftCornerBlock.getLocationI(); i++) {
             for (int j = UpLeftCornerBlock.getLocationJ(); j < 40 + UpLeftCornerBlock.getLocationJ(); j++) {
                 mini[i - UpLeftCornerBlock.getLocationI()][j - UpLeftCornerBlock.getLocationJ()] = map[i][j];
             }
         }
         return mini;
     }

     public Block getUpLeftCornerBlock() {
         return UpLeftCornerBlock;
     }
     public static int getDistanceBetweenTwoBlocks(Block a , Block b) {
        return Math.abs(a.getLocationI() - b.getLocationI()) + Math.abs(a.getLocationJ() - b.getLocationJ());
     }

    public void setUpLeftCornerBlock(Block upLeftCornerBlock) {
        UpLeftCornerBlock = upLeftCornerBlock;
    }

    public Block getABlock(int i , int j) {
        return map[i][j];
    }
    //TODO functions with String output go out side of model
    public String showDetails(int x, int y) {
        if (x >= size || y >= size || x < 0 || y < 0) return "Out of bounds!";
        Block block = map[y][x];
        StringBuilder details = new StringBuilder();
        HashMap<String, Integer> troops = block.troops();
        Set<String> troopTypes = troops.keySet();
        details.append("details:");
        details.append("\n" +
                "block type: ");
        details.append(block.getBlockType().toString());
        details.append("\n" +
                "resource: ");
        details.append(block.resource());
        details.append("\n" + "amount: ");
        if (block.getBLockFiller() != null)
            details.append(block.getBlockFillerAmount());
        else if (block.resource() != null)
            details.append("infinity");
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
                    .append(building.getBuildingType().toString());
        }
        return details.toString();
    }

    public String setRectangleTexture(int x1, int x2, int y1, int y2, BlockType blockType) {
        if (!(checkBounds(y1, x1) && checkBounds(y2, x2))) return "Out of bounds!";
        for (; y1 <= y2; y1++){
            for (int j = x1; j <= x2; j++) {
                map[y1][j].setBlockType(blockType);
            }
        }
        return "Success!";
    }

    public String clearBlock(int i, int j) {
        if (!checkBounds(i, j)) return "Out of bounds!";
        map[i][j] = new Block(i, j);
        return "Success!";
    }

    public boolean checkBounds(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    public void setUpLeftCorner (int x, int y) {
        setUpLeftCornerBlock(map[y][x]);
    }
}
