package model.building;

import model.enums.benums.BuildingType;
import model.enums.BlockType;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.forces.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Maker extends Building{
    private final ArrayList<MakeAble> output;

    private int outputRate;

    private int currentRate;
    private final int inputRate;
    private final int capacity;
    private final MakeAble input;
    private final BlockType requiredBlock;
    private final int numberOfWorkers;

    private int numberOfCurrentWorkers;

    protected Maker(Government government, Block block , ArrayList<MakeAble> output , int rate , int capacity, BlockType requiredBlock
                        , int HP, int numberOfWorkers , HashMap<Resources, Integer> cost , BuildingType buildingType, int inputRate, MakeAble input) {
        super(government, block, HP , cost, buildingType);
        this.output = output;
        this.outputRate = rate;
        this.capacity = capacity;
        this.requiredBlock = requiredBlock;
        this.numberOfWorkers = numberOfWorkers;
        this.inputRate = inputRate;
        this.input = input;
        numberOfCurrentWorkers = 0;
    }
    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public BlockType getRequiredBlock() {
        return requiredBlock;
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
    public int getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(int integrityRate) {
        integrityRate *= 5;
        this.currentRate = (outputRate * (integrityRate+100))/100;
    }

    public void setOutputRate(int outputRate) {
        this.outputRate = outputRate;
    }

    public void setNumberOfCurrentWorkers(int numberOfCurrentWorkers) {
        this.numberOfCurrentWorkers = numberOfCurrentWorkers;
    }
}
