package src.model.building;

import src.model.enums.make_able.Resources;
import src.model.government.Government;
import src.model.map.Block;

import java.util.HashMap;

public class Inn extends Building{

    private final int numberOfWorkers = 1;
    private final int popularityRate = 0;
    private final int aleUsage = 0;
    public Inn(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType) {
        super(government, block, HP, cost, buildingType);
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

}
