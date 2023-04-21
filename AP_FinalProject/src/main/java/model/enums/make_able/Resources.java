package model.enums.make_able;

public enum Resources implements MakeAble {
    GHIR(0, "ghir"),
    GOLD(0, "gold"),
    WOOD(0, "wood"),
    IRON(0, "iron"),
    STONE(0, "stone");

    private int price;
    private String name;

    private Resources(int price, String name){
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
