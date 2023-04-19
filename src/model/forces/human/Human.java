package src.model.forces.human;

import src.model.building.Building;
import src.model.enums.Direction;
import src.model.government.Government;
import src.model.map.Block;

import java.util.ArrayList;

public class Human {
    private final int maxHP;
    private static final ArrayList<Human> humans = new ArrayList<>();
    private int HP;
    private Government government;
    private ArrayList<Direction> rout = new ArrayList<>();
    private int damage;
    private boolean canClimb;
    private boolean canDig;
    private Block block;
    private boolean isUnemployed;
    public Human(Block block , Government government){
        this.block = block;
        maxHP = 1;
        HP = 1;
        this.damage = 0;
        this.government = government;
    }
    public Human (int HP , int maxHP , Block block , int damage , boolean canDig , boolean canClimb , Government government) {
        this.maxHP = maxHP;
        this.HP = HP;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.government = government;
        this.block = block;
    }

    public void die() {

    }
    public void move(int x , int y) {

    }
    public void decreaseHP(int damage) {

    }
    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public ArrayList<Direction> getRout() {
        return rout;
    }

    public void setRout(ArrayList<Direction> rout) {
        this.rout = rout;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Boolean getCanClimb() {
        return canClimb;
    }

    public void setCanClimb(Boolean canClimb) {
        this.canClimb = canClimb;
    }

    public Boolean getCanDig() {
        return canDig;
    }

    public void setCanDig(Boolean canDig) {
        this.canDig = canDig;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
    public void makeWorker(Building building) {
    }

    public void makeTroop(Building building) {
    }
    public void dismissWorker() {
    }
}
