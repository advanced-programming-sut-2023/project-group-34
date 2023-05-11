package model.enums.make_able;

import model.enums.make_able.MakeAble;
import model.government.Government;

public enum Weapons implements MakeAble {

    LEATHER_ARMOUR(10, "leather armour"),
    MACE(15, "mace"),
    SWORD(20, "sword"),
    METAL_ARMOUR(30, "metal armour"),
    BOW(10, "bow"),
    CROSSBOW(15, "crossbow"),
    SPEAR(10, "spear"),
    PIKE(20, "pike"),
    HORSE(40, "horse");

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
