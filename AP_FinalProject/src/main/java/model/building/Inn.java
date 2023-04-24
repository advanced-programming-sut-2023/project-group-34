package model.building;

import model.enums.benums.InnType;
import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class Inn extends Building{

    private final int numberOfWorkers = 1;
    private final int popularityRate = 0;
    private final int aleUsage = 0;
    protected Inn(Government government, Block block, int HP, HashMap<Resources, Integer> cost) {
        super(government, block, HP, cost, InnType.INN);
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

}
