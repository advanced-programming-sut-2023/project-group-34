package model.building;

import model.enums.benums.BuildingType;
import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public abstract class Building {
    private final Government government;
    private final Block block;
    private final HashMap<Resources, Integer> cost;
    private final BuildingType buildingType;
    private int HP;

    public Building(Government government, Block block, int HP , HashMap<Resources, Integer> cost, BuildingType buildingType) {
        this.government = government;
        this.block = block;
        this.cost = cost;
        this.buildingType = buildingType;
        this.HP = HP;
    }


    public Government getGovernment() {
        return government;
    }

    public Block getBlock() {
        return block;
    }

    public abstract void process();
    public abstract void destroy();

    public HashMap<Resources, Integer> getCost() {
        return cost;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
