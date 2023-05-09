package model.human;

import model.government.Government;
import model.map.Block;

public class SiegeMachine extends Human{
    private final SiegeType type;
    private final int speed;
    private final int price;
    private final int range;
    private final int numberOfEngineers;
    public SiegeMachine(int HP, int maxHP, Block block, int damage, boolean canDig, boolean canClimb, Government government, SiegeType type, int speed, int price, int range, int numberOfEngineers) {
        super(HP, maxHP, block, damage, canDig, canClimb, government);
        this.type = type;
        this.speed = speed;
        this.price = price;
        this.range = range;
        this.numberOfEngineers = numberOfEngineers;
    }

    public SiegeType getType() {
        return type;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPrice() {
        return price;
    }

    public int getRange() {
        return range;
    }

    public int getNumberOfEngineers() {
        return numberOfEngineers;
    }
}
