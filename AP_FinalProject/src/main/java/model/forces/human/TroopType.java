package model.forces.human;

import model.government.Government;
import model.map.Block;
public enum TroopType {
    ARCHER(0, 0 , 0 , true , true , 0 , 0 , 0 ),
    CROSS_BOW_MAN(0, 0 , 0 , false , false , 0 , 0 , 0),
    SPEAR_MAN(0, 0 , 0 , true , true , 0 , 0 , 0),
    PIKE_MAN(0, 0 , 0 ,false ,true , 0 , 0 , 0),
    MACE_MAN(0, 0 , 0  , true , true , 0 , 0 , 0),
    SWORDS_MAN(0, 0 , 0 ,false , false  ,0 , 0 ,0),
    KNIGHT(0, 0 , 0,false , false , 0 , 0 , 0),
    TUNNELER(0, 0 , 0 ,false , false , 0 , 0 ,0),
    LADDER_MAN(0, 0 ,0  ,false , false , 0 ,0 ,0),
    BLACK_MONK(0, 0 , 0 , false , false , 0 , 0 , 0),
    ARCHER_BOW(0, 0 , 0 ,false , true , 0 , 0 ,0),
    SLAVE(0, 0 ,0  ,false ,true,0 , 0 , 0),
    SLINGER(0, 0 , 0 ,false , false , 0 , 0, 0 ),
    ASSASSIN(0, 0 , 0 , false , false , 0 , 0 ,0),
    HORSE_ARCHER(0, 0 , 0 ,false , false , 0 ,0 ,0),
    ARAB_SWORD_MAN(0, 0 , 0 , false , false ,0  ,0 , 0 ),
    WAR_DOG(0 , 0 , 0 , false , false , 0 , 0 , 0),
    FIRE_THROWERS(0, 0 , 0 , false , false ,0 , 0 , 0);



    TroopType(int price, int HP, int damage, boolean canDig, boolean canClimb, int defensiveRange, int fireRange, int aggressiveRange) {
        this.price = price;
        this.HP = HP;
        this.damage = damage;
        this.canDig = canDig;
        this.canClimb = canClimb;
        this.defensiveRange = defensiveRange;
        this.fireRange = fireRange;
        this.aggressiveRange = aggressiveRange;
    }

    final int price;
    final int HP;
    final int damage;
    final boolean canDig;
    final boolean canClimb;
    final int defensiveRange;
    final int fireRange;
    final int aggressiveRange;
    public void Maker(Block block, Government government) {
         block.getHumans().add(new Troop(this.HP, block, this.damage, this.canDig,this.canClimb,  government, this.defensiveRange, this.fireRange, this.aggressiveRange , this, this.price));
    }
}

