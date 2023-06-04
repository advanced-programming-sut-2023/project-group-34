package controller;

import model.Dictionaries;
import model.building.*;
import model.enums.BlockType;
import model.enums.TroopStage;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.government.Government;
import model.human.*;
import model.map.Block;
import model.map.GameMap;
import model.map.findroute.Router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;

public class TroopsController {
    public static String createUnit (Matcher matcher) {
        String type = matcher.group("type");
        type = type.replaceAll("\"", "");
        int count = Integer.parseInt(matcher.group("count"));
        if (GameController.selectedBuilding == null) {
            return "You have to choose a building first!";
        }
        Block place = GameController.selectedBuilding.getBlock();
        if (count < 1) return "Invalid count!";
        if (type.equals("engineer")) {
            return createEngineer(count, place);
        }
        if (type.equals("tunneler")) {
            return createTunneler(count, place);
        }
        if (type.equals("ladder man")) {
            return createLadderMan(count, place);
        }
        if (!Dictionaries.troopDictionary.containsKey(type)) {
            return "there is no such type!";
        }
        return createTroops(type, count, place);
    }
    
    private static String createTunneler (int count, Block place) {
        if (!createUnitErrorChecker(count, 30, GeneralBuildingsType.TUNNELERS_GUILD).equals("OK")) {
            return createUnitErrorChecker(count, 30, GeneralBuildingsType.TUNNELERS_GUILD);
        }
        for (int i = 0; i < count; i++) {
            place.getHumans().add(new Tunneler(place, GameController.currentGame.getCurrentGovernment()));
        }
        return "tunnelers trained successfully!";
    }
    
    private static String createLadderMan (int count, Block place) {
        if (!createUnitErrorChecker(count, 30, GeneralBuildingsType.BARRACK).equals("OK")) {
            return createUnitErrorChecker(count, 30, GeneralBuildingsType.BARRACK);
        }
        for (int i = 0; i < count; i++) {
            place.getHumans().add(new LadderMan(place, GameController.currentGame.getCurrentGovernment()));
        }
        return "ladder men trained successfully!";
    }
    
    private static String createEngineer (int count, Block place) {
        if (!createUnitErrorChecker(count, 50, GeneralBuildingsType.ENGINEER_GUILD).equals("OK")) {
            return createUnitErrorChecker(count, 50, GeneralBuildingsType.ENGINEER_GUILD);
        }
        for (int i = 0; i < count; i++) {
            place.getHumans().add(new Engineer(place, GameController.currentGame.getCurrentGovernment()));
        }
        return "engineers trained successfully!";
    }
    
    private static String createTroops (String type, int count, Block place) {
        ArrayList<TroopType> arabs = new ArrayList<>(Arrays.asList(TroopType.ASSASSIN, TroopType.ARAB_SWORD_MAN, TroopType.FIRE_THROWERS,
                TroopType.HORSE_ARCHER, TroopType.SLAVE, TroopType.SLINGER, TroopType.ARCHER_BOW));
        TroopType troopType = Dictionaries.troopDictionary.get(type);
        if (troopType.equals(TroopType.BLACK_MONK) && !GameController.selectedBuilding.getBuildingType().equals(GeneralBuildingsType.CATHEDRAL)) {
            return "You have to train black monks only in cathedrals!";
        }
        if ((arabs.contains(troopType) && !GameController.selectedBuilding.getBuildingType().equals(GeneralBuildingsType.MERCENARY)) ||
                ((!arabs.contains(troopType) && !troopType.equals(TroopType.BLACK_MONK)) &&
                        !GameController.selectedBuilding.getBuildingType().equals(GeneralBuildingsType.BARRACK))) {
            return "you have to select a related building!";
        }
        for (Map.Entry<MakeAble, Integer> entry : troopType.getCost().entrySet()) {
            if (count * entry.getValue() > entry.getKey().getAmount(GameController.currentGame.getCurrentGovernment())) {
                return "You do not have enough " + entry.getKey().toString() + " to train troops!";
            }
        }
        if (findUnemployed().size() < count) {
            return "You do not have enough people to hire!";
        }
        Human human;
        ArrayList<Human> unemployed = findUnemployed();
        for (int i = count - 1; i >= 0; i--) {
            human = unemployed.get(i);
            for (Map.Entry<MakeAble, Integer> entry : troopType.getCost().entrySet()) {
                entry.getKey().use(entry.getValue(), GameController.currentGame.getCurrentGovernment());
            }
            human.die();
            troopType.Creator(place, GameController.currentGame.getCurrentGovernment());
        }
        return "unit created successfully!";
    }
    
    private static String createUnitErrorChecker (int count, int price, BuildingType buildingType) {
        if (!GameController.selectedBuilding.getBuildingType().equals(buildingType)) {
            return "You have to select a related building first!";
        }
        if (count * price > Resources.GOLD.getAmount(GameController.currentGame.getCurrentGovernment())) {
            return "You do not have enough money!";
        }
        if (findUnemployed().size() < count) {
            return "You do not have enough people to hire!";
        }
        Human tempHuman;
        ArrayList<Human> unemployed = findUnemployed();
        for (int i = count - 1; i >= 0; i--) {
            tempHuman = unemployed.get(i);
            tempHuman.die();
            Resources.GOLD.use(30, GameController.currentGame.getCurrentGovernment());
        }
        return "OK";
    }
    
    static ArrayList<Human> findUnemployed () {
        ArrayList<Human> unemployed = new ArrayList<>();
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if ((human instanceof LadderMan) || (human instanceof SiegeMachine) ||
                    (human instanceof Troop) || (human instanceof Tunneler)
                || (human instanceof Engineer)) {
                continue;
            }
            if (!human.isUnemployed()) {
                continue;
            }
            unemployed.add(human);
        }
        return unemployed;
    }
    
    public static String selectUnits (Matcher matcher) {
        int xLocation = Integer.parseInt(matcher.group("x"));
        int yLocation = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        if (type == null) type = "";
        if (!GameController.currentGame.getMap().checkBounds(yLocation, xLocation))
            return "Invalid coordinates, selecting unit failed";
        type = type.replaceAll("\"", "");
        Block block = GameController.currentGame.getMap().getABlock(yLocation, xLocation);
        deselectUnits();
        if (type.isEmpty()) {
            boolean flag = false;
            for (Human warEquipment : block.getHumans()) {
                if (warEquipment.getGovernment().equals(GameController.currentGame.getCurrentGovernment())) {
                    flag = true;
                    GameController.selectedWarEquipment.add(warEquipment);
                }
            }
            if (!flag) return "there is no troop in that block!";
            return "Troops selected successfully";
        }
        if (type.equals("engineer") || type.equals("tunneler") || type.equals("ladder man")) {
            return selectOtherTroops(block, type);
        }
        if (Dictionaries.troopDictionary.containsKey(type)) {
            return selectTroop(block, type);
        }
        if (Dictionaries.siegeMachineDictionary.containsKey(type)) {
            return selectSiegeMachine(block, type);
        }
        return "Invalid type";
    } //checker
    
    private static String selectOtherTroops (Block block, String type) {
        boolean flag = false;
        switch (type) {
            case "engineer" -> {
                for (Human human : block.getHumans()) {
                    if (!(human instanceof Engineer engineer)) continue;
                    GameController.selectedWarEquipment.add(engineer);
                    flag = true;
                }
                if (!flag) return "there is no engineer in that block!";
                return "engineer selected successfully";
            }
            case "tunneler" -> {
                for (Human human : block.getHumans()) {
                    if (!(human instanceof Tunneler tunneler)) continue;
                    GameController.selectedWarEquipment.add(tunneler);
                    flag = true;
                }
                if (!flag) return "there is no tunneler in that block!";
                return "tunneler selected successfully";
            }
            case "ladder man" -> {
                for (Human human : block.getHumans()) {
                    if (!(human instanceof LadderMan ladderMan)) continue;
                    GameController.selectedWarEquipment.add(ladderMan);
                    flag = true;
                }
                if (!flag) return "there is no ladder man in that block!";
                return "ladder man selected successfully";
            }
            default -> {
                return "ERROR in select human!";
            }
        }
    }
    
    private static String selectSiegeMachine (Block block, String type) {
        boolean flag = false;
        for (Human human : block.getHumans()) {
            if (!(human instanceof SiegeMachine siegeMachine)) continue;
            SiegeType siegeType = Dictionaries.siegeMachineDictionary.get(type);
            if (!siegeMachine.getType().equals(siegeType)) continue;
            GameController.selectedWarEquipment.add(human);
            flag = true;
        }
        if (!flag) return "there is no siege machine in that block!";
        return "selected successfully";
    }
    
    private static String selectTroop (Block block, String type) {
        boolean flag = false;
        for (Human warEquipment : block.getHumans()) {
            if (warEquipment.getGovernment().equals(GameController.currentGame.getCurrentGovernment())) {
                TroopType troopType = Dictionaries.troopDictionary.get(type);
                if (!(warEquipment instanceof Troop troop)) continue;
                if (!troop.getTroopType().equals(troopType)) continue;
                flag = true;
                GameController.selectedWarEquipment.add(warEquipment);
            }
        }
        if (!flag) return "there is no troop in that block!";
        return "Troops selected successfully";
    }
    
    public static void deselectUnits () {
        GameController.selectedWarEquipment.clear();
    }
    
    public static String patrolUnit (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if (GameController.selectedWarEquipment.isEmpty()) return "You have to choose a unit first";
        if (!GameController.currentGame.getMap().checkBounds(y, x)) return "please enter a point in the map";
        Block destination = GameController.currentGame.getMap().getABlock(y, x);
        boolean canAnyonePatrol = false;
        boolean someoneCantPatrol = false;
        for (Human human : GameController.selectedWarEquipment) {
            if (Router.canFindAWay(GameController.currentGame.getMap(), destination, human)) {
                human.setDestination(destination);
                human.setPatrolDestination(human.getBlock());
                Router.moveTowardsDestination(GameController.currentGame.getMap(), destination, human);
                canAnyonePatrol = true;
            } else someoneCantPatrol = true;
        }
        if (!canAnyonePatrol) return "Nobody from selected units can patrol between those two blocks";
        if (someoneCantPatrol) return "Some of selected units couldn't patrol!";
        return "All units are patrolling!";
    }
    
    public static String buildEquipment (Matcher matcher) {
        if (GameController.selectedBuilding == null || !GameController.selectedBuilding.getBuildingType().equals(GeneralBuildingsType.SIEGE_TENT))
            return "You have to choose a siege tent first!";
        String type = matcher.group("type").replaceAll("\"", "");
        if (!Dictionaries.siegeMachineDictionary.containsKey(type)) {
            return "siege tent can not build that!";
        }
        SiegeType type1 = Dictionaries.siegeMachineDictionary.get(type);
        
        Government government = GameController.selectedBuilding.getGovernment();
        int numberOfEngineer = 0;
        for (Human human : government.getHumans()) {
            if (human instanceof Engineer) {
                numberOfEngineer++;
            }
        }
        int amount = type1.getNumberOfEngineer();
        if (amount > numberOfEngineer) {
            return "there is not enough engineer to build!";
        }
        int size = government.getHumans().size();
        for (int i = size - 1; i >= 0; i--) {
            Human human = government.getHumans().get(i);
            if (human instanceof Engineer engineer && engineer.isUnemployed()) {
                amount--;
                engineer.die();
            }
            if (amount == 0) break;
        }
        if (type1.getPrice() > Resources.GOLD.getAmount(government)) {
            return "You don't have enough money!";
        }
        Resources.GOLD.use(type1.getPrice(), government);
        type1.creator(GameController.selectedBuilding.getBlock(), government);
        
        return "The equipment was built successfully!";
    }
    
    private static Block keepFinder () {
        for (Building building : GameController.currentGame.getCurrentGovernment().getBuildings()) {
            if (building.getBuildingType().equals(GateType.KEEP)) {
                return building.getBlock();
            }
        }
        return null;
    }
    
    public static String disband () {
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have not selected any unit";
        }
        Government government = GameController.currentGame.getCurrentGovernment();
        for (int j = GameController.selectedWarEquipment.size() - 1; j >= 0; j--) {
            Human human = GameController.selectedWarEquipment.get(j);
            if (human instanceof SiegeMachine siegeMachine) {
                for (int i = 0; i < siegeMachine.getNumberOfEngineers(); i++) {
                    government.getHumans().add(new Human(keepFinder(), government));
                }
                human.die();
                continue;
            }
            new Human(keepFinder(), government);
            human.die();
            government.getHumans().remove(human);
        }
        return "Unit disbanded successfully!";
    }
    
    public static String setTroopState (Matcher matcher) {
        String state = matcher.group("state");
        if (!state.equals("defensive") && !state.equals("standing") && !state.equals("aggressive"))
            return "Invalid state, setting state failed";
        for (Human human : GameController.selectedWarEquipment) {
            if (!(human instanceof LadderMan || human instanceof Tunneler || human instanceof SiegeMachine)) {
                switch (state) {
                    case "aggressive":
                        human.setTroopStage(TroopStage.AGGRESSIVE);
                    case "standing":
                        human.setTroopStage(TroopStage.STANDING);
                    case "defensive":
                        human.setTroopStage(TroopStage.DEFENSIVE);
                }
            }
        }
        return "Troops' stage switched successfully";
    }
    
    public static String dropUnit (GameMap map, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xIndex"));
        int y = Integer.parseInt(matcher.group("yIndex"));
        String type = matcher.group("type");
        if (type == null) type = "";
        type = type.replaceAll("\"", "");
        int count = Integer.parseInt(matcher.group("count"));
        if (!map.checkBounds(y, x)) {
            return "Index out of bound! try between 0 and 399";
        }
        Block block = map.getABlock(y, x);
        if (!block.getBuilding().isEmpty()) return "You can not drop unit on buildings!";
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND,
                BlockType.GRASS, BlockType.MEADOW, BlockType.DENSE_MEADOW));
        if (!goodBlockTypes.contains(block.getBlockType())) {
            return "You can not put anything on that block!";
        }
        if (count < 1) {
            return "Invalid number for count!";
        }
        Government government = GameController.currentGame == null ? null : GameController.getGame().getCurrentGovernment();
        if (type.equals("engineer")) {
            for (int i = 0; i < count; i++) {
                block.getHumans().add(new Engineer(block, government));
            }
            return "engineers dropped successfully!";
        } else if (type.equals("ladder man")) {
            for (int i = 0; i < count; i++) {
                block.getHumans().add(new LadderMan(block, government));
            }
            return "ladder man dropped successfully!";
        } else if (type.equals("tunneler")) {
            for (int i = 0; i < count; i++) {
                block.getHumans().add(new Tunneler(block, government));
            }
            return "tunneler dropped successfully!";
        } else if (Dictionaries.siegeMachineDictionary.containsKey(type)) {
            for (int i = 0; i < count; i++) {
                Dictionaries.siegeMachineDictionary.get(type).creator(block, government);
            }
            return "siege machines dropped successfully!";
        } else if (Dictionaries.troopDictionary.containsKey(type)) {
            for (int i = 0; i < count; i++) {
                Dictionaries.troopDictionary.get(type).Creator(block, government);
            }
            return "troops dropped successfully!";
        }
        return "No such type!";
    }
}
