package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public enum GateType implements BuildingType{
    SMALL_GATE_HOUSE(0 , false, 0 , null , 0),
    BIG_GATE_HOUSE(0 , false , 0 , null , 0);
    private final int population;
    private final boolean isVertical;
    private final int HP;
    private final HashMap<Resources , Integer> cost;
    private final int capacity;

    GateType(int population, boolean isVertical, int hp, HashMap<Resources, Integer> cost, int capacity) {
        this.population = population;
        this.isVertical = isVertical;
        HP = hp;
        this.cost = cost;
        this.capacity = capacity;
    }
    public Gate makeGate(Government government , Block block , GateType gateType) {
        return null;
    }
}
