package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public enum CagedWarDogType implements BuildingType{
    CAGED_WAR_DOG;
    private static final HashMap<Resources ,Integer> cost = new HashMap<>()

    @Override
    public void create(Government government, Block block) {

    }

    @Override
    public HashMap<Resources, Integer> getCost() {

    }
}
