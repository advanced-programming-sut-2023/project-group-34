package model.building;

import model.enums.benums.BuildingType;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.forces.human.Engineer;
import model.forces.human.Human;
import model.forces.human.Troop;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Maker extends Building{
    private final ArrayList<MakeAble> output;

    private int outputRate;
    private final int inputRate;
    private final int capacity;
    private final MakeAble input;
    private final int numberOfWorkers;

    private int numberOfCurrentWorkers;

    public Maker(Government government, Block block, ArrayList<MakeAble> output, int outputRate, int capacity
            , int HP, int numberOfWorkers, HashMap<Resources, Integer> cost, BuildingType buildingType, int inputRate, MakeAble input) {
        super(government, block, HP , cost, buildingType);
        this.output = output;
        this.outputRate = outputRate;
        this.capacity = capacity;
        this.numberOfWorkers = numberOfWorkers;
        this.inputRate = inputRate;
        this.input = input;
        numberOfCurrentWorkers = 0;
    }
    @Override
    public void process() {
        double tempInputRate = Double.min(inputRate , input.getAmount(government));
        for(MakeAble makeAble : output) {
            makeAble.add(tempInputRate * outputRate , government);
            if(government.getStorageDepartment().foodOccupied() > government.getStorageDepartment().getFoodMaxCapacity()) {
                makeAble.use(government.getStorageDepartment().foodOccupied() - government.getStorageDepartment().getFoodMaxCapacity() , government);
            }
        }
    }

    @Override
    public void destroy() {
        for (Human human : this.block.getHumans()) {
            if(!(human instanceof Troop || human instanceof Engineer)) {
                human.die();
            }
        }
        block.getBuilding().remove(this);
    }


    public int getOutputRate() {
        return outputRate;
    }



    public ArrayList<MakeAble> getOutput() {
        return output;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public int getNumberOfCurrentWorkers() {
        return numberOfCurrentWorkers;
    }

    public void addWorker(Human human) {

    }

    public int getInputRate() {
        return inputRate;
    }

    public MakeAble getInput() {
        return input;
    }
    public void setCurrentRate(int integrityRate) {
        integrityRate *= 5;
        this.outputRate = (outputRate * (integrityRate+100))/100;
    }

    public void setOutputRate(int outputRate) {
        this.outputRate = outputRate;
    }

    public void addWorkers(int numberOfWorkers) {
        this.numberOfCurrentWorkers += numberOfWorkers;
    }
}
