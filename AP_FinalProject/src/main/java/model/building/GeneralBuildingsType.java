package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public enum GeneralBuildingsType implements BuildingType{
    RESOURCES_STORAGE(0 , 0 , null),
    FOOD_STORAGE(0 , 0 , null),
    ARMOUR_STORAGE(0 , 0 , null),
    BARRACK(0 , 0 , null),
    MERCENARY(0 , 0 , null),
    ENGINEER_GUILD(0 ,0 , null),
    TUNNELERS_GUILD(0 ,0 ,null),
    SIEGE_TENT(0 , 0 , null),
    CHURCH(0 , 0 , null),
    CATHEDRAL(0 , 0 , null);

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
