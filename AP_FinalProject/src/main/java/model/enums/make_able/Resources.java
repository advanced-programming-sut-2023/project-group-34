package model.enums.make_able;

import model.government.Government;

public enum Resources implements MakeAble {
    GHIR(5, "ghir"),
    GOLD(0, "gold"),
    WOOD(5, "wood"),
    IRON(5, "iron"),
    STONE(5, "stone");

    private final int price;
    private final String name;

    Resources(int price, String name){
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
        if(temp - rate < 0) government.getStorageDepartment().getResourcesStorage().replace(this , 0.0);
        government.getStorageDepartment().getResourcesStorage().replace(this , temp - rate);
    }
    @Override
    public double getLeftCapacity(Government government) {
        return government.getStorageDepartment().getResourcesMaxCapacity() - government.getStorageDepartment().resourcesOccupied();
    }
    @Override
    public double getAmount(Government government) {
        return government.getStorageDepartment().getResourcesStorage().get(this);
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
