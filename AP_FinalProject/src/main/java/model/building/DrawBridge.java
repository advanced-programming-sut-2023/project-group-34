package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class DrawBridge extends Building{

    private final int speedBump;
    public DrawBridge(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int speedBump) {
        super(government, block, HP, cost, buildingType);
        this.speedBump = speedBump;
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }


    public int getSpeedBump() {
        return speedBump;
    }
}
