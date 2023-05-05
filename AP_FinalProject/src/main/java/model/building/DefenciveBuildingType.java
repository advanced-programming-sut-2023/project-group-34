package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum DefenciveBuildingType implements BuildingType{
    LOOKOUT_TOWER( 4, 4 , 5 , 2500 ,new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 10)))),
    PERIMETER_TOWER(2 , 2,  15,  10000  , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 10)))),
    DEFENCIVE_TURRET(6 , 6 , 20 , 12000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 15)))),
    SQUARE_TOWER(8 , 8 , 35 , 16000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 35)))),
    CIRCLE_TOWER(10 , 10, 40 , 20000 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 40)))),
    LOW_WALL(2 , 2 , 5 , 400 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 1)))),
    HIGH_WALL(4 , 4 , 5 , 600 , new HashMap<>(Map.ofEntries(Map.entry(Resources.STONE , 2))));

    private final int fireRange;
    private final int defendRange;
    private final int warEquipmentCapacity;
    private final int HP;
    private final HashMap<Resources, Integer> cost;

    DefenciveBuildingType(int fireRange, int defendRange, int warEquipmentCapacity, int hp, HashMap<Resources, Integer> cost) {
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.warEquipmentCapacity = warEquipmentCapacity;
        HP = hp;
        this.cost = cost;
    }

    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new DefenciveBuilding(government , block , HP , cost , this , fireRange , defendRange , warEquipmentCapacity));
    }

    @Override
    public HashMap<Resources, Integer> getCost() {
        return this.cost;
    }
}
