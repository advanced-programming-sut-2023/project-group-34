package model.building;

import model.forces.human.Engineer;
import model.forces.human.Human;
import model.forces.human.Troop;
import model.government.Government;
import model.map.Block;

public class Inn extends Building{

    private  int numberOfWorkers = 0;
    private final int numberOfMaxWorkers = 1;
    private final int popularityRate = 0;
    private final double aleUsage = 0;
    public Inn(Government government, Block block) {
        super(government, block, 0, null, InnType.INN);
    }

    @Override
    public void process() {
        //TODO needs to know the rates
    }

    @Override
    public void destroy() {
        for (Human human : this.block.getHumans()) {
            if(!(human instanceof Troop || human instanceof Engineer)) {
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
}
