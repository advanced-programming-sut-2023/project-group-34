package model.enums.benums;

import model.government.Government;
import model.map.Block;

public enum OxTetherType implements BuildingType{
    OX_TETHER;

    @Override
    public void creator(Government government, Block block) {
    }
}
