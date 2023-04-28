package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class CagedWarDog extends Building{
    private final int numberOfDogs = 0;
    private final int Range = 0;
    protected CagedWarDog(Government government, Block block, int HP, HashMap<Resources, Integer> cost) {
        super(government, block, HP, cost, CagedWarDogType.CAGED_WAR_DOG);
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

}
