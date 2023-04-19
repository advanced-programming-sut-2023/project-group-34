package model.enums.make_able;

import model.enums.make_able.MakeAble;

public enum Food implements MakeAble {
    FOOD_CAPACITY(0),
    APPLE(0),
    MEAT(0),
    BREAD(0),
    ALE(0),
    CHEESE(0),
    WHEAT(0),
    HOP(0),
    FLOUR(0);

    private int price;
    private Food(int price){
        this.price = price;
    }
    @Override
    public void add(int rate) {

    }

    @Override
    public void use(int rate) {

    }
}

