package model.building;

import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class Inn extends Building{

    private  int numberOfWorkers = 0;
    private final int numberOfMaxWorkers = 1;
    private final int popularityRate = 2;
    private final double aleUsage = 2;
    public Inn(Government government, Block block) {
        super(government, block, 1000, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 100) , Map.entry(Resources.WOOD , 20))), InnType.INN);
    }

    @Override
    public void process() {
        double inputRate = Double.min(aleUsage , Food.ALE.getLeftCapacity(government));
        Food.ALE.use(inputRate ,government);
        government.setTotalPopularity((int) (government.getTotalPopularity() + inputRate * popularityRate));
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void addWorkers(int number) {
        this.numberOfWorkers += number;
    }

    public double getAleUsage() {
        return aleUsage;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public int getNumberOfMaxWorkers() {
        return numberOfMaxWorkers;
    }
}
