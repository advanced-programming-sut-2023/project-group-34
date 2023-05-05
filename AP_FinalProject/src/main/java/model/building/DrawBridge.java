package model.building;

import model.enums.make_able.Resources;
import model.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class DrawBridge extends Building{

    private boolean isUP = false;
    protected DrawBridge(Government government, Block block) {
        super(government, block, 500 , new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 10))), DrawBridgeType.DRAW_BRIDGE);
    }

    @Override
    public void process() {
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }
    public boolean isUP() {
        return isUP;
    }

    public void setUP(boolean UP) {
        isUP = UP;
    }
}
