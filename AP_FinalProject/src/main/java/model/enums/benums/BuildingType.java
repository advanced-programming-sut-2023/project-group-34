package model.enums.benums;

import model.government.Government;
import model.map.Block;

public interface BuildingType {
    void create(Government government , Block block);
}
