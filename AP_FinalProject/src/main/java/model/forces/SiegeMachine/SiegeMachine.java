package model.forces.SiegeMachine;

import model.forces.WarEquipment;
import model.government.Government;
import model.map.Block;

public class SiegeMachine implements WarEquipment {
    private final int numberOfRequiredEngineers;
    private final int speed;
    private Block block;
    private final OpponentTypes opponentType;

    private int range;

    private final int damage;
    private final Government government;
    private final int HP;
    protected SiegeMachine(int numberOfRequiredEngineers, int speed, Block block, OpponentTypes opponentType, int range, int damage, Government government, int hp) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.block = block;
        this.opponentType = opponentType;
        this.range = range;
        this.damage = damage;
        this.government = government;
        HP = hp;
    }

    @Override
    public int getFireRange() {
        return 0;
    }
    @Override
    public void fight(WarEquipment opponent) {
       //TODO first attack to conquer machines
    }

    public int getNumberOfRequiredEngineers() {
        return numberOfRequiredEngineers;
    }

    public int getSpeed() {
        return speed;
    }

    public void move() {

    }
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public OpponentTypes getOpponentType() {
        return opponentType;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getHP() {
        return HP;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Government getGovernment() {
        return government;
    }
}
