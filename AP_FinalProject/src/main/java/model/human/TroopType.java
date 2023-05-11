package model.human;

import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Map;

public enum TroopType {
    ARCHER(65 , 8, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 12), Map.entry(Weapons.BOW, 1))), 20 , 4,true , true , 5 , 7 , 7 ),
    CROSS_BOW_MAN(70 , 4, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 20), Map.entry(Weapons.CROSSBOW, 1), Map.entry(Weapons.LEATHER_ARMOUR, 1))), 20 , 6,false , false , 7 , 9 , 9),
    SPEAR_MAN( 65, 2, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 8), Map.entry(Weapons.SPEAR, 1))), 30 , 2,true , true , 3 , 0 , 5),
    PIKE_MAN(75 , 8, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 8), Map.entry(Weapons.PIKE, 1), Map.entry(Weapons.METAL_ARMOUR, 1))), 30 ,8,false ,true , 3 , 0 , 5),
    MACE_MAN(80, 6,new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 20), Map.entry(Weapons.MACE, 1), Map.entry(Weapons.LEATHER_ARMOUR, 1))), 40  , 6,true , true , 3 , 0 , 5),
    SWORDS_MAN(100, 2, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 40), Map.entry(Weapons.SWORD, 1), Map.entry(Weapons.METAL_ARMOUR, 1))), 50 ,2,false , false  ,3 , 0 ,5),
    KNIGHT(120, 10, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 40), Map.entry(Weapons.HORSE, 1), Map.entry(Weapons.SWORD, 1), Map.entry(Weapons.METAL_ARMOUR, 1))), 50,8,false , false , 3 , 0 , 5),
    BLACK_MONK(70 , 4, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 10))), 30 , 6,false , false , 3 , 0 , 5),
    ARCHER_BOW(55 , 8, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 10), Map.entry(Weapons.BOW, 1))), 20 ,4,false , true , 5 , 7 ,7),
    SLAVE(40 , 8, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 5))), 10  ,1,false ,true,3, 0 , 5),
    SLINGER(45 , 8, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 5))), 20 ,2,false , false , 3 , 5, 5 ),
    ASSASSIN(60 , 6, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 60), Map.entry(Weapons.SWORD, 1))), 30 , 4,false , false , 3 , 0 ,5),
    HORSE_ARCHER(80 , 10, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 80), Map.entry(Weapons.HORSE, 1), Map.entry(Weapons.BOW, 1))), 20 ,4,false , false , 6 ,8 ,8),
    ARAB_SWORD_MAN(90 , 10, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 80), Map.entry(Weapons.SWORD, 1), Map.entry(Weapons.METAL_ARMOUR, 1))), 40 , 8,false , false ,3  ,0 , 5 ),
    WAR_DOG(50 , 10, new HashMap<>(), 10 , 0,false , false , 7 , 0 , 10),
    FIRE_THROWERS(40 , 10, new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 100))), 40 , 4,false , false ,3 , 5 , 5);



    TroopType(int HP, int speed, HashMap<MakeAble, Integer> cost, int damage, int defendRate, boolean canDig, boolean canClimb, int defensiveRange, int fireRange, int aggressiveRange) {
        this.HP = HP;
        this.speed = speed;
        this.cost = cost;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
        this.defendRate = defendRate;
    }

    final int HP;
    final int speed;
    final HashMap<MakeAble , Integer> cost;
    final int damage;
    final boolean canDig;
    final boolean canClimb;
    final int defensiveRange;
    final int fireRange;
    final int aggressiveRange;
    final int defendRate;
    public void Creator(Block block, Government government) {
         block.getHumans().add(new Troop(this.HP, block, this.damage, this.defendRate, this.canDig, this.canClimb,  government, this.defensiveRange, this.fireRange, this.aggressiveRange , this,this.speed, this.cost));
    }
    public HashMap<MakeAble , Integer> getCost() {
        return cost;
    }
}

