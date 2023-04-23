package model.building;

import model.enums.benums.PitchDitchType;
import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class PitchDitch extends Building{
    ArrayList<Block> blocks;
    private final int damage = 0;
    private boolean isOnFire = false;

    protected PitchDitch(Government government, Block block, int HP, HashMap<Resources, Integer> cost) {
        super(government, block, HP, cost, PitchDitchType.PITCH_DITCH);
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void strike() {

    }
    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }


    @Override
    public void process() {
     //TODO kill people
    }

    @Override
    public void destroy() {

    }
}
