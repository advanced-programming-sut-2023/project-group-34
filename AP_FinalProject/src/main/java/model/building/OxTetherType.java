package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum OxTetherType implements BuildingType {
    OX_TETHER;
    
    @Override
    public void create (Government government, Block block) {
        block.addBuilding(new OxTether(government, block, 100, new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD, 5)))));
    }
    
    @Override
    public HashMap<Resources, Integer> getCost () {
        return new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD, 5)));
    }
}
