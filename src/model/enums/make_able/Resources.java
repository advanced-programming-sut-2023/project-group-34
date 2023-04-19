package src.model.enums.make_able;

public enum Resources implements MakeAble {
    RESOURCES_CAPACITY(0),
    GHIR(0),
    GOLD(0),
    WOOD(0),
    IRON(0),
    STONE(0);

    private int price;

    private Resources(int price){
        this.price = price;
    }

    @Override
    public void add(int rate) {

    }

    @Override
    public void use(int rate) {

    }
}
