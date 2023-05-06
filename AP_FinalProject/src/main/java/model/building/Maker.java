package model.building;

import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Maker extends Building{
    private final ArrayList<MakeAble> output;

    private final double outputRate;
    private  double currentOutPutRate;
    private final double inputRate;
    private final double capacity;

    private double currentAmount = 0;

    private final MakeAble input;
    private final int numberOfMaxWorkers;
    private int numberOfCurrentWorkers;
    public Maker(Government government, Block block, ArrayList<MakeAble> output, int outputRate, int capacity
            , int HP, int numberOfMaxWorkers, HashMap<Resources, Integer> cost, BuildingType buildingType, int inputRate, MakeAble input) {
        super(government, block, HP , cost, buildingType);
        this.output = output;
        this.outputRate = outputRate;
        this.capacity = capacity;
        this.numberOfMaxWorkers = numberOfMaxWorkers;
        this.inputRate = inputRate;
        this.input = input;
        numberOfCurrentWorkers = 0;
        currentOutPutRate = outputRate;
    }

    @Override
    public void process() {
        if(this.buildingType == MakerType.QUARRY) {
            currentAmount += currentOutPutRate;
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
            makeAble.add(Math.floor((tempInputRate * currentOutPutRate) / inputRate), government);
            if(makeAble.getLeftCapacity(government) < 0) {
                makeAble.use(-makeAble.getLeftCapacity(government) , government);
            }
        }
    }
    @Override
    public void destroy() {
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

    public int getNumberOfMaxWorkers() {
        return numberOfMaxWorkers;
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
        this.currentOutPutRate = (outputRate * (integrityRate+100))/100;
    }

    public void addWorkers(int numberOfWorkers) {
        this.numberOfCurrentWorkers += numberOfWorkers;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void use(double amount) {
        this.currentAmount -= amount;
    }
}
