package model.building;

import model.enums.benums.DrawBridgeType;
import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class DrawBridge extends Building{

    private final int speedBump;
    protected DrawBridge(Government government, Block block, int HP, HashMap<Resources, Integer> cost, int speedBump) {
        super(government, block, HP, cost, DrawBridgeType.DRAW_BRIDGE);
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
