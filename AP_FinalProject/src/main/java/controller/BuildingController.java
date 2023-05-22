package controller;

import model.Dictionaries;
import model.building.*;
import model.enums.BlockType;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.government.Government;
import model.human.*;
import model.map.Block;
import model.map.GameMap;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;

public class BuildingController {
    public static String repairCurrentBuilding() {
        if (GameController.selectedBuilding == null) return "No building has been selected!";
        if (GameController.selectedBuilding.getHP() == GameController.selectedBuilding.getMaxHP())
            return "There is nothing to repair in this building, repairing failed";
        int x = GameController.selectedBuilding.getBlock().getLocationJ();
        int y = GameController.selectedBuilding.getBlock().getLocationI();

        for (int j = x - 3; j <= x + 3; j++) {
            for (int i = y - 3; i <= y + 3; i++) {
                if (!GameController.currentGame.getMap().checkBounds(i, j)) {
                    continue;
                }
                for (Human warEquipment : GameController.currentGame.getMap().getABlock(i, j).getHumans()) {
                    if (!warEquipment.getGovernment().equals(GameController.selectedBuilding.getGovernment())) {
                        return "There are enemy troops near this building, repairing failed";
                    }
                }
            }
        }

        for (Map.Entry<Resources, Integer> entry : GameController.selectedBuilding.getCost().entrySet()) {
            double resourceNeeded = Math.ceil(((double) GameController.selectedBuilding.getHP() / GameController.selectedBuilding.getMaxHP()) * entry.getValue());
            if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(entry.getKey()) < resourceNeeded)
                return "You do not have enough resources to repair this building, repairing failed";
        }
        for (Map.Entry<Resources, Integer> entry : GameController.selectedBuilding.getCost().entrySet()) {
            double resourceNeeded = Math.ceil(((double) GameController.selectedBuilding.getHP() / GameController.selectedBuilding.getMaxHP()) * entry.getValue());
            GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(entry.getKey(),
                    GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(entry.getKey()) - resourceNeeded);
        }
        GameController.selectedBuilding.setHP(GameController.selectedBuilding.getMaxHP());
        return "Building repaired successfully";
    }

    public static String closeBridge() {
        if (GameController.selectedBuilding == null || !GameController.selectedBuilding.getBuildingType().equals(DrawBridgeType.DRAW_BRIDGE)) {
            return "You have to select a gate first!";
        }
        DrawBridge bridge = (DrawBridge) GameController.selectedBuilding;
        if (bridge.isUP()) {
            return "bridge is already closed!";
        }
        bridge.setUP(true);
        return "bridge successfully closed!";
    }

    public static String openBridge() {
        if (GameController.selectedBuilding == null || !GameController.selectedBuilding.getBuildingType().equals(DrawBridgeType.DRAW_BRIDGE)) {
            return "You have to select a gate first!";
        }
        DrawBridge bridge = (DrawBridge) GameController.selectedBuilding;
        if (!bridge.isUP()) {
            return "bridge is already open!";
        }
        bridge.setUP(false);
        return "bridge successfully opened!";
    }

    public static String deployCagedWarDog() {
        if (!(GameController.selectedBuilding instanceof CagedWarDog cagedWarDog)) {
            return "You have to choose a caged war dog first!";
        }
        cagedWarDog.process();
        return "Caged war dog deployed successfully!";
    }

    public static String addWorker(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("n"));
        if (number <= 0) {
            return "Invalid number of workers!";
        }
        if (GameController.selectedBuilding == null) return "You have to choose a building first!";
        if (GameController.selectedBuilding instanceof OilSmelter oilSmelter) {
            return employOilSmelter(oilSmelter);
        }
        if (GameController.selectedBuilding instanceof Inn inn) {
            return employInn(inn);
        }
        if (GameController.selectedBuilding instanceof Maker maker) {
            return employMaker(maker, number);
        }
        return "You have chosen the wrong building for this command!";
    }

    private static String employOilSmelter(OilSmelter oilSmelter) {
        if (oilSmelter.getEngineer() != null) return "There is already an engineer in that oilSmelter!";
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Engineer engineer && engineer.isUnemployed()) {
                engineer.setUnemployed(false);
                oilSmelter.setEngineer(engineer);
                engineer.getBlock().getHumans().remove(engineer);
                engineer.setBlock(oilSmelter.getBlock());
                engineer.setVisible(false);
                return "engineer added to OilSmelter successfully!";
            }
        }
        return "there was no available engineers!";
    }

    private static String employMaker(Maker maker, int number) {
        if (maker.getNumberOfCurrentWorkers() >= maker.getNumberOfMaxWorkers()) {
            return "That building has max capacity";
        }
        int unemployedCounter = 0;
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine || human instanceof LadderMan || human instanceof Tunneler) {
                continue;
            }
            if (!human.isUnemployed()) continue;
            unemployedCounter++;
        }
        if (unemployedCounter < number) {
            return "there is not enough people to hire!";
        }
        int counter = Math.min(maker.getNumberOfMaxWorkers() - maker.getNumberOfCurrentWorkers(), number);
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine || human instanceof LadderMan || human instanceof Tunneler) {
                continue;
            }
            if (!human.isUnemployed()) continue;
            human.setUnemployed(false);
            human.setVisible(false);
            human.getBlock().getHumans().remove(human);
            human.setBlock(maker.getBlock());
            maker.addWorker();
            counter--;
            if (counter == 0) break;
        }
        return "The building was equipped with workers successfully";
    }

    private static String employInn(Inn inn) {
        if (inn.getNumberOfWorkers() == 1) {
            return "That building has max capacity";
        }
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine || human instanceof LadderMan || human instanceof Tunneler) {
                continue;
            }
            if (!human.isUnemployed()) continue;
            human.setVisible(false);
            human.getBlock().getHumans().remove(human);
            human.setBlock(inn.getBlock());
            human.setUnemployed(false);
            inn.addWorkers(1);
            return "Inn was equipped with workers successfully";
        }
        return "there is no people to hire";
    }

    public static String putLadder(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!GameController.currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        Block target = GameController.currentGame.getMap().getABlock(y, x);
        if (target.getBuilding().isEmpty()) {
            return "there is no building there!";
        }
        if (target.getBuilding().get(0).getGovernment().equals(GameController.currentGame.getCurrentGovernment())) {
            return "That wall is not for enemy!";
        }
        if (GameController.selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        BuildingType buildingType = target.getBuilding().get(0).getBuildingType();
        if (!(buildingType.equals(DefenciveBuildingType.HIGH_WALL) || buildingType.equals(DefenciveBuildingType.LOW_WALL))) {
            return "You can only use ladder man on walls!";
        }
        boolean flag = false;
        int size = GameController.selectedWarEquipment.size();
        Human human;
        for (int i = size - 1; i >= 0; i--) {
            human = GameController.selectedWarEquipment.get(i);
            if (!(human instanceof LadderMan ladderMan)) continue;
            if (GameMap.getDistanceBetweenTwoBlocks(ladderMan.getBlock(), target) > ladderMan.getSpeed()) continue;
            flag = true;
            if (target.getBuilding().get(0) instanceof DefenciveBuilding defenciveBuilding) {
                defenciveBuilding.setHasLadder(true);
                ladderMan.die();
                break;
            }
        }
        if (!flag) {
            return "The selected wall could not be laddered on by your unit!";
        }
        return "the wall was laddered on successfully!";
    }

    static String checkBlockType(Block block, BuildingType buildingType) {
        if ((buildingType.equals(MakerType.HOP_FARM) || buildingType.equals(MakerType.WHEAT_FARM))) {
            if ((!block.getBlockType().equals(BlockType.GRASS) && !block.getBlockType().equals(BlockType.DENSE_MEADOW))) {
                return "You can't put farm on that ground!";
            } else return "OK";
        }
        if (buildingType.equals(MakerType.QUARRY)) {
            if (!block.getBlockType().equals(BlockType.BOULDER)) {
                return "You only can put quarry on rocks!";
            } else return "OK";
        }
        if (buildingType.equals(MakerType.IRON_MINE)) {
            if (!block.getBlockType().equals(BlockType.IRON)) {
                return "You can only put iron mine on iron!";
            } else return "OK";
        }
        if (buildingType.equals(MakerType.PITCH_RIG)) {
            if (!block.getBlockType().equals(BlockType.PLAIN))
            {
                return "You can only put pitch rig on plains!";
            }
            else return "OK";
        }
        if (buildingType.equals(DrawBridgeType.DRAW_BRIDGE)) {
            ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.LAKE, BlockType.SEA,
                    BlockType.RIVER, BlockType.DITCH));
            if (goodBlockTypes.contains(block.getBlockType())) return "OK";
            return "You can not put draw bridge on that block!";
        }
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND,
                BlockType.GRASS, BlockType.MEADOW, BlockType.DENSE_MEADOW, BlockType.BEACH, BlockType.PLAIN));
        if (!goodBlockTypes.contains(block.getBlockType()) && !buildingType.equals(DrawBridgeType.DRAW_BRIDGE)) {
            return "You cannot put anything on that block!";
        }
        return "OK";
    }

    public static String dropBuilding(GameMap map, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xIndex"));
        String type = matcher.group("type");
        int y = Integer.parseInt(matcher.group("yIndex"));
        if (type == null) type = "";
        type = type.replaceAll("\"", "");

        if (!map.checkBounds(y, x)) {
            return "Index out of bound! try between 0 and 399";
        }
        if (!Dictionaries.buildingDictionary.containsKey(type)) {
            return "Invalid building name!";
        }
        Block block = map.getABlock(y, x);
        if (!block.getBuilding().isEmpty()) return "You already have building in that block!";
        BuildingType buildingType = Dictionaries.buildingDictionary.get(type);
        if (buildingType.equals(GateType.KEEP)) {
            return "You can not build a keep in the middle of the game!";
        }
        if (!checkBlockType(block, buildingType).equals("OK")) {
            return checkBlockType(block, buildingType);
        }
        if (buildingType.equals(DrawBridgeType.DRAW_BRIDGE)) {
            ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.SEA,
                    BlockType.RIVER,
                    BlockType.DITCH,
                    BlockType.LAKE));
            if (!(goodBlockTypes.contains(block.getBlockType()))) {
                return "you can not put a draw bridge on that block!";
            }
        }
        if (buildingType.equals(GeneralBuildingsType.FOOD_STORAGE)) {
            Block tempBlock;
            boolean flag = false;
            outer:
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= j + 1; j++) {
                    if (i < 0 || j < 0 || i > 399 || j > 399) {
                        continue;
                    }
                    tempBlock = map.getABlock(i, j);
                    if (GameMap.getDistanceBetweenTwoBlocks(tempBlock, block) == 1 && tempBlock.containsThisBuilding(buildingType)) {
                        flag = true;
                        break outer;
                    }
                }
            }
            if (!flag) {
                return "you have to put a food storage near other food storages";
            }
        }
        boolean isGameStarted = GameController.currentGame != null;
        if (isGameStarted) {
            Government government = GameController.currentGame.getCurrentGovernment();
            for (Map.Entry<Resources, Integer> entry : buildingType.getCost().entrySet()) {
                if (entry.getValue() > entry.getKey().getAmount(government)) {
                    return "You dont have enough " + entry.getKey().toString() + " to make this building";
                }
                entry.getKey().use(entry.getValue(), government);
            }
            buildingType.create(government, block);
        } else buildingType.create(null, block);
        return "Building created successfully!";
    }

    public static String setKeep(String username, int x, int y) {
        if (!GameController.currentGame.getMap().checkBounds(y, x)) return "Keep out of bounds!";
        User currentPlayer = User.getUserByUsername(username);
        if (currentPlayer == null) return "No user with the id given!";
        if (GameController.getCurrentGame().getPlayers().contains(currentPlayer)) return "User already added!";
        Block block = GameController.currentGame.getMap().getABlock(y, x);
        if (!block.getBuilding().isEmpty())
            return "You cannot place the keep here, another building is here already";
        String response = checkBlockType(block, GateType.KEEP);
        if (!response.equals("OK")) return response;
        currentPlayer.setGovernment(new Government(currentPlayer, ""));
        if (currentPlayer.equals(User.currentUser))
            GameController.currentGame.setCurrentGovernment(currentPlayer.getGovernment());
        GameController.getCurrentGame().addPlayer(currentPlayer);
        response = placeFoodStorage(x, y, currentPlayer.getGovernment());
        if (!response.equals("food storage placed in successfully")) return response;
        GateType.KEEP.create(currentPlayer.getGovernment(), block);
        return null;
    }

    private static String placeFoodStorage(int x, int y, Government government) {
        Block block;
        int flag = 0;
        outer:
        for (int i = -1; i < 2; i++) {
            inner:
            for (int j = -1; j < 2; j++) {
                if (!GameController.getGame().getMap().checkBounds(y + j, x + i)) continue;
                if (i == 0 && j == 0) continue;
                block = GameController.getGame().getMap().getABlock(y + j, x + i);
                String response = checkBlockType(block, GeneralBuildingsType.FOOD_STORAGE);
                if (response.equals("OK")) {
                    GeneralBuildingsType.FOOD_STORAGE.create(government, block);
                    flag++;
                    break outer;
                }
            }
        }
        if (flag == 0) return "You cannot place your keep here";
        if (Food.BREAD.getLeftCapacity(government) >= 50) {
            Food.BREAD.add(50, government);
        }
        return "food storage placed in successfully";
    }
}
