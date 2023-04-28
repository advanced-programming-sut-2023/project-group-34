package model.building;

import model.enums.make_able.Resources;
import model.forces.human.TroopType;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class CagedWarDog extends Building{
    protected CagedWarDog(Government government, Block block) {
        super(government, block, 100, new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 10) , Map.entry(Resources.GOLD , 100))), CagedWarDogType.CAGED_WAR_DOG);
    }

    @Override
    public void process() {
        int numberOfDogs = 4;
        for (int i = 0; i < numberOfDogs; i++) {
            TroopType.WAR_DOG.Maker(block , government);
        }
        destroy();
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

}
