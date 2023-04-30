package model.forces.human;

import model.forces.WarEquipment;
import model.government.Government;
import model.map.Block;

public class Engineer extends Human implements WarEquipment {
    private final int price;
    private boolean isEquippedWithOil = false;
    public Engineer(Block block, Government government, int price) {
        super(0, 0 , block, 0, false, true , government);
        this.price = price;
    }
    public void equipWithOil() {

    }

    public boolean isEquippedWithOil() {
        return isEquippedWithOil;
    }

    public void setEquippedWithOil(boolean equippedWithOil) {
        isEquippedWithOil = equippedWithOil;
    }

    public int getPrice() {
        return price;
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
