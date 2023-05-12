package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.human.Human;
import model.human.Troop;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class DefenciveBuilding extends Building{

    private final int fireRange;
    private final int damage;
    private final ArrayList<Human> warEquipments = new ArrayList<>();
    private final int capacity;
    private boolean hasLadder = false;
    private int occupation;

       protected DefenciveBuilding(Government government, Block block, int HP, HashMap<Resources, Integer> cost, BuildingType buildingType, int fireRange, int damage, int capacity) {
        super(government, block, HP, cost, buildingType);
        this.fireRange = fireRange;
        this.damage = damage;
        this.capacity = capacity;
    }

    @Override
    public void process() {
    }
    @Override
    public void destroy() {
        ArrayList<Human> equipments = this.warEquipments;
        for (int i = equipments.size() - 1; i >= 0; i--) {
            Human human = equipments.get(i);
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

    public int getDamage() {
        return damage;
    }

    public int getCapacity() {
        return capacity;
    }

    public static boolean canConquerMachineClimb(DefenciveBuilding building) {
        return false;
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

}
