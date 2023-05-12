package controller;

import model.Dictionaries;
import model.Game;
import model.Trade;
import model.building.*;
import model.enums.BlockType;
import model.enums.Direction;
import model.enums.TroopStage;
import model.enums.make_able.Food;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.government.Government;
import model.human.*;
import model.map.GameMap;
import model.map.findroute.Router;
import model.user.User;
import model.map.Block;
import view.BackgroundColor;
import view.gameMenu.GameMenu;
import view.gameMenu.MapMenu;
import view.gameMenu.ShopMenu;
import view.gameMenu.TradeMenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;

public class GameController {
    public static Game currentGame;
    public static Building selectedBuilding;
    public static ArrayList<Human> selectedWarEquipment = new ArrayList<>();

    public static String run(){
        while (true) {
            String response = GameMenu.run();
            switch (response) {
                case "shop menu" -> ShopMenu.run();
                case "trade menu" -> TradeMenu.run();
                case "map menu" -> MapMenu.run();
                case "back" -> {
                    return "back";
                }
            }
        }
    }
    
    public static void setCurrentGame (Game currentGame) {
        GameController.currentGame = currentGame;
    }
    
    public static Game getCurrentGame () {
        return currentGame;
    }
    
    public static Game getGame() {
        return currentGame;
    }

    public static void setGame(Game game) {
        currentGame = game;
    }

    public static String selectBuilding(Matcher matcher){
        int xLocation = Integer.parseInt(matcher.group("x"));
        int yLocation = Integer.parseInt(matcher.group("y"));

        if (xLocation < 0 || yLocation < 0 || xLocation > 399 || yLocation > 399)
            return "Invalid coordinates, selecting building failed";

        if (currentGame.getMap().getABlock(yLocation, xLocation).getBuilding() == null ||
                currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().isEmpty())
            return "There is no building in this block, selecting building failed";

        if (!currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().get(0).getGovernment().getOwner()
                .getName().equals(currentGame.getCurrentGovernment().getOwner().getName()))
            return "You do not own this building, selecting building failed";

        selectedBuilding = currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().get(0);
        return "Building selected successfully";
    } //checked

    public static String deselectBuilding(){
        if (selectedBuilding == null) return "You have no building selected, deselecting building failed";
        else selectedBuilding = null;
        return "Building deselected successfully";
    } //checked

    public static String moveUnit (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (currentGame.getMap().checkBounds(y, x)) return "Out of bounds!";
        Router router = new Router(currentGame.getMap(), selectedWarEquipment.get(0).getBlock(), currentGame.getMap().getABlock(y, x), (Troop) selectedWarEquipment.get(0));
        ArrayList<Block> route = router.findBestRoute();
        if (route == null) return "Can't reach there!";
        if (route.size() == 0) return "You are already in the destination given!";
        for (Human human : selectedWarEquipment) {
            human.setRoute(new ArrayList<>(route));
            human.applyMoves();
        }
        return null;
        //todo: Arshia check it!
    }

    public static String setMapLocation (int x, int y) {
        if (!(currentGame.getMap().checkBounds(y, x) && currentGame.getMap().checkBounds(y + 10, x + 10))) return "Wrong coordinates";
        currentGame.getMap().setUpLeftCorner(x, y);
        return null;
    }

    public static String showMiniMap (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String response = setMapLocation(x, y);
        if (response != null) return response;
        return showMiniMap();
    } //checked

    public static String showMiniMap () {
        StringBuilder output = new StringBuilder();
        Block[][] map = currentGame.getMap().getMiniMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j].getTroops().length != 0) output.append(BackgroundColor.dictionary(map[i][j]) + "S " + "\u001B[0m");
                else if (map[i][j].getBuilding().size() != 0)
                    if (map[i][j].getBuilding().get(0).equals(DeathPitType.DEATH_PIT) && map[i][j].getBuilding().get(0).getGovernment().equals(currentGame.getCurrentGovernment()))
                        output.append(BackgroundColor.dictionary(map[i][j]) + "B " + "\u001B[0m");
                    else
                        output.append(BackgroundColor.dictionary(map[i][j]) + "B " + "\u001B[0m");
                else if (map[i][j].getBLockFiller() != null)
                    output.append(BackgroundColor.dictionary(map[i][j]) + "T " + "\u001B[0m");
                else {
                    String abbreviation= map[i][j].getBlockType().toString().substring(0,2);
                    output.append(BackgroundColor.dictionary(map[i][j]) + abbreviation + "\u001B[0m");
                }
                output.append(BackgroundColor.dictionary(map[i][j]) + ' ' + "\u001B[0m");
            }
            output.append('\n');
        }
        return output.toString();
    } //checked

    public static String moveMiniMap (Matcher matcher) {
        String up = matcher.group("up");
        String down = matcher.group("down");
        String left = matcher.group("left");
        String right = matcher.group("right");
        if ((down != null && up != null) || (left != null && right != null)) return "Invalid command: opposite directions used!";
        if (up != null) currentGame.getMap().moveMiniMap(Direction.NORTH, Integer.parseInt(up));
        if (down != null) currentGame.getMap().moveMiniMap(Direction.SOUTH, Integer.parseInt(down));
        if (left != null) currentGame.getMap().moveMiniMap(Direction.WEST, Integer.parseInt(left));
        if (right != null) currentGame.getMap().moveMiniMap(Direction.EAST, Integer.parseInt(right));
        return showMiniMap();
    } //checked

    public static String getBlockDetails (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return currentGame.getMap().showDetails(x, y);
    } //checked

    public static String showPopularity () {
        return "Your current popularity is: " + currentGame.getCurrentGovernment().getTotalPopularity();
    }

    public static String showPopularityFactors () {
        String finalString = "";
        finalString = finalString.concat("Food: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFoodPopularity() + "\n");
        finalString = finalString.concat("Fear: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFearRate() + "\n");
        finalString = finalString.concat("Tax: " + currentGame.getCurrentGovernment().getAccountingDepartment().getTaxPopularity() + "\n");
        finalString = finalString.concat("Religion: " + currentGame.getCurrentGovernment().getAccountingDepartment().getReligionPopularity());
        return finalString;
    }

    public static String showFoodList () {
        String finalString = "";
        finalString = finalString.concat("Bread: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.BREAD) + "\n");
        finalString = finalString.concat("Meat: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.MEAT) + "\n");
        finalString = finalString.concat("Apple: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.APPLE) + "\n");
        finalString = finalString.concat("Cheese: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.CHEESE));
        return finalString;
    } //checked

    public static String showFoodRate() {
        return "Your current food rate is: " + currentGame.getCurrentGovernment().
                getAccountingDepartment().getFoodRate();
    }
    public static String showTaxRate() {
        return "Your current tax rate is: " + currentGame.getCurrentGovernment().
                getAccountingDepartment().getTaxRate();
    }
    public static String showFearRate() {
        return "Your current fear rate is: " + currentGame.getCurrentGovernment().
                getAccountingDepartment().getFearRate();
    }

    public static String setTaxRate (Matcher matcher) {
        if(selectedBuilding == null || !(selectedBuilding.getBuildingType() == GateType.BIG_GATE_HOUSE || selectedBuilding.getBuildingType() == GateType.SMALL_GATE_HOUSE || selectedBuilding.getBuildingType() == GateType.KEEP)) {
            return "You have not selected the right building";
        }
        int taxRate = Integer.parseInt(matcher.group("taxRate"));
        if (taxRate < -3  || taxRate > 8) return "Invalid tax rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setTaxRate(taxRate);
        return "Tax rate successfully";
    }

    public static String setFearRate (Matcher matcher) {
        int fearRate = Integer.parseInt(matcher.group("fearRate"));
        if (fearRate < -5 || fearRate > 5) return "Invalid fear rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFearRate(fearRate);
        return "Fear rate set successfully";
    }

    public static String setFoodRate(Matcher matcher){
        if(selectedBuilding == null || !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.FOOD_STORAGE))
            return "you have not chosen a food storage";
        int foodRate = Integer.parseInt(matcher.group("foodRate"));
        if (foodRate < -2 || foodRate > 2) return "Invalid food rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFoodRate(foodRate);
        return "Food rate set successfully";
    }

    public static String createUnit (Matcher matcher) {
        String type = matcher.group("type");
        type = type.replaceAll("\"", "");
        int count = Integer.parseInt(matcher.group("count"));
        if(selectedBuilding == null) {
            return "You have to choose a building first!";
        }
        Block place = selectedBuilding.getBlock();
        if(count < 1) return "Invalid count!";
        if(type.equals("engineer")) {
            if(!createUnitErrorChecker(count , 50 , GeneralBuildingsType.ENGINEER_GUILD).equals("OK")) {
                return createUnitErrorChecker(count , 50 , GeneralBuildingsType.ENGINEER_GUILD);
            }
            for (int i = 0; i < count; i++) {
                place.getHumans().add(new Engineer(place , currentGame.getCurrentGovernment()));
            }
            return "engineers trained successfully!";
        }
        if(type.equals("tunneler")) {
            if(!createUnitErrorChecker(count , 30 , GeneralBuildingsType.TUNNELERS_GUILD).equals("OK")) {
                return createUnitErrorChecker(count , 30 , GeneralBuildingsType.TUNNELERS_GUILD);
            }
            for (int i = 0; i < count; i++) {
                place.getHumans().add(new Tunneler(place , currentGame.getCurrentGovernment()));
            }
            return "tunnelers trained successfully!";
        }
        if(type.equals("ladder man")) {
            if(!createUnitErrorChecker(count , 30 , GeneralBuildingsType.BARRACK).equals("OK")) {
                return createUnitErrorChecker(count , 30 , GeneralBuildingsType.BARRACK);
            }
            for (int i = 0; i < count; i++) {
                place.getHumans().add(new LadderMan(place , currentGame.getCurrentGovernment()));
            }
            return "ladder men trained successfully!";
        }
        if(!Dictionaries.troopDictionary.containsKey(type)) {
            return "there is no such type!";
        }
        ArrayList<TroopType> arabs = new ArrayList<>(Arrays.asList(TroopType.ASSASSIN, TroopType.ARAB_SWORD_MAN,
                TroopType.FIRE_THROWERS, TroopType.HORSE_ARCHER, TroopType.SLAVE, TroopType.SLINGER, TroopType.ARCHER_BOW));
        TroopType troopType = Dictionaries.troopDictionary.get(type);
        if(troopType.equals(TroopType.BLACK_MONK) && !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.CATHEDRAL)) {
            return "You have to train black monks only in cathedrals!";
        }
        if(     (arabs.contains(troopType) && !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.MERCENARY)) ||
                ((!arabs.contains(troopType) && !troopType.equals(TroopType.BLACK_MONK))&& !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.BARRACK))) {
            return "you have to select a related building!";
        }
        for(Map.Entry<MakeAble , Integer> entry : troopType.getCost().entrySet()) {

            if (count * entry.getValue() > entry.getKey().getAmount(currentGame.getCurrentGovernment())) {
                return "You do not have enough " + entry.getKey().toString() + " to train troops!";
            }
        }
        if(findUnemployed().size() < count) {
            return "You do not have enough people to hire!";
        }
        Human human;
        ArrayList<Human> unemployed = findUnemployed();
        for (int i = 0; i < count; i++) {
            human = unemployed.get(i);
            for(Map.Entry<MakeAble , Integer> entry : troopType.getCost().entrySet()) {
                entry.getKey().use(entry.getValue(), currentGame.getCurrentGovernment());
            }
            human.die();
            troopType.Creator(place , currentGame.getCurrentGovernment());
        }
        return "unit created successfully!";
    } //checked

    private static String createUnitErrorChecker(int count , int price , BuildingType buildingType) {
        if(!selectedBuilding.getBuildingType().equals(buildingType)) {
            return "You have to select a related building first!";
        }
        if(count * price > Resources.GOLD.getAmount(currentGame.getCurrentGovernment())) {
            return "You do not have enough money!";
        }
        if(findUnemployed().size() < count) {
            return "You do not have enough people to hire!";
        }
        Human tempHuman;
        ArrayList<Human> unemployed = findUnemployed();
        for (int i = 0; i < count; i++) {
            tempHuman = unemployed.get(i);
            tempHuman.die();
            Resources.GOLD.use(30, currentGame.getCurrentGovernment());
        }
        return "OK";
    } //checked

    private static ArrayList<Human> findUnemployed() {
        ArrayList<Human> unemployed = new ArrayList<>();
        for(Human human : currentGame.getCurrentGovernment().getHumans()) {
            if(     (human instanceof LadderMan) ||
                    (human instanceof SiegeMachine) ||
                    (human instanceof Troop) ||
                    (human instanceof Tunneler)) {
                continue;
            }
            if(!human.isUnemployed()) {
                continue;
            }
            unemployed.add(human);
        }
        return unemployed;
    } //checked

    public static String moveSelectedUnits (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if(x < 0 || x > 399 || y < 0 || y > 399) {
            return "please enter a point in the map";
        }
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        Block block = currentGame.getMap().getABlock(x , y);
        boolean flag = false;
        for (Human human : selectedWarEquipment) {
            if(human.isThereAWay(block)) {
                flag = true;
                human.setBlock(block);
            }
        }
        if(!flag) {
            return "Nobody from selected unit can rich there!";
        }
        return "Units reached successfully!";
    }

    public static String repairCurrentBuilding () {
        if (selectedBuilding.getHP() == selectedBuilding.getMaxHP())
            return "There is nothing to repair in this building, repairing failed";
        int x = selectedBuilding.getBlock().getLocationI() - 1;
        int y = selectedBuilding.getBlock().getLocationJ() - 1;

        for (int i = x; i < x + 3; i++){
            for (int j = y; j < y + 3; y++) {
                if (!currentGame.getMap().checkBounds(j , i)){
                    continue;
                }
                for (Human warEquipment : currentGame.getMap().getABlock(j, i).getHumans()){
                    if (!warEquipment.getGovernment().equals(selectedBuilding.getGovernment())){
                        return "There are enemy troops near this building, repairing failed";
                    }
                }
            }
        }

        for (Map.Entry<Resources, Integer> entry : selectedBuilding.getCost().entrySet()) {
            double resourceNeeded = Math.ceil(((double) selectedBuilding.getHP()/selectedBuilding.getMaxHP()) * entry.getValue());
            if (currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(entry.getKey()) < resourceNeeded)
                return "You do not have enough resources to repair this building, repairing failed";
        }
        for (Map.Entry<Resources, Integer> entry : selectedBuilding.getCost().entrySet()) {
            double resourceNeeded = Math.ceil(((double) selectedBuilding.getHP() / selectedBuilding.getMaxHP()) * entry.getValue());
            currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                    put(entry.getKey(), currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                            get(entry.getKey()) - resourceNeeded);
        }
        return "Building repaired successfully";
    }


    public static String arialAttack(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!currentGame.getMap().checkBounds(x , y)) {
            return "please enter a point in the map";
        }
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        boolean flag = false;
        Block OpponentBlock = currentGame.getMap().getABlock(x , y);
        if(OpponentBlock.getHumans().isEmpty()) {
            return "there is no enemy in that block";
        }
        for(Human human : selectedWarEquipment) {
            if(!(human instanceof Troop warEquipment)) {
                continue;
            }
            if(GameMap.getDistanceBetweenTwoBlocks(OpponentBlock , warEquipment.getBlock()) > warEquipment.getFireRange() || warEquipment.getFireRange() <= 1) {
                continue;
            }
            flag = true;
            for(Human enemy : OpponentBlock.getHumans()) {
                if(!enemy.isVisible() || enemy.getGovernment().equals(warEquipment.getGovernment())) {
                    continue;
                }
                enemy.getHit(warEquipment.getCurrentDamage());
            }
        }
        if(!flag) {
            return "that block is out of range!";
        }
        selectedWarEquipment.clear();
        return "arial attack was successful";
    }

    public static String selectUnits (Matcher matcher) {
        //TODO needs to be checked for type
        int xLocation = Integer.parseInt(matcher.group("x"));
        int yLocation = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        if (!currentGame.getMap().checkBounds(yLocation , xLocation))
            return "Invalid coordinates, selecting unit failed";
        Block block = currentGame.getMap().getABlock(yLocation , xLocation);

        if(type == null) {
            boolean flag = false;
            for (Human warEquipment: block.getHumans()){
                if (warEquipment.getGovernment().equals(currentGame.getCurrentGovernment())){
                    flag = true;
                    selectedWarEquipment.add(warEquipment);
                }
            }
            if(!flag) return "there is no troop in that block!";
            return "Troops selected successfully";
        }

        if(Dictionaries.troopDictionary.containsKey(type)) {
            return selectTroop(block , type);
        }
        if(Dictionaries.siegeMachineDictionary.containsKey(type)) {
            return selectSiegeMachine(block , type);
        }
        return "Invalid type";
    }
    private static String selectSiegeMachine(Block block , String type) {
        boolean flag = false;
        for(Human human : block.getHumans()) {
            if(!(human instanceof SiegeMachine siegeMachine)) continue;
            SiegeType siegeType = Dictionaries.siegeMachineDictionary.get(type);
            if(!siegeMachine.getType().equals(siegeType)) continue;
            selectedWarEquipment.add(human);
            flag = true;
        }
        if(!flag) return "there is no siege machine in that block!";
        return "selected successfully";
    }

    private static String selectTroop(Block block , String type) {
        boolean flag = false;
        for (Human warEquipment: block.getHumans()){
            if (warEquipment.getGovernment().equals(currentGame.getCurrentGovernment())){
                switch (type) {
                    case "engineer" -> {
                        if ((warEquipment instanceof Engineer)) continue;
                        flag = true;
                        selectedWarEquipment.add(warEquipment);
                    }
                    case "tunneler" -> {
                        if ((warEquipment instanceof Tunneler)) continue;
                        flag = true;
                        selectedWarEquipment.add(warEquipment);
                    }
                    case "ladder man" -> {
                        if (!(warEquipment instanceof LadderMan)) continue;
                        flag = true;
                        selectedWarEquipment.add(warEquipment);
                    }
                    default -> {
                        TroopType troopType = Dictionaries.troopDictionary.get(type);
                        if(!(warEquipment instanceof Troop troop)) continue;
                        if(!troop.getTroopType().equals(troopType)) continue;
                        flag = true;
                        selectedWarEquipment.add(warEquipment);
                    }
                }
            }
        }
        if(!flag) return "there is no troop in that block!";
        return "Troops selected successfully";
    }
    public static String deselectUnits(){
        if (selectedWarEquipment.isEmpty()) return "You have no troops selected";
        else selectedWarEquipment.clear();
        return "Troops deselected successfully";
    } //checked

    public static String closeBridge() {
        if(selectedBuilding == null || !selectedBuilding.getBuildingType().equals(DrawBridgeType.DRAW_BRIDGE)) {
            return "You have to select a gate first!";
        }
        DrawBridge bridge = (DrawBridge) selectedBuilding;
        if(bridge.isUP()) {
            return "bridge is already closed!";
        }
        bridge.setUP(true);
        return "bridge successfully closed!";
    } //checked

    public static String patrolUnit(Matcher matcher) {
        int y1 = Integer.parseInt(matcher.group("y1"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        int x1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if(!currentGame.getMap().checkBounds(x1 , y1)) {
            return "please enter a point in the map";
        }
        if(!currentGame.getMap().checkBounds(x2 , y2)) {
            return "please enter a point in the map";
        }
        Block block2 = currentGame.getMap().getABlock(x2 , y2);
        Block block1 = currentGame.getMap().getABlock(x1 , y1);
        if(!block1.isPassable() || !block2.isPassable()) {
            return "these blocks are not passable";
        }
        boolean flag = false;
        Block tempBlock;
        for(Human human : selectedWarEquipment) {
            tempBlock = human.getBlock();
            if(!human.isThereAWay(block1)) {
                continue;
            }
            human.setBlock(block1);
            if(!human.isThereAWay(block2)) {
                human.setBlock(tempBlock);
                continue;
            }
            flag = true;
            //TODO patrol between
        }
        if(!flag) {
            return "Nobody from selected unit can patrol between those two blocks";
        }
        return "unit is patrolling successfully!";
    }

    public static String openBridge() {
        if(selectedBuilding == null || !selectedBuilding.getBuildingType().equals(DrawBridgeType.DRAW_BRIDGE)) {
            return "You have to select a gate first!";
        }
        DrawBridge bridge = (DrawBridge) selectedBuilding;
        if(!bridge.isUP()) {
            return "bridge is already open!";
        }
        bridge.setUP(false);
        return "bridge successfully opened!";
    } //checked

    public static String attackTheBlock (Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if(currentGame.getMap().checkBounds(y , x)) {
            return "please enter a point in the map";
        }
        Block OpponentBlock = currentGame.getMap().getABlock(x , y);
        if(!OpponentBlock.getBuilding().isEmpty()) {
            for(Human human1 : selectedWarEquipment) {
                if(human1 instanceof SiegeMachine siegeMachine) {
                    if(siegeMachine.getType().equals(SiegeType.CATAPULT)) {
                        if(GameMap.getDistanceBetweenTwoBlocks(siegeMachine.getBlock() , OpponentBlock) > siegeMachine.getRange()) {
                            continue;
                        }
                    }
                    if (siegeMachine.getType().equals(SiegeType.STABLE_CATAPULT)) {
                        if(GameMap.getDistanceBetweenTwoBlocks(siegeMachine.getBlock() , OpponentBlock) > siegeMachine.getRange()) {
                            continue;
                        }
                    }
                    OpponentBlock.getBuilding().get(0).getHit(siegeMachine.getCurrentDamage());
                }
                if (!(human1 instanceof Troop human)) {
                    continue;
                }
                if(human.getFireRange() > 1) continue;
                if(human.isThereAWay(OpponentBlock)) {
                    OpponentBlock.getBuilding().get(0).getHit(human.getCurrentDamage());
                }
            }
        }
        if(OpponentBlock.getHumans().isEmpty()) {
            return "there is no enemy in that block";
        }
        boolean flag = false;
        for(Human human1 : selectedWarEquipment) {
            if(human1 instanceof SiegeMachine siegeMachine) {
                if(siegeMachine.getType().equals(SiegeType.FIRE_XBOW)) {
                    if(GameMap.getDistanceBetweenTwoBlocks(siegeMachine.getBlock() , OpponentBlock) > siegeMachine.getRange()) {
                        continue;
                    }
                    for(Human enemy : OpponentBlock.getHumans()){
                        if(!enemy.isVisible() || enemy.getGovernment().equals(siegeMachine.getGovernment())) {
                            continue;
                        }
                        enemy.getHit(siegeMachine.getCurrentDamage());
                        flag = true;
                    }
                    continue;
                }
            }
            if(!(human1 instanceof Troop human)) {
                continue;
            }
            if(human.getFireRange() > 1) {
                if(GameMap.getDistanceBetweenTwoBlocks(human.getBlock() , OpponentBlock) > human.getFireRange()) {
                    continue;
                }
                for(Human enemy : OpponentBlock.getHumans()){
                    if(!enemy.isVisible() || enemy.getGovernment().equals(human.getGovernment())) {
                        continue;
                    }
                    enemy.getHit(human.getCurrentDamage());
                    flag = true;
                    break;
                }
                continue;
            }
            if(!human.isThereAWay(OpponentBlock)) {
                continue;
            }
            if(human.getFireRange() <= 1) {
                human.setBlock(OpponentBlock);
                for(Human enemy : OpponentBlock.getHumans()) {
                    if(!enemy.isVisible() || enemy.getGovernment().equals(human.getGovernment())) {
                        continue;
                    }
                    enemy.getHit(human.getCurrentDamage());
                    flag = true;
                    break;
                }
            }
        }
        //TODO make this right
        selectedWarEquipment.clear();
        if(!flag) {
            return "no troop in selected building cat commit damage!";
        }
        return "Attack implemented successfully";
    }

    private static ArrayList<Block> getEngineerTarget(String d , Block block) {
        int x = block.getLocationI();
        int y = block.getLocationJ();
        ArrayList<Block> result = new ArrayList<>();
        switch (d) {
            case "left" -> {
                for (int i = x - 3; i <= x - 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (!currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(currentGame.getMap().getABlock(i, j));
                    }
                }
            }
            case "up" -> {
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 3; j < y; j++) {
                        if (!currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(currentGame.getMap().getABlock(i, j));
                    }
                }
            }
            case "right" -> {
                for (int i = x + 1; i <= x + 3; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (!currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(currentGame.getMap().getABlock(i, j));
                    }
                }
            }

            default -> {
                for (int i = x - 3; i < x; i++) {
                    for (int j = y + 1; j <= y + 3; j++) {
                        if (!currentGame.getMap().checkBounds(i, j)) continue;
                        result.add(currentGame.getMap().getABlock(i, j));
                    }
                }
            }
        }
        return result;
    }
    public static String pourOil (Matcher matcher) {
        String direction = matcher.group("d");
        ArrayList<Block> target = getEngineerTarget(direction , selectedWarEquipment.get(0).getBlock());
        if(target.isEmpty()) {
            return "that is the corner of the map and no blocks can get hit!";
        }
        boolean flag = false;
        for(Human human : selectedWarEquipment){
            if(!(human instanceof Engineer engineer)) {
                continue;
            }
            if(!engineer.isEquippedWithOil()) continue;
            flag = true;
            for(Block block : target) {
                if(!block.getBuilding().isEmpty()) {
                    block.getBuilding().get(0).getHit(engineer.getCurrentDamage());
                    continue;
                }
                for(Human enemy : block.getHumans()) {
                    if(!human.isVisible()) {
                        if(enemy.getGovernment().equals(engineer.getGovernment())) continue;
                        if(human instanceof Troop troop && troop.getTroopType() == TroopType.ASSASSIN) {
                            enemy.getHit(engineer.getCurrentDamage());
                        }
                        continue;
                    }
                    enemy.getHit(engineer.getCurrentDamage());
                }
            }
            engineer.useOil();
        }
        if(!flag) return "There is no engineer in the selected humans";
        return "Deployed fire successfully!";
    }

    public static String deployCagedWarDog() {
        if(!(selectedBuilding instanceof CagedWarDog cagedWarDog)) {
            return "You have to choose a caged war dog first!";
        }
        cagedWarDog.process();
        return "Caged war dog deployed successfully!";
    }
    public static String addWorker(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("n"));
        if(number <= 0) {
            return "Invalid number of workers!";
        }
        if(selectedBuilding == null) return "You have to choose a building first!";
        if(selectedBuilding instanceof OilSmelter oilSmelter) {
            return employOilSmelter(oilSmelter);
        }
        if(selectedBuilding instanceof Inn inn) {
            return employInn(inn);
        }
        if(selectedBuilding instanceof Maker maker) {
            return employMaker(maker , number);
        }
        return "You have chosen the wrong building for this command!";
    } //checked
    private static String employOilSmelter(OilSmelter oilSmelter) {
        if(oilSmelter.getEngineer() != null) return "There is already an engineer in that oilSmelter!";
        for(Human human : currentGame.getCurrentGovernment().getHumans()) {
            if(human instanceof Engineer engineer && engineer.isUnemployed()) {
                engineer.setUnemployed(false);
                oilSmelter.setEngineer(engineer);
                engineer.getBlock().getHumans().remove(engineer);
                engineer.setBlock(oilSmelter.getBlock());
                engineer.setVisible(false);
                return "engineer added to OilSmelter successfully!";
            }
        }
        return "there was no available engineers!";
    } //checked

    private static String employMaker(Maker maker , int number) {
        System.out.println(maker.getNumberOfCurrentWorkers());
        System.out.println(maker.getNumberOfMaxWorkers());
        if(maker.getNumberOfCurrentWorkers() >= maker.getNumberOfMaxWorkers()) {
            return "That building has max capacity";
        }
        int unemployedCounter = 0;
        for(Human human : currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine ||
                    human instanceof LadderMan || human instanceof Tunneler) {
                continue;
            }
            if (!human.isUnemployed()) continue;
            unemployedCounter++;
        }
        if(unemployedCounter < number) {
            return "there is not enough people to hire!";
        }
        int counter = Math.min(maker.getNumberOfMaxWorkers() - maker.getNumberOfCurrentWorkers() , number);
        for(Human human : currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine ||
                    human instanceof LadderMan || human instanceof Tunneler){
                continue;
            }
            if(!human.isUnemployed()) continue;
            human.setUnemployed(false);
            human.setVisible(false);
            human.getBlock().getHumans().remove(human);
            human.setBlock(maker.getBlock());
            maker.addWorker();
            counter--;
            if(counter == 0) break;
        }
        return "The building was equipped with workers successfully";
    } //checked

    private static String employInn(Inn inn) {
        if(inn.getNumberOfWorkers() == 1) {
            return "That building has max capacity";
        }
        for(Human human : currentGame.getCurrentGovernment().getHumans()) {
            if (human instanceof Troop || human instanceof SiegeMachine ||
                    human instanceof LadderMan || human instanceof Tunneler){
                continue;
            }
            if(!human.isUnemployed()) continue;
            human.setVisible(false);
            human.getBlock().getHumans().remove(human);
            human.setBlock(inn.getBlock());
            human.setUnemployed(false);
            inn.addWorkers(1);
            return "Inn was equipped with workers successfully";
        }
        return "there is no people to hire";
    } //checked

    public static String digTunnel (Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if(currentGame.getMap().checkBounds(y , x)) {
            return "please enter a point in the map";
        }
        Block target = currentGame.getMap().getABlock(y , x);
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if(target.getBuilding().isEmpty()) {
            return "there is no building there!";
        }
        BuildingType buildingType = target.getBuilding().get(0).getBuildingType();
        ArrayList<BuildingType> goodBuildingType = new ArrayList<>(Arrays.asList(DefenciveBuildingType.HIGH_WALL , DefenciveBuildingType.LOW_WALL , DefenciveBuildingType.DEFENCIVE_TURRET , DefenciveBuildingType.LOOKOUT_TOWER , DefenciveBuildingType.PERIMETER_TOWER));
        if(!goodBuildingType.contains(buildingType)) {
            return "You can only use tunnelers on walls and towers!";
        }
        boolean flag = false;
        for(Human human : selectedWarEquipment) {
            if(!(human instanceof Tunneler tunneler)) continue;
            if(!tunneler.isThereAWay(target)) continue;
            flag = true;
            target.getBuilding().get(0).getHit(300000);
            tunneler.die();
            break;
        }
        if(!flag) {
            return "The selected wall could not be destroyed by your unit!";
        }
        return "the wall was destroyed successfully!";
    }

    private static boolean thereIsNoTroopToDig(Block target) {
        for(Human human : selectedWarEquipment) {
            if(!(human instanceof Troop troop)) {
                continue;
            }
            if(!troop.getCanDig()) {
                continue;
            }
            if(!troop.isThereAWay(target)) {
                continue;
            }
            return false;
        }
        return true;
    }
    public static String digDitch(Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if(currentGame.getMap().checkBounds(x , y)) {
            return "please enter a point in the map";
        }
        Block target = currentGame.getMap().getABlock(x , y);
        if(isUnavailable(target)) {
            return "You can not dig a ditch on that block!";
        }
        if(thereIsNoTroopToDig(target)) {
            return "None of the selected people could dig that place!";
        }
        target.setBlockType(BlockType.DITCH);
        return "Ditch was successfully dug!";
    }
    private static boolean isUnavailable(Block block) {
        ArrayList<BlockType> goodBlockTypes = new ArrayList<>(Arrays.asList(BlockType.GROUND, BlockType.STONY_GROUND, BlockType.GRASS, BlockType.MEADOW,
                BlockType.DENSE_MEADOW , BlockType.BEACH , BlockType.PLAIN));

        return !goodBlockTypes.contains(block.getBlockType());
    }
    public static String buildEquipmentOnTower(Matcher matcher) {
        if(!(selectedBuilding.getBuildingType().equals(GeneralBuildingsType.SIEGE_TENT)))
            return "You have ot choose a siege tent first!";
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(!currentGame.getMap().checkBounds(y , x)) {
            return "please enter a point in the map";
        }
        Block place = currentGame.getMap().getABlock(y , x);
        String type = matcher.group("type").replaceAll("\"", "");
        if(!Dictionaries.siegeMachineDictionary.containsKey(matcher.group("type"))) {
            return "siege tent can not build that!";
        }
        SiegeType type1 = Dictionaries.siegeMachineDictionary.get(type);
        if(place.getBuilding().isEmpty() || (
                !place.getBuilding().get(0).getBuildingType().equals(DefenciveBuildingType.SQUARE_TOWER) &&
                !place.getBuilding().get(0).getBuildingType().equals(DefenciveBuildingType.CIRCLE_TOWER))) {
            return "there is no useful tower on that block!";
        }
        Government government = currentGame.getCurrentGovernment();
        if(type1.getPrice() > Resources.GOLD.getAmount(government)) {
            return "You don't have enough money!";
        }
        int numberOfEngineer = 0;
        for(Human human : government.getHumans()) {
            if(human instanceof Engineer) {
                numberOfEngineer++;
            }
        }
        if(type1.getNumberOfEngineer() > numberOfEngineer)
        {
            return "there is not enough engineer to build!";
        }
        int amount = type1.getNumberOfEngineer();
        int size = government.getHumans().size();
        for (int i = size-1; i > -1 ; i--) {
            Human human = government.getHumans().get(i);
            if (human instanceof Engineer engineer && engineer.isUnemployed()) {
                amount--;
                engineer.die();
            }
            if (amount == 0) break;
        }
        Resources.GOLD.use(type1.getPrice() , government);
        type1.creator(place , government);
        DefenciveBuilding defenciveBuilding = (DefenciveBuilding) place.getBuilding().get(0);
        defenciveBuilding.addHuman(place.getHumans().get(place.getHumans().size() - 1));
        return "The equipment was built successfully!";
    } //checked
    public static String putLadder(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(currentGame.getMap().checkBounds(y , x)) {
            return "please enter a point in the map";
        }
        Block target = currentGame.getMap().getABlock(y , x);
        if(target.getBuilding().isEmpty()) {
            return "there is no building there!";
        }
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        BuildingType buildingType = target.getBuilding().get(0).getBuildingType();
        if(!(buildingType.equals(DefenciveBuildingType.HIGH_WALL) || buildingType.equals(DefenciveBuildingType.LOW_WALL))) {
            return "You can only use ladder man on walls!";
        }
        boolean flag = false;
        for(Human human : selectedWarEquipment) {
            if(!(human instanceof LadderMan ladderMan)) continue;
            if(!ladderMan.isThereAWay(target)) continue;
            flag = true;
            if(target.getBuilding().get(0) instanceof DefenciveBuilding defenciveBuilding) {
                defenciveBuilding.setHasLadder(true);
                ladderMan.die();
            }
            break;
        }
        if(!flag) {
            return "The selected wall could not be laddered on by your unit!";
        }
        return "the wall was laddered on successfully!";
    }
    public static String buildEquipment (Matcher matcher) {
        if(selectedBuilding == null || !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.SIEGE_TENT))
            return "You have to choose a siege tent first!";
        String type = matcher.group("type").replaceAll("\"", "");
        if(!Dictionaries.siegeMachineDictionary.containsKey(type)) {
            return "siege tent can not build that!";
        }
        SiegeType type1 = Dictionaries.siegeMachineDictionary.get(type);

        Government government = selectedBuilding.getGovernment();
        int numberOfEngineer = 0;
        for(Human human : government.getHumans()) {
            if(human instanceof Engineer) {
                numberOfEngineer++;
            }
        }
        int amount = type1.getNumberOfEngineer();
        if(amount > numberOfEngineer)
        {
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
        if(type1.getPrice() > Resources.GOLD.getAmount(government)) {
            return "You don't have enough money!";
        }
        Resources.GOLD.use(type1.getPrice() , government);
        type1.creator(selectedBuilding.getBlock() , government);

        return "The equipment was built successfully!";
    } //checked
    private static Block keepFinder() {
        for(Building building : currentGame.getCurrentGovernment().getBuildings()) {
            if(building.getBuildingType().equals(GateType.KEEP)) {
                return building.getBlock();
            }
        }
        return null;
    }
    public static String disband(){
        if(selectedWarEquipment.isEmpty()) {
            return "You have not selected any unit";
        }
        Government government = currentGame.getCurrentGovernment();
        for(Human human : selectedWarEquipment) {
            if(human instanceof SiegeMachine siegeMachine) {
                for (int i = 0; i < siegeMachine.getNumberOfEngineers(); i++) {
                    government.getHumans().add(new Human(keepFinder() , government));
                }
                human.die();
                continue;
            }
            government.getHumans().add(new Human(keepFinder() , government));
            human.die();
        }
        return "Unit disbanded successfully!";
    }

    public static String fillDitch(Matcher matcher) {
        int y = Integer.parseInt(matcher.group("y"));
        int x = Integer.parseInt(matcher.group("x"));
        if(currentGame.getMap().checkBounds(x , y)) {
            return "please enter a point in the map";
        }
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        Block target = currentGame.getMap().getABlock(x , y);
        if(!target.getBlockType().equals(BlockType.DITCH)) {
            return "that block is not a ditch";
        }
        if(thereIsNoTroopToDig(target)) {
            return "None of the selected people could dig that place!";
        }
        target.setBlockType(BlockType.GROUND);
        return "Ditch was successfully dug!";
    }


    public static String sellItems(Matcher matcher){
        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");

        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, selling failed";


        if (finalAmount <= 0) return "Invalid amount, selling failed";

        double finalPrice = (currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount)*4)/5;

        if (finalPrice == 0) return "The product you are looking for does not exit, selling failed";
        if (!capacityCheckerForSelling(item, finalAmount)) return "You do not have enough to sell, selling failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                put(Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(Resources.GOLD) + finalPrice);
        changeStorage(item, finalAmount, -1);
        return "Item sold successfully";
    }

    public static String buyItems(Matcher matcher){

        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");
        item = item.replaceAll("\"", "");

        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, buying failed";

        if (finalAmount <= 0) return "Invalid amount, buying failed";

        double finalPrice = currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount);

        if (finalPrice == 0 && !item.equals("gold")) return "The product you are looking for does not exit, buying failed";

        if (finalPrice > currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD))
            return "You do not have enough gold to buy this item, buying failed";

        if (!capacityCheckerForBuying(item, finalAmount)) return "You do not have enough capacity to buy this item, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                put(Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(Resources.GOLD) - finalPrice);

        changeStorage(item, finalAmount, 1);
        return "Item purchased successfully";
    }

    private static boolean capacityCheckerForBuying(String name, int amount){
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().getResourcesMaxCapacity()
                        >= amount + currentGame.getCurrentGovernment().getStorageDepartment().resourcesOccupied())
                    return true;
            }
        }

        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsMaxCapacity()
                        >= amount + currentGame.getCurrentGovernment().getStorageDepartment().weaponsOccupied())
                    return true;
            }
        }

        for (Food food : Food.values()){
            if (food.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().getFoodMaxCapacity()
                        >= amount + currentGame.getCurrentGovernment().getStorageDepartment().foodOccupied())
                    return true;
            }
        }
        return false;
    }

    private static boolean capacityCheckerForSelling(String name, int amount){
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) - amount >= 0)
                    return true;
            }
        }

        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.get(weapons) - amount >= 0)
                    return true;
            }
        }

        for (Food food : Food.values()){
            if (food.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(food) - amount >= 0)
                    return true;
            }
        }
        return false;
    }


    private static void changeStorage(String name, int amount, int coefficient){
        amount *= coefficient;
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)) {
                currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        put(resources, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                                get(resources) + amount);
                return;
            }
        }

        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)) {
                currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.
                        put(weapons, currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.
                                get(weapons) + amount);
                return;
            }
        }

        for (Food food : Food.values()){
            if (food.getName().equals(name)) {
                currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.
                        put(food, currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.
                                get(food) + amount);
                return;
            }
        }
    }

    private static String getResourcesPriceList(){
        String finalString = "";
        for (Map.Entry<Resources, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getResourcesStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " +
                    entry.getKey().getPrice() + " Selling Price: " + (entry.getKey().getPrice()*4)/5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    private static String getFoodPriceList(){
        String finalString = "";
        for (Map.Entry<Food, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getFoodStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " +
                    entry.getKey().getPrice() + " Selling Price: " + (entry.getKey().getPrice()*4)/5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    private static String getWeaponPriceList(){
        String finalString = "";
        for (Map.Entry<Weapons, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " +
                    entry.getKey().getPrice() + " Selling Price: " + (entry.getKey().getPrice()*4)/5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    public static String showPriceList(){
        String finalString = "";
        finalString = finalString.concat(getResourcesPriceList());
        finalString = finalString.concat(getFoodPriceList());
        finalString = finalString.concat(getWeaponPriceList());

        return finalString.substring(0 , finalString.length() - 2);
    }

    public static String foodResource(){
        String finalString = "";
        for (Map.Entry<Food, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getFoodStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() +  " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    public static String Resource(){
        String finalString = "";
        for (Map.Entry<Resources, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getResourcesStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() +  " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    public static String weaponsResource(){
        String finalString = "";
        for (Map.Entry<Weapons, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() +  " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    public static String showTradeDetails(Trade trade){
        String finalString = "";
        finalString = finalString.concat("Sender: " + trade.getSender().getName() + "  ");
        finalString = finalString.concat("Resource offered: " + trade.getOfferedNumber() + " " + trade.getOffered() + "  ");
        if (trade.getOffered() == null)
            finalString = finalString.concat("Resource wanted: Nothing  ");
        else
            finalString = finalString.concat("Resource wanted: " + trade.getWantedNumber() + trade.getWanted());
        finalString = finalString.concat("Message: " + trade.getMessage());
        return finalString;
    }

    public static String showTradeNotification(){
        String finalString = "";

        if (currentGame.getCurrentGovernment().getOwner().getNotificationsList().isEmpty())
            return "You have no new requests";

        while (currentGame.getCurrentGovernment().getOwner().getNotificationsList().size() > 0){
            Trade trade = currentGame.getCurrentGovernment().getOwner().getNotificationsList().poll();
            if (trade.isAccepted())
                finalString = finalString.concat("Trade Accepted: " + showTradeDetails(trade) + " Acceptor: " + trade.getReceiver().getName() + "\n");
            else
                finalString = finalString.concat("New Request: " + showTradeDetails(trade) + "\n");
        }
        return finalString;
    }

    public static String trade(Matcher matcher){
        String message = matcher.group("message");
        String receiverName = matcher.group("receiver");
        String wantedName = matcher.group("wanted");
        String offeredName = matcher.group("offered");
        int wantedAmount = Integer.parseInt(matcher.group("wantedAmount"));
        int offeredAmount = Integer.parseInt(matcher.group("offeredAmount"));

        if (wantedAmount < 0 || offeredAmount < 0) return "Invalid amount, trading failed";

        if (!wantedName.equals("null") || !checkResourceName(wantedName))
            return "The resource you want does not exit, trading failed";

        if (!offeredName.equals("null") || !checkResourceName(offeredName))
            return "The resource you are offering does not exit, trading failed";

        if (wantedName.equals("null") && wantedAmount != 0)
            return "This is a donation and you cannot set an amount, trade failed";

        if (offeredName.equals("null") && offeredAmount != 0)
            return "You are begging people so you cannot set an amount, trade failed";

        if (checkEnoughStorageForTrade(offeredName, offeredAmount))
            return "You do not have enough resources to make this trade, trading failed";

        if (!receiverName.equals("all") && getPlayerByUsername(receiverName) == null)
            return "This player does not exit in the game, trading failed";

        tradeWork(wantedName, wantedAmount, offeredName, offeredAmount, receiverName);
        return null;
    }

    private static void tradeWork(String wanted, int wantedAmount, String offered, int offeredAmount, String receiverName){
        Resources wantedResource = null;
        Resources offeredResource = null;
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(wanted)){
                wantedResource = resources;
            }
            if (resources.getName().equals(offered)){
                offeredResource = resources;
            }
        }


        Trade trade = new Trade(wantedResource, wantedAmount, offeredResource, offeredAmount);
        currentGame.addAllTrades(trade);
        if (!receiverName.equals("all")){
            getPlayerByUsername(receiverName).addToMyTrades(trade);
            getPlayerByUsername(receiverName).putNotificationList(trade);
        } else {
            for (User player : currentGame.getPlayers()){
                player.addToMyTrades(trade);
                player.putNotificationList(trade);
            }
        }

    }

    private static User getPlayerByUsername(String username){
        for (User user : currentGame.getPlayers()){
            if (user.getName().equals(username))
                return user;
        }
        return null;
    }

    private static boolean checkResourceName(String name){
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name))
                return true;
        }
        return false;
    }

    private static boolean checkEnoughStorageForTrade(String name, int amount){
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)){
                if ((currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(resources) - amount) < 0){
                    return false;
                }
            }
        }
        return true;
    }

    public static String showTradeList(){
        String finalString = "";
        int counter = 1;
        if (currentGame.getCurrentGovernment().getOwner().getMyTrades().isEmpty())
            return "You have no offers";

        for (Trade trade : currentGame.getCurrentGovernment().getOwner().getMyTrades()){
            if (!trade.isAccepted()) {
                finalString = finalString.concat(counter + ". ");
                counter++;
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
            }
        }
        return finalString;
    }

    public static String acceptTradeItem(Matcher matcher){
        String id = matcher.group("id");
        String message = matcher.group("message");
        if (id.isEmpty() || message.isEmpty()) return "Required field is empty, accepting trade failed";
        int id1 = Integer.parseInt(matcher.group("id"));

        int tradesToBeAccepted = 0;
        for (Trade trade : currentGame.getCurrentGovernment().getOwner().getMyTrades()){
            if (!trade.isAccepted())
                tradesToBeAccepted++;
        }

        if (id1 > tradesToBeAccepted)
            return "Invalid id number, accepting trade failed";

        for (Trade trade : currentGame.getCurrentGovernment().getOwner().getMyTrades()){
            if (!trade.isAccepted() && id1 == 0){
                Resources wanted = trade.getWanted();
                int wantedAmount = trade.getWantedNumber();

                if (wantedAmount > currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(wanted))
                    return "You do not have enough resources to accept this trade, accepting trade failed";
                AcceptingTradeWork(trade, message);
                break;
            } else if (!trade.isAccepted()){
                id1--;
            }
        }
        return "Trade finished successfully";
    }

    public static void AcceptingTradeWork(Trade trade, String message){
        trade.setMessage(message);
        trade.setAccepted(true);
        trade.setReceiver(currentGame.getCurrentGovernment().getOwner());
        int offeredAmount = trade.getOfferedNumber();
        String offeredName = trade.getOffered().getName();
        int wantedAmount = trade.getWantedNumber();
        String wantedName = trade.getWanted().getName();

        for (Resources resources : Resources.values()){
            if (resources.getName().equals(offeredName)){
                currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        put(resources, currentGame.getCurrentGovernment().
                                getStorageDepartment().resourcesStorage.get(resources) + offeredAmount);
                if (currentGame.getCurrentGovernment().getStorageDepartment().
                        resourcesOccupied() > currentGame.getCurrentGovernment().
                        getStorageDepartment().getResourcesMaxCapacity())
                    currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                            put(resources, (double) currentGame.getCurrentGovernment().getStorageDepartment()
                                    .getResourcesMaxCapacity());
                trade.getSender().getGovernment().getStorageDepartment().resourcesStorage.
                        put(resources, trade.getSender().getGovernment().getStorageDepartment().
                                resourcesStorage.get(resources) - offeredAmount);

            }
        }

        for (Resources resources : Resources.values()){
            if (resources.getName().equals(wantedName)){
                currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        put(resources, currentGame.getCurrentGovernment().getStorageDepartment().
                                resourcesStorage.get(resources) - wantedAmount);
                trade.getSender().getGovernment().getStorageDepartment().
                        resourcesStorage.put(resources, trade.getSender().getGovernment().
                                getStorageDepartment().resourcesStorage.get(resources) + wantedAmount);
                if (trade.getSender().getGovernment().getStorageDepartment().
                        resourcesOccupied() > trade.getSender().getGovernment().
                        getStorageDepartment().getResourcesMaxCapacity())
                    trade.getSender().getGovernment().getStorageDepartment().
                            resourcesStorage.put(resources, (double) trade.getSender().getGovernment().
                                    getStorageDepartment().getResourcesMaxCapacity());
            }
        }


    }

    public static String showTradeHistory(){
        String finalString = "";
        finalString = finalString.concat("Trades Sent: " + "\n");
        for (Trade trade : currentGame.getAllTrades()){
            if (trade.getSender().getName().equals(currentGame.getCurrentGovernment().getOwner().getName()))
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
        }

        finalString = finalString.concat("Trades Accepted: " + "\n");
        for (Trade trade : currentGame.getCurrentGovernment().getOwner().getMyTrades()){
            if (trade.isAccepted() && trade.getReceiver().getName().equals(currentGame.getCurrentGovernment().getOwner().getName()))
                finalString = finalString.concat(showTradeDetails(trade) + "\n");
        }
        return finalString;
    }

    public static String setTroopState(Matcher matcher){
        String state = matcher.group("state");
        if (!state.equals("defensive") && !state.equals("standing") && !state.equals("aggressive"))
            return "Invalid state, setting state failed";
        for (Human human : selectedWarEquipment){
            if (human instanceof LadderMan || human instanceof Tunneler || human instanceof SiegeMachine){

            } else {
                switch (state){
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


}