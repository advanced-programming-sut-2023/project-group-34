package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public enum GeneralBuildingsType implements BuildingType{
    RESOURCES_STORAGE(0 , 0 , null, 0),
    FOOD_STORAGE(0 , 0 , null, 0),
    ARMOUR_STORAGE(0 , 0 , null , 0),
    KEEP(0 ,0 , null , 0),
    HOUSE(0 , 0 , null , 0),
    SHOP(0 , 0, null , 0),
    BARRACK(0 , 0 , null , 0),
    MERCENARY(0 , 0 , null , 0),
    ENGINEER_GUILD(0 ,0 , null , 0),
    TUNNELERS_GUILD(0 ,0 ,null , 0),
    SIEGE_TENT(0 , 0 , null , 0),
    CHURCH(0 , 0 , null , 0),
    CATHEDRAL(0 , 0 , null , 0);

    private final int capacity;
    private final int HP;
    private final HashMap<Resources, Integer> cost;
    private final int numberOfResidents;

    GeneralBuildingsType(int capacity, int hp, HashMap<Resources, Integer> cost, int numberOfResidents) {
        this.capacity = capacity;
        HP = hp;
        this.cost = cost;
        this.numberOfResidents = numberOfResidents;
    }

    public static GeneralBuilding storageMaker(Government government , Block block , GeneralBuildingsType storageType) {
        return null;
    }

    @Override
    public void create(Government government, Block block) {
        block.addBuilding(new GeneralBuilding(government , block , this.HP , this.cost , this , this.capacity , this.numberOfResidents));
    }
}
