package model.human;

import model.building.Building;
import model.building.OilSmelter;
import model.government.Government;
import model.map.Block;

public class Engineer extends Human{
    private final int price;
    private boolean isEquippedWithOil = false;
    public Engineer(Block block, Government government, int price) {
        super(0, 0 , block, 0, 0, false, true , government);
        this.price = price;
        useOil();
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
    public void setVisible(boolean visible) {

    }

    public void useOil() {
        boolean flag = false;
        for(Building building : getGovernment().getBuildings()) {
            if(!(building instanceof OilSmelter oilSmelter)) {
                continue;
            }
            flag = true;
            if(oilSmelter.getNumberOfOils() < 1) {
                continue;
            }
            oilSmelter.giveOil();
            isEquippedWithOil = true;
        }
        if(!flag) isEquippedWithOil = false;
    }

}
