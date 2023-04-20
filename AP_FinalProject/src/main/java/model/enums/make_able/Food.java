package model.enums.make_able;

import model.enums.make_able.MakeAble;

public enum Food implements MakeAble {
    FOOD_CAPACITY(0, "food_capacity"),
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

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}

