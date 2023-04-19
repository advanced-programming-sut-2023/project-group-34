package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.forces.human.Engineer;
import src.model.government.Government;
import src.model.map.Block;

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
