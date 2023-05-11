package model.human;

import model.government.Government;
import model.map.Block;

public class Tunneler extends Human{
    public Tunneler(Block block, Government government) {
        super(50 ,50 , block, 300000, 10 , false, false , government , 6);
    }

}
