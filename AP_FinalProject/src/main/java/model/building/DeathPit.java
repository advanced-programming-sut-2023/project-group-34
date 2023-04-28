package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class DeathPit extends Building {

    private final int damage;
    protected DeathPit(Government government, Block block, int HP, HashMap<Resources, Integer> cost, int damage) {
        super(government, block, HP, cost, DeathPitType.DEATH_PIT);
        this.damage = damage;
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

    public void deployTrap() {

    }

    public int getDamage() {
        return damage;
    }

}
