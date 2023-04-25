package model.enums.make_able;

import model.government.Government;

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
    public void add(double rate , Government government) {
        double temp = getAmount(government);
        government.getStorageDepartment().getResourcesStorage().replace(this , temp + rate);
    }

    @Override
    public void use(double rate , Government government) {
        double temp = getAmount(government);
        government.getStorageDepartment().getResourcesStorage().replace(this , temp - rate);
    }
    @Override
    public double getLeftCapacity(Government government) {
        return government.getStorageDepartment().getResourcesMaxCapacity() - government.getStorageDepartment().resourcesOccupied();
    }
    @Override
    public double getAmount(Government government) {
        return government.getStorageDepartment().getFoodStorage().get(this);
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
