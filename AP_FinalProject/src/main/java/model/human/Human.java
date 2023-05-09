package model.human;

import model.building.Building;
import model.enums.Direction;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;

public class Human {
    private final int maxHP;
    private int HP;
    private Government government;
    private ArrayList<Direction> rout = new ArrayList<>();
    private final int damage;
    private final int defendRate;
    private int currentDamage;
    private boolean isVisible = true;
    private boolean canClimb;
    private boolean canDig;
    private Block block;
    private boolean isUnemployed = true;
    public Human(Block block , Government government){
        this.block = block;
        maxHP = 1;
        HP = 1;
        this.damage = 0;
        this.defendRate = 0;
        this.government = government;
        government.getHumans().add(this);
    }
    public Human (int HP , int maxHP , Block block , int damage , int defendRate, boolean canDig , boolean canClimb , Government government) {
        this.maxHP = maxHP;
        this.HP = HP;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.government = government;
        this.block = block;
        government.getHumans().add(this);
        this.defendRate = defendRate;
    }

    public void die() {

    }
    public void move(int x , int y) {

    }
    public void getHit(int damage) {
        HP -= damage;
        if(HP < 0) die();
    }
    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
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

    public int getDefendRate() {
        return defendRate;
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

    public void setCurrentRate(int moralityRate) {
        moralityRate *= 5;
        this.currentDamage = (damage * (moralityRate+100))/100;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public boolean isThereAWay(Block block) {
        return true;
    }

    public boolean isUnemployed() {
        return isUnemployed;
    }

    public void setUnemployed(boolean unemployed) {
        isUnemployed = unemployed;
    }
}
