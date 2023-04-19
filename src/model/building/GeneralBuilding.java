package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public class GeneralBuilding extends Building{

    private final int capacity;
    private final int numberOfResidents;
    protected GeneralBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int capacity, int numberOfResidents) {
        super(government, block, HP, cost, buildingType);
        this.capacity = capacity;
        this.numberOfResidents = numberOfResidents;
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public int getCapacity() {
        return capacity;
    }

}
