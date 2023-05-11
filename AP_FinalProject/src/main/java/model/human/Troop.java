package model.human;

import model.building.Building;
import model.enums.TroopStage;
import model.enums.make_able.MakeAble;
import model.government.Government;
import model.map.Block;

import java.util.*;

public class Troop extends Human {
    private final static ArrayList<Troop> troops = new ArrayList<>();
    private final int defensiveRange;
    private int fireRange;
    private final int aggressiveRange;
    private final TroopType troopType;
    private TroopStage troopStage;
    private final HashMap<MakeAble , Integer> cost;
    protected Troop(int HP, Block block, int damage, int defendRate, boolean canDig, boolean canClimb, Government government, int defensiveRange, int fireRange, int aggressiveRange , TroopType troopType, int speed, HashMap<MakeAble, Integer> cost) {
        super(HP, HP , block, damage, defendRate, canDig, canClimb, government , speed);
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
        this.troopType = troopType;
        this.cost = cost;
        if(troopType == TroopType.ASSASSIN) setVisible(false);
    }

    public Troop enemyLocator(){
        return null;
    }

    public void addRange(int amount) {
        fireRange += amount;
    }

    public int getFireRange() {
        return fireRange;
    }
    public int getAggressiveRange() {
        return aggressiveRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefensiveRange() {
        return defensiveRange;
    }

    @Override
    public void move(int x ,  int y) {

    }

    public void dismissTroop() {

    }

    public static void createUnit(Building humanMaker , int number , TroopType troopType) {

    }

    public TroopType getTroopType() {
        return troopType;
    }

    public TroopStage getTroopStage() {
        return troopStage;
    }

    public void setTroopStage(TroopStage troopStage) {
        this.troopStage = troopStage;
    }

    public HashMap<MakeAble, Integer> getCost() {
        return cost;
    }
}
