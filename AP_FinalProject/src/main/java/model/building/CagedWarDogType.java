package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum CagedWarDogType implements BuildingType{
    CAGED_WAR_DOG;
    private static final HashMap<Resources ,Integer> cost = new HashMap<>()

    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new CagedWarDog(government , block));
    }

    @Override
    public HashMap<Resources, Integer> getCost() {
        return new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 10) , Map.entry(Resources.GOLD , 100)));
    }
}
