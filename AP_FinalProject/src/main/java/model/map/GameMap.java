package model.map;

import com.google.gson.Gson;
import model.enums.Direction;

public class GameMap {
    public final String name;
    private final Block[][] map;

    private final int size = 400;
    public final int minimapSize = 30;

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

     public void moveMiniMap(Direction direction , int number) {
         switch (direction) {
             case NORTH -> {
                 for (int i = 0; i < number; i++) {
                     if (UpLeftCornerBlock.getLocationI() != 0)
                         UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI() - 1][UpLeftCornerBlock.getLocationJ()];
                 }
             }
             case EAST -> {
                 for (int i = 0; i < number; i++) {
                     if (UpLeftCornerBlock.getLocationJ() != size - minimapSize - 1)
                         UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() + 1];
                 }
             }
             case SOUTH -> {
                 for (int i = 0; i < number; i++) {
                     if (UpLeftCornerBlock.getLocationI() != size - minimapSize - 1)
                         UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI() + 1][UpLeftCornerBlock.getLocationJ()];
                 }
             }
             case WEST -> {
                 for (int i = 0; i < number; i++) {
                     if (UpLeftCornerBlock.getLocationJ() != 0)
                         UpLeftCornerBlock = map[UpLeftCornerBlock.getLocationI()][UpLeftCornerBlock.getLocationJ() - 1];
                 }
             }
             case NORTH_EAST -> {
                 moveMiniMap(Direction.NORTH, number);
                 moveMiniMap(Direction.EAST, number);
             }
             case NORTH_WEST -> {
                 moveMiniMap(Direction.NORTH, number);
                 moveMiniMap(Direction.WEST, number);
             }
             case SOUTH_EAST -> {
                 moveMiniMap(Direction.SOUTH, number);
                 moveMiniMap(Direction.EAST, number);
             }
             case SOUTH_WEST -> {
                 moveMiniMap(Direction.SOUTH, number);
                 moveMiniMap(Direction.WEST, number);
             }
         }
     }
    
    public int getSize () {
        return size;
    }
    
    
    public Block[][] getMiniMap() {
         Block[][] mini = new Block[minimapSize][minimapSize];
         for (int i = UpLeftCornerBlock.getLocationI(); i < minimapSize + UpLeftCornerBlock.getLocationI(); i++) {
             for (int j = UpLeftCornerBlock.getLocationJ(); j < minimapSize + UpLeftCornerBlock.getLocationJ(); j++) {
                 mini[i - UpLeftCornerBlock.getLocationI()][j - UpLeftCornerBlock.getLocationJ()] = map[i][j];
             }
         }
         return mini;
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
    
    public boolean checkBounds(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    public void setUpLeftCorner (int x, int y) {
        setUpLeftCornerBlock(map[y][x]);
    }
    
    
    public static GameMap jsonToGameMap (String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameMap.class);
    }
    
    public String toJson () {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
