package model.map;

import model.building.Building;
import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.forces.human.Human;

import java.util.ArrayList;

public class Block {
    private BlockFillerType bLockFiller;
    private BlockType blockType;
    private ArrayList<Human> humans;

    public BlockFillerType getBLockFiller() {
        return bLockFiller;
    }

    private ArrayList<Building> building;

    private int resourcesCapacity;
    private boolean isPassable;

    private final int locationX;
    private final int locationY;

    public Block(int locationX, int locationY) {
        this.blockType = BlockType.GROUND;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }
    public ArrayList<Building> getBuilding() {
        return building;
    }


    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public int getResourcesCapacity() {
        return resourcesCapacity;
    }

    public void setResourcesCapacity(int resourcesCapacity) {
        this.resourcesCapacity = resourcesCapacity;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public void setBLockFiller(BlockFillerType bLockFiller) {
        this.bLockFiller = bLockFiller;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public void setBuilding(ArrayList<Building> building) {
        this.building = building;
    }

    public void addBuilding(Building building) {

    }
    public void removeBuilding(Building building) {

    }
}
