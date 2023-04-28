package model.building;

import model.enums.make_able.Resources;
import model.forces.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class DrawBridge extends Building{

    private final int speedBump = 0;
    private boolean isUP = false;
    protected DrawBridge(Government government, Block block) {
        super(government, block, 500 , new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 10))), DrawBridgeType.DRAW_BRIDGE);
    }

    @Override
    public void process() {
        for(Human human : block.getHumans()) {
            if(human.getGovernment() != government && isUP) {
                //TODO decrease speed
            }
        }
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }


    public int getSpeedBump() {
        return speedBump;
    }

    public boolean isUP() {
        return isUP;
    }

    public void setUP(boolean UP) {
        isUP = UP;
    }
}
