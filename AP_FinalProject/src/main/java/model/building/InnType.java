package model.building;

import model.government.Government;
import model.map.Block;

public enum InnType implements BuildingType{
    INN;
    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new Inn(government ,block));
    }
}
