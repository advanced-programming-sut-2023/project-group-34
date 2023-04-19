package model.building;

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
    private final int rate;
    private final int inputRate;
    private final int capacity;
    private final MakeAble input;
    private final BlockType requiredBlock;
    private final int numberOfWorkers;
    private int numberOfCurrentWorkers =0;

    protected Maker(Government government, Block block , ArrayList<MakeAble> output , int rate , int capacity, BlockType requiredBlock
                        , int HP, int numberOfWorkers , HashMap<Resources, Integer> cost , BuildingType buildingType, int inputRate, MakeAble input) {
        super(government, block, HP , cost, buildingType);
        this.output = output;
        this.rate = rate;
        this.capacity = capacity;
        this.requiredBlock = requiredBlock;
        this.numberOfWorkers = numberOfWorkers;
        this.inputRate = inputRate;
        this.input = input;
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



    public int getRate() {
        return rate;
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
}
