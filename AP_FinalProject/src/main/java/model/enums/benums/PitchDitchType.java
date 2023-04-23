package model.enums.benums;

import model.government.Government;
import model.map.Block;

public enum PitchDitchType implements BuildingType{
    PITCH_DITCH;
    @Override
    public void creator(Government government, Block block) {

    }
}
