package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class CagedWarDog extends Building{
    private final int numberOfDogs = 0;
    private final int Range = 0;
    public CagedWarDog(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType) {
        super(government, block, HP, cost, buildingType);
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }
}
