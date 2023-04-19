package model.building;

import model.enums.make_able.Resources;
import model.forces.human.Engineer;
import model.forces.human.Troop;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class OilSmelter extends Building{
    private final int rate;

    public OilSmelter(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int rate) {
        super(government, block, HP, cost, buildingType);
        this.rate = rate;
    }
    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public int getRate() {
        return rate;
    }

    public void giveOilToEngineer(Engineer engineer) {

    }
}
