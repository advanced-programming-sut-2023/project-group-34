package model.human;

import model.government.Government;
import model.map.Block;

public class Tunneler extends Human{
    public Tunneler(int HP, int maxHP, Block block, int damage, boolean canDig, boolean canClimb, Government government) {
        super(HP, maxHP, block, damage, canDig, canClimb, government);
    }

}
