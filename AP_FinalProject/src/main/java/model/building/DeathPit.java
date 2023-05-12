package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.human.Human;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class DeathPit extends Building {
    protected DeathPit(Government government, Block block) {
        super(government, block, 100000, new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 6))),  DeathPitType.DEATH_PIT);
    }

    @Override
    public void process() {
        boolean humanFoundFlag = false;
        int size  = block.getHumans().size();
        for (int i = size - 1; i >= 0; i--) {
            Human human;
            human = block.getHumans().get(i);
            if (!human.getGovernment().equals(government)) {
                humanFoundFlag = true;
                human.getHit(50000);
            }
        }
        if(humanFoundFlag) destroy();
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }


}
