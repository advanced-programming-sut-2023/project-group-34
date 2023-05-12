package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum DeathPitType implements BuildingType {
    DEATH_PIT;
    
    @Override
    public void create (Government government, Block block) {
        block.addBuilding(new DeathPit(government, block));
    }
    
    @Override
    public HashMap<Resources, Integer> getCost () {
        return new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD, 6)));
    }
}
