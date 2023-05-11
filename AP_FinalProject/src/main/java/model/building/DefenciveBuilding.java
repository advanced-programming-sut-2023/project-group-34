package model.building;

import controller.GameController;
import model.enums.Direction;
import model.enums.make_able.Resources;
import model.government.Government;
import model.human.Human;
import model.human.SiegeMachine;
import model.human.Troop;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DefenciveBuilding extends Building{

    private final int fireRange;
    private final int defendRange;
    private final ArrayList<Human> warEquipments = new ArrayList<>();
    private final int capacity;
    private boolean hasLadder = false;
    private int occupation;

    private final HashMap<Direction, Boolean> isPassableFromThisDirection = new HashMap<>(Map.ofEntries(Map.entry(Direction.EAST , false) , Map.entry(Direction.NORTH , false) , Map.entry(Direction.SOUTH , false) , Map.entry(Direction.WEST , false)));
    protected DefenciveBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int fireRange, int defendRange, int capacity) {
        super(government, block, HP, cost, buildingType);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.capacity = capacity;
    }

    @Override
    public void process() {
        for(Human human : warEquipments) {
            if(!(human instanceof Troop troop) || !(((Troop)human).getFireRange() > 1)) {
                continue;
            }
            //TODO check for correction of x and y
            int x = block.getLocationI();
            int y = block.getLocationJ();
            for (int i = x - troop.getFireRange(); i < x + troop.getFireRange(); i++) {
                for (int j = y - troop.getFireRange(); j < y + troop.getFireRange(); j++) {
                    if(i < 0 || j < 0 || i > 399 || j > 399) {
                        continue;
                    }
                    for(Human human1 : GameController.getGame().getMap().getABlock(i , j).getHumans()) {
                        //TODO add defence strengh
                        human1.getHit(troop.getCurrentDamage());
                    }
                }
            }
        }
    }
    @Override
    public void destroy() {
        for (Human human : this.warEquipments) {
            human.die();
        }
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }
    public boolean isHasLadder () {
        return hasLadder;
    }

    public void setHasLadder (boolean hasLadder) {
        this.hasLadder = hasLadder;
    }
    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public int getCapacity() {
        return capacity;
    }

    public static boolean canConquerMachineClimb(DefenciveBuilding building) {
        return false;
    }

    public HashMap<Direction, Boolean> getIsPassableFromThisDirection() {
        return isPassableFromThisDirection;
    }

    public void addHuman(Human troop) {
        this.warEquipments.add(troop);
        troop.setVisible(false);

        if(troop instanceof Troop troop1 && troop1.getFireRange() > 1) troop1.addRange(this.fireRange);
    }

    public void removeEquipment( Troop warEquipment) {
        this.warEquipments.add(warEquipment);
        warEquipment.setVisible(false);
        warEquipment.addRange(-this.fireRange);

    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public boolean isHasLadder() {
        return hasLadder;
    }

    public void setHasLadder(boolean hasLadder) {
        this.hasLadder = hasLadder;
    }
}
