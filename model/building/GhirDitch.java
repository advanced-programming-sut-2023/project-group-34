package model.building;

import model.map.Block;

import java.util.ArrayList;

public class GhirDitch {
    ArrayList<Block> blocks;
    private final int damage = 0;
    private boolean isOnFire = false;
    public GhirDitch(ArrayList<Block> blocks) {
        //TODO enable the cost
        this.blocks = blocks;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void strike() {

    }
    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }
    public void Kill() {

    }
}
