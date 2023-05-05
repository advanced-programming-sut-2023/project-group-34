package model.map;

import model.building.Building;
import model.building.BuildingType;
import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.human.Human;
import model.human.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Block {
    private BlockFillerType bLockFiller;
    private BlockType blockType;

    public BlockFillerType getBLockFiller() {
        return bLockFiller;
    }

    private ArrayList<Building> building = new ArrayList<>();

    private final ArrayList <Human> humans = new ArrayList<>();
    private int resourcesCapacity;
    private boolean isPassable;
    private final int locationI;
    private final int locationJ;

    public Block(int locationI, int locationJ) {
        this.blockType = BlockType.GROUND;
        this.locationI = locationI;
        this.locationJ = locationJ;
    }

    public Block(Block sample) {
        this.blockType = sample.getBlockType();
        this.locationI = sample.locationI;
        this.locationJ = sample.locationJ;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public ArrayList<Building> getBuilding() {
        return building;
    }

    public int getLocationI() {
        return locationI;
    }

    public int getLocationJ() {
        return locationJ;
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

    public void setBuilding(ArrayList<Building> building) {
        this.building = building;
    }

    public void addBuilding(Building building) {
        this.building.add(building);
    }
    public void removeBuilding(Building building) {
        this.building.remove(building);
    }

    public Troop[] getTroops() {
        ArrayList<Troop> troops = new ArrayList<>();
        for (Human human : humans) {
            if (human instanceof Troop) troops.add((Troop) human);
        }
        Troop[] result = new Troop[troops.size()];
        for (int i = 0; i < troops.size(); i++) {
            result[i] = troops.get(i);
        }
        return result;
    }

    public HashMap<String, Integer> troops() {
        HashMap<String, Integer> result = new HashMap<>();
        for (Human human : humans) {
            if (!(human instanceof Troop)) continue;
            String type = Troop.troopsNameString.get(((Troop) human).getTroopType());
            if (result.containsKey(type)) result.replace(type, result.get(type) + 1);
            else result.put(type, 1);
        }
        return result;
    }

    public String resource() {
        if (bLockFiller == null || bLockFiller.equals(BlockFillerType.STAIR)) return bLockFiller.toString();
        else if (blockType.equals(BlockType.IRON)) return "iron";
        else if (blockType.equals(BlockType.BOULDER)) return "stone";
        else if (blockType.equals(BlockType.OIL)) return "oil";
        else return null;
    }

    public boolean containsThisBuilding(BuildingType buildingType) {
        for(Building building1 : this.getBuilding()) {
            if(building1.getBuildingType().equals(buildingType)) {
                return true;
            }
        }
        return false;
    }
}
