package model.forces.human;

import model.building.Building;
import model.enums.TroopStage;
import model.enums.make_able.Weapons;
import model.forces.WarEquipment;
import model.government.Government;
import model.map.Block;

import java.util.*;

public class Troop extends Human implements WarEquipment {
    private final static ArrayList<Troop> troops = new ArrayList<>();
    private int defensiveRange;
    private int fireRange;
    private final int aggressiveRange;
    private final TroopType troopType;

    private boolean isVisible = true;

    private TroopStage troopStage;
    private final int price;
    protected Troop(int HP, Block block, int damage, boolean canDig, boolean canClimb, Government government, int defensiveRange, int fireRange, int aggressiveRange , TroopType troopType, int price) {
        super(HP, HP , block, damage, canDig, canClimb, government);
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
        this.troopType = troopType;
        this.price = price;
        if(troopType == TroopType.ASSASSIN) isVisible = false;
    }

    public Troop enemyLocator(){
        return null;
    }


    public int getAggressiveRange() {
        return aggressiveRange;
    }

    @Override
    public int getFireRange() {
        return fireRange;
    }

    @Override
    public void fight(WarEquipment opponent) {
        //TODO first attack conquer machines
    }

    @Override
    public void addRange(int amount) {
        this.fireRange += amount;
        this.defensiveRange += amount;
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
        //TODO
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

    public int getPrice() {
        return price;
    }


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
