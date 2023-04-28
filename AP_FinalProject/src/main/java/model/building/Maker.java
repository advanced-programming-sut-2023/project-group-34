package model.building;

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

    private double outputRate;  //مقدار تولید شده به ازای هر واحد ورودی
    private final double inputRate;
    private final double capacity;
    private double currentAmount = 0;
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
        if(this.buildingType == MakerType.QUARRY) {
            currentAmount += outputRate;
            currentAmount = Math.min(currentAmount, capacity);
            return;
        }
        double tempInputRate;
        if(input == null) tempInputRate = inputRate;
        else {
            tempInputRate = Double.min(inputRate , input.getAmount(government));
            input.use(tempInputRate , government);
        }
        for(MakeAble makeAble : output) {
            makeAble.add(tempInputRate * outputRate , government);
            if(makeAble.getLeftCapacity(government) < 0) {
                makeAble.use(-makeAble.getLeftCapacity(government), government);
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
        government.getBuildings().remove(this);
    }


    public double getOutputRate() {
        return outputRate;
    }



    public ArrayList<MakeAble> getOutput() {
        return output;
    }

    public double getCapacity() {
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

    public double getInputRate() {
        return inputRate;
    }

    public MakeAble getInput() {
        return input;
    }
    public void setCurrentOutputRate(int integrityRate) {
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
