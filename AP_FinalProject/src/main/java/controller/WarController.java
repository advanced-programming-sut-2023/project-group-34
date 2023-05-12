package controller;

import model.building.Building;
import model.building.BuildingType;
import model.building.DefenciveBuildingType;
import model.enums.BlockType;
import model.human.*;
import model.map.Block;
import model.map.GameMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;

public class WarController {
    public static String arialAttack (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        boolean flag = false;
        Block OpponentBlock = GameController.currentGame.getMap().getABlock(y, x);
        if (OpponentBlock.getHumans().isEmpty()) {
            return "there is no enemy in that block";
        }
        for (Human human : GameController.selectedWarEquipment) {
            if (GameMap.getDistanceBetweenTwoBlocks(OpponentBlock, human.getBlock()) > human.getRange() || human.getRange() <= 1) {
                continue;
            }
            flag = true;
            ArrayList<Human> humans = OpponentBlock.getHumans();
            for (int i = humans.size() - 1; i >= 0; i--) {
                Human enemy = humans.get(i);
                if (!enemy.isVisible() || enemy.getGovernment().equals(human.getGovernment())) {
                    continue;
                }
                enemy.getHit(human.getCurrentDamage());
            }
        }
        if (!flag) {
            return "that block is out of range!";
        }
        GameController.selectedWarEquipment.clear();
        return "arial attack was successful";
    }
    
    private static ArrayList<Block> getEngineerTarget (String d, Block block) {
        int x = block.getLocationI();
        int y = block.getLocationJ();
        ArrayList<Block> result = new ArrayList<>();
        switch (d) {
            case "left" -> {
                for (int i = x - 3; i <= x - 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (!GameController.currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(GameController.currentGame.getMap().getABlock(i, j));
                    }
                }
            }
            case "up" -> {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 3; j < y; j++) {
                        if (!GameController.currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(GameController.currentGame.getMap().getABlock(i, j));
                    }
                }
            }
            case "right" -> {
                for (int i = x + 1; i <= x + 3; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (!GameController.currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(GameController.currentGame.getMap().getABlock(i, j));
                    }
                }
            }
            
            default -> {
                for (int i = x - 3; i < x; i++) {
                    for (int j = y + 1; j <= y + 3; j++) {
                        if (!GameController.currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(GameController.currentGame.getMap().getABlock(i, j));
                    }
                }
            }
        }
        return result;
    }
    
    public static String pourOil (Matcher matcher) {
        String direction = matcher.group("d");
        ArrayList<Block> target = getEngineerTarget(direction, GameController.selectedWarEquipment.get(0).getBlock());
        if (target.isEmpty()) {
            return "that is the corner of the map and no blocks can get hit!";
        }
        boolean flag = false;
        for (Human human : GameController.selectedWarEquipment) {
            if (!(human instanceof Engineer engineer)) {
                continue;
            }
            if (!engineer.isEquippedWithOil()) continue;
            flag = true;
            for (Block block : target) {
                if (!block.getBuilding().isEmpty()) {
                    block.getBuilding().get(0).getHit(engineer.getCurrentDamage());
                    continue;
                }
                ArrayList<Human> humans = block.getHumans();
                for (int i = humans.size() - 1; i >= 0; i--) {
                    Human enemy = humans.get(i);
                    if (!human.isVisible()) {
                        if (enemy.getGovernment().equals(engineer.getGovernment())) continue;
                        if (human instanceof Troop troop && troop.getTroopType() == TroopType.ASSASSIN) {
                            enemy.getHit(engineer.getCurrentDamage());
                        }
                        continue;
                    }
                    enemy.getHit(engineer.getCurrentDamage());
                }
            }
            engineer.useOil();
        }
        if (!flag) return "There is no engineer in the selected humans";
        return "Deployed fire successfully!";
    }
    
    public static String digTunnel (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        Block target = GameController.currentGame.getMap().getABlock(y, x);
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if (target.getBuilding().isEmpty()) {
            return "there is no building there!";
        }
        if (target.getBuilding().get(0).getGovernment().equals(GameController.currentGame.getCurrentGovernment())) {
            return "That building is not for enemy!";
        }
        BuildingType buildingType = target.getBuilding().get(0).getBuildingType();
        ArrayList<BuildingType> goodBuildingType = new ArrayList<>(Arrays.asList(DefenciveBuildingType.HIGH_WALL,
                DefenciveBuildingType.LOW_WALL, DefenciveBuildingType.DEFENCIVE_TURRET, DefenciveBuildingType.LOOKOUT_TOWER,
                DefenciveBuildingType.PERIMETER_TOWER));
        if (!goodBuildingType.contains(buildingType)) {
            return "You can only use tunnelers on walls and towers!";
        }
        boolean flag = false;
        for (Human human : GameController.selectedWarEquipment) {
            if (!(human instanceof Tunneler tunneler)) continue;
            if (!tunneler.isThereAWay(target)) continue;
            flag = true;
            target.getBuilding().get(0).getHit(300000);
            tunneler.die();
            break;
        }
        if (!flag) {
            return "The selected wall could not be destroyed by your unit!";
        }
        return "the wall was destroyed successfully!";
    }
    
    private static boolean thereIsNoTroopToDig (Block target) {
        for (Human human : GameController.selectedWarEquipment) {
            if (!(human instanceof Troop troop)) {
                continue;
            }
            if (!troop.getCanDig()) {
                continue;
            }
            if (GameMap.getDistanceBetweenTwoBlocks(human.getBlock(), target) > human.getSpeed()) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    public static String digDitch (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        int x = Integer.parseInt(matcher.group("x"));
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        Block target = GameController.currentGame.getMap().getABlock(y, x);
        if (isUnavailable(target)) {
            return "You can not dig a ditch on that block!";
        }
        
        if (thereIsNoTroopToDig(target)) {
            return "None of the selected people could dig that place!";
        }
        target.setBlockType(BlockType.DITCH);
        return "Ditch was successfully dug!";
    }
    
    private static boolean isUnavailable (Block block) {
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND,
                BlockType.GRASS, BlockType.MEADOW, BlockType.DENSE_MEADOW, BlockType.BEACH, BlockType.PLAIN));
        
        return !goodBlockTypes.contains(block.getBlockType());
    }
    
    public static String fillDitch (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        Block target = GameController.currentGame.getMap().getABlock(y, x);
        if (!target.getBlockType().equals(BlockType.DITCH)) {
            return "that block is not a ditch";
        }
        if (thereIsNoTroopToDig(target)) {
            return "None of the selected people could dig that place!";
        }
        target.setBlockType(BlockType.GROUND);
        return "Ditch was successfully filled!";
    }
    
    public static String showDetails (GameMap gameMap, int x, int y) {
        if (x >= gameMap.getSize() || y >= gameMap.getSize() || x < 0 || y < 0) return "Out of bounds!";
        Block block = gameMap.getMap()[y][x];
        StringBuilder details = new StringBuilder();
        HashMap<String, Integer> troops = block.troops();
        Set<String> troopTypes = troops.keySet();
        details.append("details:");
        details.append("\n" + "block type: ");
        details.append(block.getBlockType().toString());
        details.append("\n" + "resource: ");
        details.append(block.resource());
        details.append("\n" + "amount: ");
        if (block.getBLockFiller() != null) details.append(block.getBlockFillerAmount());
        else if (block.resource() != null) details.append("infinity");
        details.append("\n" + "troops:");
        for (String troopType : troopTypes) {
            details.append("\n").append(troopType).append(": ").append(troops.get(troopType));
        }
        details.append("\n" + "buildings:");
        for (Building building : block.getBuilding()) {
            details.append("\n").append(building.getBuildingType().toString());
        }
        return details.toString();
    }
    
    public static String attackTheBlock (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        Block OpponentBlock = GameController.currentGame.getMap().getABlock(y, x);
        if (!OpponentBlock.getBuilding().isEmpty() && !OpponentBlock.getBuilding().get(0).getGovernment().
                equals(GameController.currentGame.getCurrentGovernment())) {
            return attackABuilding(OpponentBlock);
        }
        if (OpponentBlock.getHumans().isEmpty()) {
            return "there is no enemy in that block";
        }
        return attackHumans(OpponentBlock);
    }
    
    private static String attackABuilding (Block OpponentBlock) {
        for (Human human1 : GameController.selectedWarEquipment) {
            if (human1 instanceof SiegeMachine siegeMachine) {
                if (siegeMachine.getType().equals(SiegeType.CATAPULT) || siegeMachine.getType().equals(SiegeType.STABLE_CATAPULT)) {
                    if (GameMap.getDistanceBetweenTwoBlocks(siegeMachine.getBlock(), OpponentBlock) > siegeMachine.getRange()) {
                        continue;
                    }
                }
                OpponentBlock.getBuilding().get(0).getHit(siegeMachine.getCurrentDamage());
            }
            if (!(human1 instanceof Troop troop)) {
                continue;
            }
            if (troop.getFireRange() > 1) {
                if (GameMap.getDistanceBetweenTwoBlocks(troop.getBlock(), OpponentBlock) > troop.getRange()) {
                    OpponentBlock.getBuilding().get(0).getHit(troop.getCurrentDamage());
                }
            } else if (troop.isThereAWay(OpponentBlock)) {
                OpponentBlock.getBuilding().get(0).getHit(troop.getCurrentDamage());
            }
        }
        return "building was hit successfully!";
    }
    
    private static String attackHumans (Block OpponentBlock) {
        boolean flag = false;
        for (Human human1 : GameController.selectedWarEquipment) {
            if (human1 instanceof SiegeMachine siegeMachine) {
                flag = flag || attackBySiegeMachine(siegeMachine, OpponentBlock);
                continue;
            }
            if (!(human1 instanceof Troop human)) {
                continue;
            }
            if (human.getFireRange() > 1) {
                flag = flag || attackByLonger(human, OpponentBlock);
                continue;
            }
            if (!human.isThereAWay(OpponentBlock)) {
                continue;
            }
            if (human.getFireRange() <= 1) {
                flag = flag || attackByClosers(human, OpponentBlock);
            }
        }
        if (!flag) {
            return "no troop in selected building can commit damage!";
        }
        return "Attack implemented successfully";
    }
    
    private static boolean attackBySiegeMachine (SiegeMachine siegeMachine, Block OpponentBlock) {
        if (siegeMachine.getType().equals(SiegeType.FIRE_XBOW)) {
            if (GameMap.getDistanceBetweenTwoBlocks(siegeMachine.getBlock(), OpponentBlock) > siegeMachine.getRange()) {
                return false;
            }
            ArrayList<Human> humans = OpponentBlock.getHumans();
            for (int i = humans.size() - 1; i >= 0; i--) {
                Human enemy = humans.get(i);
                if (!enemy.isVisible() || enemy.getGovernment().equals(siegeMachine.getGovernment())) {
                    continue;
                }
                enemy.getHit(siegeMachine.getCurrentDamage());
                return true;
            }
        }
        return false;
    }
    
    private static boolean attackByLonger (Troop human, Block OpponentBlock) {
        if (GameMap.getDistanceBetweenTwoBlocks(human.getBlock(), OpponentBlock) > human.getFireRange()) {
            return false;
        }
        ArrayList<Human> humans = OpponentBlock.getHumans();
        for (int i = humans.size() - 1; i >= 0; i--) {
            Human enemy = humans.get(i);
            if (!enemy.isVisible() || enemy.getGovernment().equals(human.getGovernment())) {
                continue;
            }
            enemy.getHit(human.getCurrentDamage());
            return true;
        }
        return false;
    }
    
    private static boolean attackByClosers (Troop human, Block OpponentBlock) {
        ArrayList<Human> humans = OpponentBlock.getHumans();
        for (int i = humans.size() - 1; i >= 0; i--) {
            Human enemy = humans.get(i);
            if (!enemy.isVisible() || enemy.getGovernment().equals(human.getGovernment())) {
                continue;
            }
            enemy.getHit(human.getCurrentDamage());
            human.setBlock(OpponentBlock);
            return true;
        }
        return false;
    }
}
