package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class GeneralBuilding extends Building{

    private final int capacity;
    protected GeneralBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int capacity) {
        super(government, block, HP, cost, buildingType);
        this.capacity = capacity;
        if (buildingType.equals(GeneralBuildingsType.FOOD_STORAGE)) {
            government.getStorageDepartment().setFoodMaxCapacity(government.getStorageDepartment().getFoodMaxCapacity() + capacity);
        } else if (buildingType.equals(GeneralBuildingsType.ARMOUR_STORAGE)) {
            government.getStorageDepartment().setWeaponsMaxCapacity(government.getStorageDepartment().getWeaponsMaxCapacity() + capacity);
        } else if (buildingType.equals(GeneralBuildingsType.RESOURCES_STORAGE)) {
            government.getStorageDepartment().setFoodMaxCapacity(government.getStorageDepartment().getResourcesMaxCapacity()+ capacity);
        }
    }

    @Override
    public void process() {
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
        if (buildingType.equals(GeneralBuildingsType.FOOD_STORAGE)) {
            government.getStorageDepartment().setFoodMaxCapacity(government.getStorageDepartment().getFoodMaxCapacity() - capacity);
        } else if (buildingType.equals(GeneralBuildingsType.ARMOUR_STORAGE)) {
            government.getStorageDepartment().setWeaponsMaxCapacity(government.getStorageDepartment().getWeaponsMaxCapacity() - capacity);
        } else if (buildingType.equals(GeneralBuildingsType.RESOURCES_STORAGE)) {
            government.getStorageDepartment().setFoodMaxCapacity(government.getStorageDepartment().getResourcesMaxCapacity() - capacity);
        }
    }

    public int getCapacity() {
        return capacity;
    }

}
