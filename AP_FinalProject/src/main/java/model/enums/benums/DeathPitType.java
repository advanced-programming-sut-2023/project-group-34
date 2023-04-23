package model.enums.benums;

import model.government.Government;
import model.map.Block;

public enum DeathPitType implements BuildingType{
    DEATH_PIT;

    @Override
    public void creator(Government government, Block block) {

    }
}
