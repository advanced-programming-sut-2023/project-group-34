package model.map.findroute;

import model.map.Block;

public class Node {
    private final Node parent;
    private final Block currentBlock;
    private final int h;
    private final int g;
    private final int f;
    private final Block destination;
    
    public Node (Node parent, Block currentBlock, Block destination) {
        this.parent = parent;
        this.currentBlock = currentBlock;
        if (parent != null) g = parent.getG() + 1;
        else g = 0;
        h = manhattanDistance(currentBlock, destination);
        f = h + g;
        this.destination = destination;
    }
    private int manhattanDistance (Block start, Block end) {
        return Math.abs(start.getLocationI() - end.getLocationI()) +
                Math.abs(start.getLocationJ() - end.getLocationJ());
    }
    
    public Node getParent () {
        return parent;
    }
    
    public Block getCurrentBlock () {
        return currentBlock;
    }
    
    public int getH () {
        return h;
    }
    
    public int getG () {
        return g;
    }
    
    public int getF () {
        return f;
    }
    
    public Block getDestination () {
        return destination;
    }
}
