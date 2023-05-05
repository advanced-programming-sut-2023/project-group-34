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
    private final int speed;
    private TroopStage troopStage;
    private final HashMap<MakeAble , Integer> cost;
    protected Troop(int HP, Block block, int damage, boolean canDig, boolean canClimb, Government government, int defensiveRange, int fireRange, int aggressiveRange , TroopType troopType, int price, int speed, HashMap<MakeAble, Integer> cost) {
        super(HP, HP , block, damage, canDig, canClimb, government);
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
        this.troopType = troopType;
        this.speed = speed;
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


    public static HashMap<TroopType, String> troopsNameString = new HashMap<>(
            Map.ofEntries(
                    Map.entry(TroopType.ARCHER, "archer"),
                    Map.entry(TroopType.CROSS_BOW_MAN, "crossbow man"),
                    Map.entry(TroopType.SPEAR_MAN, "spear man"),
                    Map.entry(TroopType.PIKE_MAN, "pike man"),
                    Map.entry(TroopType.MACE_MAN, "mace man"),
                    Map.entry(TroopType.SWORDS_MAN, "swords man"),
                    Map.entry(TroopType.KNIGHT, "knight"),
                    Map.entry(TroopType.TUNNELER, "tunneler"),
                    Map.entry(TroopType.LADDER_MAN, "ladder man"),
                    Map.entry(TroopType.BLACK_MONK, "black monk"),
                    Map.entry(TroopType.ARCHER_BOW, "archer bow"),
                    Map.entry(TroopType.SLAVE, "slave"),
                    Map.entry(TroopType.SLINGER, "slinger"),
                    Map.entry(TroopType.ASSASSIN, "assassin"),
                    Map.entry(TroopType.HORSE_ARCHER, "horse archer"),
                    Map.entry(TroopType.ARAB_SWORD_MAN, "arab swords man"),
                    Map.entry(TroopType.FIRE_THROWERS, "fire thrower")
            )
    );

    public HashMap<MakeAble, Integer> getCost() {
        return cost;
    }

    public int getSpeed() {
        return speed;
    }
}
