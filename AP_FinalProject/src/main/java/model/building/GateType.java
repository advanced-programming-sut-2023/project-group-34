package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum GateType implements BuildingType{
    SMALL_GATE_HOUSE(8 , 10000 , new HashMap<>()),
    BIG_GATE_HOUSE(10 ,  20000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 20)))),
    KEEP(20, 10000, new HashMap<>()),
    HOUSE(8, 500, new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD, 6))));

    private final int population;
    private final int HP;
    private final HashMap<Resources , Integer> cost;

    GateType(int population, int hp, HashMap<Resources, Integer> cost) {
        this.population = population;
        HP = hp;
        this.cost = cost;
    }
    public void create(Government government , Block block) {
        block.addBuilding(new Gate(government , block , HP , cost , this , population , false));
    }

    @Override
    public HashMap<Resources, Integer> getCost() {
       return this.cost;
    }

}
