package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.human.Human;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Gate extends Building {

    private final int population;
    private boolean isVertical;
    private final ArrayList<Human> warEquipments = new ArrayList<>();
    public Gate(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int population, boolean isVertical) {
        super(government, block, HP, cost, buildingType);
        this.population = population;
        this.isVertical = isVertical;
        if (government != null)
            government.changeMaxPopulation(population);
    }


    @Override
    public void process() {
    }

    @Override
    public void destroy() {
        government.changeMaxPopulation(-population);
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

    public boolean isVertical() {
        return isVertical;
    }

    public int getPopulation() {
        return population;
    }
    public void setIsAVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }
}
