package model.building;

import model.enums.make_able.Resources;
import model.human.Engineer;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class OilSmelter extends Building {
    private final int rate;
    private Engineer engineer;
    private int numberOfOils = 20;
    
    protected OilSmelter (Government government, Block block, int HP, HashMap<Resources, Integer> cost, int rate) {
        super(government, block, HP, cost, OilSmelterType.OIL_SMELTER);
        this.rate = rate;
    }
    
    @Override
    public void process () {
        if(engineer != null) return;
        numberOfOils += rate;
    }
    
    @Override
    public void destroy () {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
        engineer.die();
    }
    
    
    public Engineer getEngineer () {
        return engineer;
    }
    
    public void setEngineer (Engineer engineer) {
        this.engineer = engineer;
    }
    
    public void giveOil () {
        numberOfOils--;
    }
    
    public int getNumberOfOils () {
        return numberOfOils;
    }
}
