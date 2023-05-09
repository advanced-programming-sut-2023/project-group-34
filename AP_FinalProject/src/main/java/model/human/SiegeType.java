package model.human;

import model.government.Government;
import model.map.Block;

public enum SiegeType {
    BATTERING_RAM(-1 , 50 , 2000, 0,1000, 50),
    SIEGE_TOWER(-1 , 20 , -1, 0,1000, 50),
    CATAPULT(20 , 5 , 100, 0,1000, 50),
    STABLE_CATAPULT(20 , 0 , 500,0,1000, 50),
    FIRE_XBOW(30 , 8 , 500 , 0,1000, 50);



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
        block.getHumans().add(new SiegeMachine(HP , HP , block , damage , defendRate,false , false , government , this, speed, price, range));
        block.getHumans().add(new SiegeMachine(HP , HP , block , damage , false , false , government , this, speed, price, range, numberOfEngineer));
    }

    public int getPrice() {
        return price;
    }
    public int getNumberOfEngineer() {
        return numberOfEngineer;
    }
}
