package model.map;

import model.Dictionaries;
import model.building.Building;
import model.building.BuildingType;
import model.enums.BlockFillerType;
import model.enums.BlockType;
import model.human.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Block {
    private BlockFillerType bLockFiller;

    private int blockFillerAmount = 100;

    private BlockType blockType;
    public BlockFillerType getBLockFiller() {
        return bLockFiller;
    }

    private ArrayList<Building> building = new ArrayList<>();

    private final ArrayList <Human> humans = new ArrayList<>();
    private final int locationI;
    private final int locationJ;
    public Block(int locationI, int locationJ) {
        this.blockType = BlockType.GROUND;
        this.locationI = locationI;
        this.locationJ = locationJ;
    }

    public Block(Block sample) {
        this.bLockFiller = sample.getBLockFiller();
        this.blockType = sample.getBlockType();
        this.locationI = sample.locationI;
        this.locationJ = sample.locationJ;
        this.blockFillerAmount = 100;
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
    

    public void setBLockFiller(BlockFillerType bLockFiller) {
        this.bLockFiller = bLockFiller;
    }

    public void setBuilding(ArrayList<Building> building) {
        this.building = building;
    }

    public void addBuilding(Building building) {
        this.building.add(building);
    }

    public Human[] getTroops() {
        ArrayList<Human> troops = new ArrayList<>();
        for (Human human : humans) {
            if (human instanceof Troop) troops.add(human);
            if (human instanceof Engineer engineer) troops.add(engineer);
            if (human instanceof LadderMan ladderMan) troops.add(ladderMan);
            if (human instanceof SiegeMachine siegeMachine) troops.add(siegeMachine);
        }
        Human[] result = new Human[troops.size()];
        for (int i = 0; i < troops.size(); i++) {
            result[i] = troops.get(i);
        }
        return result;
    }

    public HashMap<String, Integer> troops() {
        HashMap<String, Integer> result = new HashMap<>();
        for (Human human : humans) {
            if (human instanceof Troop troop) {
            String type = Dictionaries.troopDictionary.inverse().get(troop.getTroopType());
            if (result.containsKey(type)) result.replace(type, result.get(type) + 1);
            else result.put(type, 1);
            }
            if (human instanceof SiegeMachine siegeMachine) {
                String type = Dictionaries.siegeMachineDictionary.inverse().get(siegeMachine.getType());
                if (result.containsKey(type)) result.replace(type, result.get(type) + 1);
                else result.put(type, 1);
            }
            if(human instanceof Engineer) {
                if (result.containsKey("engineer")) result.replace("engineer", result.get("engineer") + 1);
                else result.put("engineer", 1);
            }
            if(human instanceof Tunneler) {
                if (result.containsKey("tunneler")) result.replace("tunneler", result.get("tunneler") + 1);
                else result.put("tunneler", 1);
            }
            if(human instanceof LadderMan) {
                if (result.containsKey("ladder man")) result.replace("ladder man", result.get("ladder man") + 1);
                else result.put("ladder man", 1);
            }
        }
        return result;
    }

    public String resource() {
        if (bLockFiller != null) return bLockFiller.toString();
        if (blockType.equals(BlockType.IRON)) return "iron";
        if (blockType.equals(BlockType.BOULDER)) return "stone";
        if (blockType.equals(BlockType.OIL)) return "oil";
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

    public int getBlockFillerAmount() {
        return blockFillerAmount;
    }

    public void useBlockFillerAmount(int amount) {
        this.blockFillerAmount -= amount;
        if(blockFillerAmount < 0) {
            blockFillerAmount = 0;
            bLockFiller = null;
        }
    }
}
