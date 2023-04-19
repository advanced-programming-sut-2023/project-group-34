package src.model.forces.human;

import src.model.building.Building;
import src.model.enums.TroopStage;
import src.model.forces.WarEquipment;
import src.model.government.Government;
import src.model.map.Block;

import java.util.ArrayList;

public class Troop extends Human implements WarEquipment {
    private final static ArrayList<Troop> troops = new ArrayList<>();
    private final int defensiveRange;
    private int fireRange;
    private final int aggressiveRange;
    private final TroopType troopType;

    private TroopStage troopStage;
    private final int price;

    protected Troop(int HP, Block block, int damage, boolean canDig, boolean canClimb, Government government, int defensiveRange, int fireRange, int aggressiveRange , TroopType troopType, int price) {
        super(HP, HP , block, damage, canDig, canClimb, government);
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
        this.troopType = troopType;
        this.price = price;
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
}
