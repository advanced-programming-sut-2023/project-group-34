package model.human;

import controller.GameController;
import model.building.Building;
import model.enums.TroopStage;
import model.government.Government;
import model.map.Block;
import model.map.findroute.Router;

import java.util.ArrayList;

public class Human {
    private final int maxHP;
    private int HP;
    private Government government;
    private ArrayList<Block> route = new ArrayList<>();
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
        maxHP = 1;
        HP = 1;
        this.damage = 0;
        this.defendRate = 0;
        this.government = government;
        government.getHumans().add(this);
        this.speed = 0;
    }
    public Human (int HP , int maxHP , Block block , int damage , int defendRate, boolean canDig , boolean canClimb , Government government, int speed) {
        this.maxHP = maxHP;
        this.HP = HP;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.government = government;
        this.block = block;
        government.getHumans().add(this);
        this.defendRate = defendRate;
        this.speed = speed;
    }

    public void die() {

    }
    public void move(int x , int y) {

    }
    public void getHit(int damage) {
        damage -= defendRate;
        if(damage < 0) damage = 0;
        HP -= damage;
        if(HP <= 0) die();
    }

    public int getSpeed () {
        return speed;
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

    public ArrayList<Block> getRoute () {
        return route;
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

    public void setRoute (ArrayList<Block> route) {
        this.route = route;
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
    public boolean isThereAWay(Block block)
    {
        Router router = new Router(GameController.currentGame.getMap(), this.block, block, (Troop) this);
        ArrayList<Block> way = router.findBestRoute();
        return way.size() <= this.speed;
    }

    public void applyMoves() {
        if (speed >= getRoute().size()) {
            this.setBlock(getRoute().get(getRoute().size() - 1));
            setRoute(new ArrayList<>());
        }
        else {
            this.setBlock(getRoute().get(speed - 1));
            for (int i = 0; i < speed; i++) {
                getRoute().remove(0);
            }
        }
    }

    public boolean isUnemployed() {
        return isUnemployed;
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

    public void automaticAttack() {
    }
}
