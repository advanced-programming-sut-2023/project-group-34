package model.building;

import controller.GameController;
import model.enums.make_able.Resources;
import model.government.Government;
import model.human.Human;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class Gate extends Building {
    
    private final int population;
    
    public Gate (Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int population) {
        super(government, block, HP, cost, buildingType);
        this.population = population;
        if (government != null) government.changeMaxPopulation(population);
        if (buildingType.equals(GateType.KEEP)) primaryHumanMaker();
    }
    
    public void primaryHumanMaker () {
        for (int i = 0; i < 10; i++) {
            block.getHumans().add(new Human(block, government));
        }
    }
    
    @Override
    public void process () {
    }
    
    @Override
    public void destroy () {
        government.changeMaxPopulation(-population);
        if (buildingType.equals(GateType.KEEP)) {
            GameController.currentGame.getPlayers().remove(government.getOwner());
            government.getOwner().setGovernment(null);
        }
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }
    
    
}
