package model.enums.benums;

import model.government.Government;
import model.map.Block;

public interface BuildingType {
    void creator(Government government , Block block);
}
