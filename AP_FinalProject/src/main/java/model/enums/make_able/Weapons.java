package model.enums.make_able;

import model.enums.make_able.MakeAble;
import model.government.Government;

public enum Weapons implements MakeAble {

    LEATHER_ARMOUR(0, "leather armour"),
    MACE(0, "mace"),
    SWORD(0, "sword"),
    METAL_ARMOUR(0, "metal armour"),
    BOW(0, "bow"),
    CROSSBOW(0, "crossbow"),
    SPEAR(0, "spear"),
    PIKE(0, "pike"),
    HORSE(0, "horse");

    private int price;
    private String name;

    private Weapons(int price, String name){
        this.price = price;
        this.name = name;
    }

    @Override
    public void add(double rate , Government government) {
        double temp = getAmount(government);
        government.getStorageDepartment().getWeaponsStorage().replace(this , temp + rate);
    }

    @Override
    public void use(double rate , Government government) {
        double temp = getAmount(government);
        government.getStorageDepartment().getWeaponsStorage().replace(this , temp - rate);
    }
    @Override
    public double getLeftCapacity(Government government) {
        return government.getStorageDepartment().getWeaponsMaxCapacity() - government.getStorageDepartment().weaponsOccupied();
    }
    @Override
    public double getAmount(Government government) {
        return government.getStorageDepartment().getWeaponsStorage().get(this);
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }


}
