package model.building;

import model.enums.make_able.Resources;
import model.forces.human.Engineer;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class OilSmelter extends Building{
    private final int rate;
    private int numberOfEngineers = 0;

    protected OilSmelter(Government government, Block block, int HP, HashMap<Resources, Integer> cost, int rate) {
        super(government, block, HP, cost, OilSmelterType.OIL_SMELTER);
        this.rate = rate;
    }
    @Override
    public void process() {
        for(Building building : government.getBuildings()) {
            if(building.getBuildingType().equals(MakerType.QUARRY)) {
                (Maker) building.ge
            }
        }
    }

    @Override
    public void destroy() {

    }

    public int getRate() {
        return rate;
    }

    public void giveOilToEngineer(Engineer engineer) {
        if(numberOfEngineers == 0) {
            numberOfEngineers++;
            return;
        }
    }

}
