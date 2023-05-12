package model.enums.make_able;

import model.government.Government;

public interface MakeAble {
    void add (double rate, Government government);
    
    void use (double rate, Government government);
    
    double getLeftCapacity (Government government);
    
    double getAmount (Government government);
}
