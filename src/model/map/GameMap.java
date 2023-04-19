package src.model.map;

import src.model.enums.Direction;

import java.util.ArrayList;

public class GameMap {
    private final Block[][] map;

    private Block UpLeftCornerBlock;

    GameMap(int size) {
        map = new Block[size + 1][size + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                map[i][j] = new Block(i , j);
            }
        }

    }
    public Block[][] getMap() {
        return map;
    }
    public ArrayList<Direction> findRoute(Block beginning , Block destination) {
        //TODO
         return null;
     }
     public void moveMiniMap(Direction direction , int number) {
        //TODO
     }

     public void getMiniMap() {

     }

     public Block getUpLeftCornerBlock() {
         return UpLeftCornerBlock;
     }

    public void setUpLeftCornerBlock(Block upLeftCornerBlock) {
        UpLeftCornerBlock = upLeftCornerBlock;
    }
}
