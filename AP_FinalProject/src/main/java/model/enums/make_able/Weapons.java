package model.enums.make_able;

import model.enums.make_able.MakeAble;

public enum Weapons implements MakeAble {
    WEAPONS_CAPACITY(0, "weapons_capacity"),
    LEATHER_ARMOUR(0, "leather armour"),
    MACE(0, "mace"),
    SWORD(0, "sword"),
    METAL_ARMOUR(0, "metal armour"),
    BOW(0, "bow"),
    CROSSBOW(0, "crossbow"),
    SPEAR(0, "spear"),
    PIKE(0, "pike");

    private int price;
    private String name;

    private Weapons(int price, String name){
        this.price = price;
        this.name = name;
    }

    @Override
    public void add(int rate) {

    }

    @Override
    public void use(int rate) {

    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }


}
