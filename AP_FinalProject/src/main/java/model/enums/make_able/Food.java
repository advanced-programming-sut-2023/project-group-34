package model.enums.make_able;

import model.government.Government;

public enum Food implements MakeAble {
    APPLE(0, "apple"),
    MEAT(0, "meat"),
    BREAD(0, "bread"),
    ALE(0, "ale"),
    CHEESE(0, "cheese"),
    WHEAT(0, "wheat"),
    HOP(0, "hop"),
    FLOUR(0, "flour");

    private int price;
    private String name;
    private Food(int price, String name){
        this.price = price;
        this.name = name;
    }
    @Override
    public void add(int rate) {

    }

    @Override
    public void use(int rate) {

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

