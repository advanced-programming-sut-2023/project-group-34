package model.human;

import model.government.Government;
import model.map.Block;

public class SiegeMachine extends Human{
    private final SiegeType type;
    private final int range;
    private final int numberOfEngineers;
    public SiegeMachine(int HP, Block block, int damage , int defendRate, boolean canDig, boolean canClimb, Government government, SiegeType type, int speed, int range, int numberOfEngineers) {
        super(HP, block, damage , defendRate , canDig, canClimb, government , speed);
        this.type = type;
        this.range = range;
        this.numberOfEngineers = numberOfEngineers;
    }

    public SiegeType getType() {
        return type;
    }



    public int getRange() {
        return range;
    }

    public int getNumberOfEngineers() {
        return numberOfEngineers;
    }
}
