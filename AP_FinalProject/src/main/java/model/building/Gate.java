package model.building;

import model.enums.benums.BuildingType;
import model.enums.make_able.Resources;
import model.forces.WarEquipment;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Gate extends Building {

    private final int population;
    private final boolean isVertical;
    private final int capacity;
    private final ArrayList<WarEquipment> warEquipments = new ArrayList<>();
    public Gate(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int population, boolean isVertical, int capacity) {
        super(government, block, HP, cost, buildingType);
        this.population = population;
        this.isVertical = isVertical;
        this.capacity = capacity;
    }


    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public boolean isVertical() {
        return isVertical;
    }

    public int getPopulation() {
        return population;
    }

    public int getCapacity() {
        return capacity;
    }
}
