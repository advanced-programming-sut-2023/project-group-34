package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public enum DefenciveBuildingType implements BuildingType{
    LOOKOUT_TOWER(0 , 0 , 0 , 0 ,null),
    PERIMETER_TOWER(0 , 0,  0,  0  , null),
    DEFENCIVE_TURRET(0 , 0 , 0 , 0 , null),
    SQUARE_TOWER(0 , 0 , 0 , 0 , null),
    CIRCLE_TOWER(0 , 0, 0 , 0 , null),
    LOW_WALL(0 , 0 , 0 , 0 , null),
    HIGH_WALL(0 , 0 , 0 , 0 , null);

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

    public static DefenciveBuilding siegeEquipmentCreator(Government government , Block block , DefenciveBuildingType siegeEquipmentType) {
        return null;
    }
}
