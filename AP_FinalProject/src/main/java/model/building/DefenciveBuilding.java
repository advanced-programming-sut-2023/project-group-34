package model.building;

import model.enums.Direction;
import model.enums.make_able.Resources;
import model.forces.WarEquipment;
import model.forces.human.Engineer;
import model.forces.human.Human;
import model.forces.human.Troop;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DefenciveBuilding extends Building{

    private final int fireRange;
    private final int defendRange;
    private final ArrayList<WarEquipment> warEquipments = new ArrayList<>();
    private final int capacity;
    private final HashMap<Direction, Boolean> isPassableFromThisDirection = new HashMap<>(Map.ofEntries(Map.entry(Direction.EAST , false) , Map.entry(Direction.NORTH , false) , Map.entry(Direction.SOUTH , false) , Map.entry(Direction.WEST , false)));

    protected DefenciveBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int fireRange, int defendRange, int capacity) {
        super(government, block, HP, cost, buildingType);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.capacity = capacity;
    }
    @Override
    public void process() {
        //TODO people on the tower attack
    }

    @Override
    public void destroy() {
        for (WarEquipment warEquipment : this.warEquipments) {
            warEquipment.die();
        }
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

    public ArrayList<WarEquipment> getWarEquipments() {
        return warEquipments;
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
    public void addAWayToHashMap() {

    }

    public static boolean canConquerMachineClimb(DefenciveBuilding building) {
        return false;
    }

    public HashMap<Direction, Boolean> getIsPassableFromThisDirection() {
        return isPassableFromThisDirection;
    }

    public void addWarEquipment(WarEquipment warEquipment) {
        this.warEquipments.add(warEquipment);
        warEquipment.setVisible(false);
        warEquipment.addRange(this.fireRange);
    }

    public void removeEquipment(WarEquipment warEquipment) {
        this.warEquipments.add(warEquipment);
        warEquipment.setVisible(false);
        warEquipment.addRange(this.fireRange);

    }
}
