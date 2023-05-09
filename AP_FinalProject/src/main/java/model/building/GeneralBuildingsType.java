package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum GeneralBuildingsType implements BuildingType{
    RESOURCES_STORAGE(2000 , 2000 , new HashMap<>()),
    FOOD_STORAGE(500 , 3000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 5)))),
    ARMOUR_STORAGE(100 , 2000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 5)))),
    BARRACK(0 , 1000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 15)))),
    MERCENARY(0 , 800 , new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 10)))),
    ENGINEER_GUILD(0 ,800 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 10), Map.entry(Resources.GOLD, 100)))),
    TUNNELERS_GUILD(0 ,800 ,new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 10), Map.entry(Resources.GOLD, 50)))),
    SIEGE_TENT(0 , 200 , new HashMap<>()),
    CHURCH(0 , 2000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 250)))),
    CATHEDRAL(0 , 5000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 1000))));

    private final int capacity;
    private final int HP;
    private final HashMap<Resources, Integer> cost;

    GeneralBuildingsType(int capacity, int hp, HashMap<Resources, Integer> cost) {
        this.capacity = capacity;
        HP = hp;
        this.cost = cost;
    }

    public static GeneralBuilding storageMaker(Government government , Block block , GeneralBuildingsType storageType) {
        return null;
    }

    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new GeneralBuilding(government , block , this.HP , this.cost , this , this.capacity));
    }

    @Override
    public HashMap<Resources, Integer> getCost() {
       return this.cost;
    }
}
