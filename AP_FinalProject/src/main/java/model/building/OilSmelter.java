package model.building;

import model.enums.make_able.Resources;
import model.forces.human.Engineer;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class OilSmelter extends Building{
    private final int rate;
    private Engineer engineer;
    private int numberOfOils = 0;

    protected OilSmelter(Government government, Block block, int HP, HashMap<Resources, Integer> cost, int rate) {
        super(government, block, HP, cost, OilSmelterType.OIL_SMELTER);
        this.rate = rate;
    }
    @Override
    public void process() {
        numberOfOils += rate;
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
        engineer.die();
    }

    public int getRate() {
        return rate;
    }

    public void addEngineer(Engineer engineer) {
        this.engineer = engineer;
    }


}
