package model.building;

import model.government.Government;
import model.map.Block;

public enum DrawBridgeType implements BuildingType{
    DRAW_BRIDGE;

    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new DrawBridge(government , block));
    }
}
