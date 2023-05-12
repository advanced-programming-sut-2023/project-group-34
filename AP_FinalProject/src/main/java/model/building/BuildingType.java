package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public interface BuildingType {
    void create (Government government, Block block);
    
    HashMap<Resources, Integer> getCost ();
}
