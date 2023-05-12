package model.human;

import controller.GameController;
import model.Game;
import model.building.Building;
import model.enums.TroopStage;
import model.government.Government;
import model.map.Block;
import model.map.findroute.Router;

import java.util.ArrayList;

public class Human {
    private int HP;
    private Government government;
    private Block destination;
    private Block patrolDestination;
    private final int damage;
    private final int defendRate;
    private int currentDamage;
    private boolean isVisible = true;
    private boolean canClimb;
    private final int speed;
    private boolean canDig;
    private Block block;
    private TroopStage troopStage = TroopStage.STANDING;
    private boolean isUnemployed = true;

    public Human(Block block , Government government){
        this.block = block;
        HP = 1;
        this.damage = 0;
        this.defendRate = 0;
        this.government = government;
        government.getHumans().add(this);
        this.speed = 0;
    }
    public Human (int HP , Block block , int damage , int defendRate, boolean canDig , boolean canClimb , Government government, int speed) {
        this.HP = HP;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.government = government;
        this.block = block;
        if (government != null) government.getHumans().add(this);
        this.defendRate = defendRate;
        this.speed = speed;
    }



    public void setDestination (Block destination) {
        this.destination = destination;
    }


    public void setPatrolDestination (Block patrolDestination) {
        this.patrolDestination = patrolDestination;
    }

    public void die() {
        block.getHumans().remove(this);
        government.getHumans().remove(this);
        GameController.selectedWarEquipment.remove(this);
    }
    public void getHit(int damage) {
        damage -= defendRate;
        if(damage < 0) damage = 0;
        HP -= damage;
        if(HP <= 0) die();
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


    public Boolean getCanDig() {
        return canDig;
    }


    public Block getBlock() {
        return block;
    }


    public void setBlock(Block block) {
        this.block.getHumans().remove(this);
        if(!block.getHumans().contains(this)) block.getHumans().add(this);
        this.block = block;
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
    public boolean isThereAWay(Block block)
    {
        Router router = new Router(GameController.currentGame.getMap(), this.block, block, this);
        ArrayList<Block> way = router.findBestRoute();
        if (way == null) return false;
        return way.size() <= this.speed;
    }

    public void applyMoves() {
        if(this.getSpeed() == 0) return;
        Router.moveTowardsDestination(GameController.currentGame.getMap(), destination, this);
        if (block.equals(destination)) {
            if (patrolDestination != null) {
                Block temp = destination;
                destination = patrolDestination;
                patrolDestination = temp;
            }
            else destination = null;
        }
    }

    public boolean isUnemployed() {
        return isUnemployed;
    }

    public void stop() {
        destination = null;
        patrolDestination = null;
    }

    public void setUnemployed(boolean unemployed) {
        isUnemployed = unemployed;
    }

    public TroopStage getTroopStage() {
        return troopStage;
    }

    public void setTroopStage(TroopStage troopStage) {
        this.troopStage = troopStage;
    }

    public int getRange() {
        if(this instanceof Troop troop) return troop.getFireRange();
        if(this instanceof  SiegeMachine siegeMachine) return siegeMachine.getRange();
        return 0;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isCanClimb() {
        return canClimb;
    }
}
