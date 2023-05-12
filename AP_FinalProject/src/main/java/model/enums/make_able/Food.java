package model.enums.make_able;

import model.government.Government;

public enum Food implements MakeAble {
    APPLE(10, "apple"),
    MEAT(10, "meat"),
    BREAD(10, "bread"),
    ALE(5, "ale"),
    CHEESE(10, "cheese"),
    WHEAT(2, "wheat"),
    HOP(2, "hop"),
    FLOUR(5, "flour");

    private final int price;
    private final String name;
    Food(int price, String name){
        this.price = price;
        this.name = name;
    }
    @Override
    public void add(double rate , Government government) {
        double temp = getAmount(government);
        government.getStorageDepartment().getFoodStorage().replace(this , temp + rate);
    }

    @Override
    public void use(double rate , Government government) {
        double temp = getAmount(government);
        if(temp - rate < 0) government.getStorageDepartment().getFoodStorage().replace(this , 0.0);
        government.getStorageDepartment().getFoodStorage().replace(this , temp - rate);
    }
    @Override
    public double getLeftCapacity(Government government) {
        return government.getStorageDepartment().getFoodMaxCapacity() - government.getStorageDepartment().foodOccupied();
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

