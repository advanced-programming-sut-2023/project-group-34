package model.building;

import model.enums.make_able.Food;
import model.forces.WarEquipment;
import model.forces.human.Engineer;
import model.forces.human.Human;
import model.forces.human.Troop;
import model.government.Government;
import model.map.Block;

public class Inn extends Building{

    private  int numberOfWorkers = 0;
    private final int numberOfMaxWorkers = 1;
    private final int popularityRate = 2;
    private final double aleUsage = 2;
    public Inn(Government government, Block block) {
        super(government, block, 0, null, InnType.INN);
    }

    @Override
    public void process() {
        double inputRate = Double.min(aleUsage , Food.ALE.getLeftCapacity(government));
        Food.ALE.use(inputRate ,government);
        government.setTotalPopularity((int) (government.getTotalPopularity() + inputRate * popularityRate));
    }

    @Override
    public void destroy() {
        for (Human human : this.block.getHumans()) {
            if(!(human instanceof WarEquipment)) {
                human.die();
            }
        }
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
