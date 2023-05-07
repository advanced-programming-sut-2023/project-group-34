package model.map;

import model.enums.Direction;
import model.human.Human;

import java.util.ArrayList;

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
                    if (UpLeftCornerBlock.getLocationJ() != size - 11)
                        UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() + 1];
                }
                break;
            case SOUTH:
                for (int i = 0; i < number; i++) {
                    if (UpLeftCornerBlock.getLocationI() != size - 11)
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

     public Block[][] getMiniMap() {
         Block[][] mini = new Block[10][10];
         for (int i = UpLeftCornerBlock.getLocationI(); i < 10 + UpLeftCornerBlock.getLocationI(); i++) {
             for (int j = UpLeftCornerBlock.getLocationJ(); j < 10 + UpLeftCornerBlock.getLocationJ(); j++) {
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
    
    public String getName () {
        return name;
    }
    
    public int getSize () {
        return size;
    }
    
    public boolean checkBounds(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    public void setUpLeftCorner (int x, int y) {
        setUpLeftCornerBlock(map[y][x]);
    }
}
