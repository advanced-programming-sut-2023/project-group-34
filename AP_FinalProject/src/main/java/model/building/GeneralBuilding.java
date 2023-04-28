package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class GeneralBuilding extends Building{

    private final int capacity;
    private final int numberOfResidents;
    protected GeneralBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int capacity, int numberOfResidents) {
        super(government, block, HP, cost, buildingType);
        this.capacity = capacity;
        this.numberOfResidents = numberOfResidents;
        //TODO add storage
    }

    @Override
    public void process() {
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
        //TODO remove storage
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public int getCapacity() {
        return capacity;
    }

}
