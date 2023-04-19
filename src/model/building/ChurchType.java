package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public enum ChurchType implements BuildingType{
    CATHEDRAL(0 , null ,0 ),
    CHURCH(0 ,null, 0);

    private final int HP;
    private final HashMap<Resources, Integer> cost;
    private final int NumberOfWorkers;
    ChurchType(int hp, HashMap<Resources, Integer> cost, int numberOfWorkers) {
        HP = hp;
        this.cost = cost;
        NumberOfWorkers = numberOfWorkers;
    }

    public Church churchMaker(Government government , Block block , ChurchType churchType) {
        return null;
    }
}
