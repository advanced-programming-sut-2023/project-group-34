package model.human;

import controller.GameController;
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
    public void automaticAttack() {
        int counter = 0;
        int x = getBlock().getLocationI();
        int y = getBlock().getLocationJ();
        Block tempBlock;
        int numberOfOpponent;
        int range;
        int tempDamage;
        switch (getTroopStage()) {
            case AGGRESSIVE -> {
                range = aggressiveRange;
                tempDamage = getCurrentDamage();
                numberOfOpponent = 5;
            }
            case DEFENSIVE -> {
                numberOfOpponent = 3;
                range = defensiveRange;
                tempDamage = (getCurrentDamage() * 8) / 10;
            }
            default -> {
                numberOfOpponent = 2;
                range = fireRange;
                tempDamage = (getCurrentDamage() * 7) / 10;
            }
        }
        for (int i = x - range ; i <= x + range ; i++) {
            for (int j = y - range ; j <= y + range ; j++) {
                if(!GameController.getGame().getMap().checkBounds(i , j)) continue;
                tempBlock = GameController.currentGame.getMap().getABlock(i , j);
                for(Human human : tempBlock.getHumans()) {
                    if(human.getGovernment().equals(getGovernment()) || !human.isVisible()) continue;
                    human.getHit(tempDamage);
                    counter++;
                    if(counter >= numberOfOpponent) return;
                }
            }
        }
    }


    public TroopType getTroopType() {
        return troopType;
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
}
