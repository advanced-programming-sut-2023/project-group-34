package model.forces.human;

import model.forces.WarEquipment;
import model.government.Government;
import model.map.Block;

public class Tunneler extends Human implements WarEquipment {
    public Tunneler(int HP, int maxHP, Block block, int damage, boolean canDig, boolean canClimb, Government government) {
        super(HP, maxHP, block, damage, canDig, canClimb, government);
    }

    @Override
    public int getFireRange() {
        return 0;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public void fight(WarEquipment opponent) {

    }

    @Override
    public void addRange(int amount) {

    }
}
