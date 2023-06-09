package model.enums.benums;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

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

    @Override
    public void create(Government government, Block block) {

    }
}
