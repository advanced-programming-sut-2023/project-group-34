package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.forces.human.Human;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public class Church extends Building{

    private final int numberOfWorkers;
    protected Church(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int trainingCost, int numberOfWorkers) {
        super(government, block, HP, cost, buildingType);
        this.numberOfWorkers = numberOfWorkers;
    }


    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public void addPriest(Human human) {

    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }
}
