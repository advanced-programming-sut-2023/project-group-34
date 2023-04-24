package model.enums.make_able;

import model.government.Government;

public interface MakeAble {
    void add(int rate);
    void use(int rate);

    double getLeftCapacity(Government government);

    double getAmount(Government government);
}
