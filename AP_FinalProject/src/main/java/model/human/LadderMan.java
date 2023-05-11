package model.human;

import model.government.Government;
import model.map.Block;

public class LadderMan extends Human{
    public LadderMan(Block block,Government government) {
        super(50, 50, block, 0, 10 , false, false , government);
    }

}
