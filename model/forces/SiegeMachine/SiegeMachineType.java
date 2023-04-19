package model.forces.SiegeMachine;

import model.government.Government;
import model.map.Block;

public enum SiegeMachineType {
    BATTERING_RAM(0 , 0 , null , 0 , 0 , 0),
    SIEGE_TOWER(0 , 0 , null , 0 , 0 , 0),

    CATAPULT(0 , 0 , null , 0 , 0 , 0),
    STONE_THROWER(0 , 0 , null , 0 , 0 , 0),
    FIXED_CATAPULT(0 , 0 , null , 0 , 0 , 0);
    private final int numberOfRequiredEngineers;
    private final int speed;
    private final OpponentTypes opponentType;
    private final int range;
    private final int damage;
    private final int HP;

    SiegeMachineType(int numberOfRequiredEngineers, int speed, OpponentTypes opponentType, int range, int damage, int hp) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.opponentType = opponentType;
        this.range = range;
        this.damage = damage;
        HP = hp;
    }
    public static SiegeMachine conquerMachineMaker(Block block , Government government , SiegeMachineType siegeMachineType) {
        return null;
    }
}
