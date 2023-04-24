package model.building;

import model.enums.benums.OilSmelterType;
import model.enums.make_able.Resources;
import model.forces.human.Engineer;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class OilSmelter extends Building{
    private final int rate;

    protected OilSmelter(Government government, Block block, int HP, HashMap<Resources, Integer> cost, int rate) {
        super(government, block, HP, cost, OilSmelterType.OIL_SMELTER);
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
