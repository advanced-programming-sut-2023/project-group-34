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
    private boolean isVertical;
    private final ArrayList<Human> warEquipments = new ArrayList<>();
    public Gate(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int population, boolean isVertical) {
        super(government, block, HP, cost, buildingType);
        this.population = population;
        this.isVertical = isVertical;
        if (government != null)
            government.changeMaxPopulation(population);
        if(buildingType.equals(GateType.KEEP)) primaryHumanMaker();
    }

    public void primaryHumanMaker() {
        for (int i = 0; i < 10; i++) {
            block.getHumans().add(new Human(block , government));
        }
    }
    @Override
    public void process() {
    }

    @Override
    public void destroy() {
        government.changeMaxPopulation(-population);
        if(buildingType.equals(GateType.KEEP)) {
            if(GameController.currentGame.getPlayers().contains(government.getOwner()))
                GameController.currentGame.getPlayers().remove(government.getOwner());
            government.getOwner().setGovernment(null);
        }
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
