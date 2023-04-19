package model.forces.human;

import model.government.Government;
import model.map.Block;

public class Engineer extends Human{
    private final int price;
    public Engineer(Block block, Government government, int price) {
        super(0, 0 , block, 0, false, true , government);
        this.price = price;
    }
    private boolean isEquippedWithOil = false;
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
}
