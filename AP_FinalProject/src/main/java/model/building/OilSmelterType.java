package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum OilSmelterType implements BuildingType {
    OIL_SMELTER;
    
    @Override
    public void create (Government government, Block block) {
        block.addBuilding(new OilSmelter(government, block, 500,
                new HashMap<>(Map.ofEntries(Map.entry(Resources.IRON, 10),
                        Map.entry(Resources.GOLD, 100))), 4));
    }
    
    @Override
    public HashMap<Resources, Integer> getCost () {
        return new HashMap<>(Map.ofEntries(Map.entry(Resources.IRON, 10)));
    }
}
