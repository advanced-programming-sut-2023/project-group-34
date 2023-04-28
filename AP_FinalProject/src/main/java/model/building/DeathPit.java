package model.building;

import model.enums.make_able.Resources;
import model.forces.SiegeMachine.SiegeMachine;
import model.forces.human.Human;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public class DeathPit extends Building {
    protected DeathPit(Government government, Block block) {
        super(government, block, 100000, new HashMap<>(Map.ofEntries(Map.entry(Resources.WOOD , 6))),  DeathPitType.DEATH_PIT);
    }

    @Override
    public void process() {
        for(Human human : this.block.getHumans()) {
            if(!human.getGovernment().equals(this.government)) {
                human.die();
            }
        }
        for (SiegeMachine siegeMachine : block.getSiegeMachines()) {
            if(!siegeMachine.getGovernment().equals(government)) {
                siegeMachine.die();
            }
        }
        destroy();
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

    public void deployTrap() {

    }

}
