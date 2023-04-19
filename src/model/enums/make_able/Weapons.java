package src.model.enums.make_able;

public enum Weapons implements MakeAble {
    WEAPONS_CAPACITY(0),
    LEATHER_ARMOUR(0),
    MACE(0),
    SWORD(0),
    METAL_ARMOUR(0),
    BOW(0),
    CROSSBOW(0),
    SPEAR(0),
    PIKE(0);

    private int price;

    private Weapons(int price){
        this.price = price;
    }

    @Override
    public void add(int rate) {

    }

    @Override
    public void use(int rate) {

    }
}
