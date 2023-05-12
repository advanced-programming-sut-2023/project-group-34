package model.building;

import controller.GameController;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.human.Human;
import model.government.Government;
import model.map.Block;
import model.map.GameMap;

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
        if(this.buildingType == MakerType.WOOD_CUTTER) {
            if(GameController.currentGame == null) return;
            GameMap map = GameController.currentGame.getMap();
            Block tempBlock;
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    tempBlock = map.getABlock(i , j);
                    if(tempBlock.getBLockFiller() != null) {
                        Resources.WOOD.add(Math.min(tempBlock.getBlockFillerAmount(), 20), government);
                        tempBlock.useBlockFillerAmount(20);
                        return;
                    }
                }
            }
            return;
        }
        double tempInputRate;
        if(input == null) tempInputRate = inputRate;
        else {
            tempInputRate = Double.min(inputRate , input.getAmount(government));
            input.use(tempInputRate , government);
        }
        if (output != null)
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
        ArrayList<Human> humans = block.getHumans();
        for (int i = humans.size() - 1; i >= 0; i--) {
            Human human = humans.get(i);
            if (!human.isUnemployed()) human.die();
        }
    }





    public ArrayList<MakeAble> getOutput() {
        return output;
    }


    public int getNumberOfMaxWorkers() {
        return numberOfMaxWorkers;
    }

    public int getNumberOfCurrentWorkers() {
        return numberOfCurrentWorkers;
    }

    public void addWorker() {
        this.numberOfCurrentWorkers++;
    }


    public void setCurrentOutputRate(int integrityRate) {
        integrityRate *= 5;
        this.currentOutPutRate = (outputRate * (integrityRate+100))/100;
    }


    public double getCurrentAmount() {
        return currentAmount;
    }

    public void use(double amount) {
        this.currentAmount -= amount;
    }
}
