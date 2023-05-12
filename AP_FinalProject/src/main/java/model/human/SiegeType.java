package model.human;

import model.government.Government;
import model.map.Block;

public enum SiegeType {
    BATTERING_RAM(0 , 50 , 50000, 10,1000, 50 , 4),
    SIEGE_TOWER(0 , 20 , 0, 10,1000, 50 , 4),
    CATAPULT(20 , 5 , 100, 10,1000, 50 , 2),
    STABLE_CATAPULT(20 , 0 , 500,10,1000, 50 , 3),
    FIRE_XBOW(30 , 8 , 500 , 10,1000, 50 , 2);



    final int range;
    final int speed;
    final int damage;
    final int defendRate;
    final int HP;
    final int price;
    final int numberOfEngineer;
    SiegeType(int range, int speed, int damage, int defendRate, int hp, int price , int numberOfEngineer) {
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        HP = hp;
        this.price = price;
        this.defendRate = defendRate;
        this.numberOfEngineer = numberOfEngineer;
    }
    public void creator(Block block , Government government) {
        block.getHumans().add(new SiegeMachine(HP , block , damage , defendRate,false , false , government , this, speed, range , numberOfEngineer));
    }

    public int getPrice() {
        return price;
    }
    public int getNumberOfEngineer() {
        return numberOfEngineer;
    }
}
