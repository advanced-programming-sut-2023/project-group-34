package controller;

import model.Dictionaries;
import model.Game;
import model.Trade;
import model.building.*;
import model.enums.BlockFillerType;
import model.enums.Direction;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.human.*;
import model.map.GameMap;
import model.user.User;
import model.map.Block;
import view.gameMenu.GameMenu;
import view.gameMenu.MapMenu;
import view.gameMenu.ShopMenu;
import view.gameMenu.TradeMenu;
import java.util.ArrayList;
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

        if (xLocation < 1 || yLocation < 1 || xLocation > 399 || yLocation > 399)
            return "Invalid coordinates, selecting building failed";

        if (currentGame.getMap().getABlock(xLocation, yLocation).getBuilding() != null)
            return "There is no building in this block, selecting building failed";

        if (!currentGame.getMap().getABlock(xLocation, yLocation).getBuilding().get(0).getGovernment().getOwner()
                .getName().equals(currentGame.getCurrentGovernment().getOwner().getName()))
            return "You do not own this building, selecting building failed";

        selectedBuilding = currentGame.getMap().getABlock(xLocation, yLocation).getBuilding().get(0);
        return "Building selected successfully";
    }

    public static String deselectBuilding(){
        if (selectedBuilding == null) return "You have no building selected, deselecting building failed";
        else selectedBuilding = null;
        return "Building deselected successfully";
    }

    public Building getCurrentBuilding() {
        return selectedBuilding;
    }

    private String deselectCurrentBuilding () { return null;}

    private static String deselectHumans () {
        selectedWarEquipment.clear();
        return null;
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
    }
    public static String showMiniMap () {
        StringBuilder output = new StringBuilder();
        Block[][] map = currentGame.getMap().getMiniMap();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j].getTroops().length != 0) output.append("S ");
                else if (map[i][j].getBuilding().size() != 0) output.append("B ");
                else if (map[i][j].getBLockFiller() != null && !map[i][j].getBLockFiller().equals(BlockFillerType.STAIR))
                    output.append("T ");
                else output.append(map[i][j].getBlockType().toString(), 0, 2);
                output.append(' ');
            }
            output.append('\n');
        }
        return output.toString();
    }

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
    }

    public static String getBlockDetails (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xAxis"));
        int y = Integer.parseInt(matcher.group("yAxis"));
        return currentGame.getMap().showDetails(x, y);
    }

    public static String showPopularity () {
        return "Your current popularity is: " + currentGame.getCurrentGovernment().getTotalPopularity();
    }

    public static String showPopularityFactors () {
        String finalString = "";
        finalString = finalString.concat("Food: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFoodPopularity());
        finalString = finalString.concat("Fear: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFearRate());
        finalString = finalString.concat("Tax: " + currentGame.getCurrentGovernment().getAccountingDepartment().getTaxPopularity());
        finalString = finalString.concat("Religion: " + currentGame.getCurrentGovernment().getAccountingDepartment().getReligionPopularity());
        return finalString;
    }

    public static String showFoodList () {
        String finalString = "";
        finalString = finalString.concat("Bread: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.BREAD));
        finalString = finalString.concat("Meat: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.MEAT));
        finalString = finalString.concat("Apple: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.APPLE));
        finalString = finalString.concat("Cheese: " + currentGame.getCurrentGovernment()
                .getStorageDepartment().foodStorage.get(Food.CHEESE));
        return finalString;
    }

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
    public static void changeResourceAmount (Resources resource, int amount) {}



    public static String setTaxRate (Matcher matcher) {
        if(!(selectedBuilding.getBuildingType() == GateType.BIG_GATE_HOUSE || selectedBuilding.getBuildingType() == GateType.SMALL_GATE_HOUSE || selectedBuilding.getBuildingType() == GateType.KEEP)) {
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
        if(!selectedBuilding.getBuildingType().equals(GeneralBuildingsType.FOOD_STORAGE))
            return "you have not choose food storage";
        int foodRate = Integer.parseInt(matcher.group("foodRate"));
        if (foodRate < -2 || foodRate > 2) return "Invalid food rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFoodRate(foodRate);
        return "Food rate set successfully";
    }

    public static String createUnit (Matcher matcher) {
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        if(count < 1) return "Invalid count!";
        if(type.equals("engineer")) {

        }
        if(type.equals("tunneler")) {

        }
        //TODO check if space in name is ok
        if(type.equals("ladder man")) {

        }
        if(!Dictionaries.troopDictionary.containsKey(type)) {
            return "there is no such type!";
        }
        return "unit created successfully!";
    }

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
                if (!currentGame.getMap().checkBounds(i , j)){
                    continue;
                }
                for (Human warEquipment : currentGame.getMap().getABlock(i, j).getHumans()){
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
        int xLocation = Integer.parseInt(matcher.group("x"));
        int yLocation = Integer.parseInt(matcher.group("y"));
        if (!currentGame.getMap().checkBounds(xLocation , yLocation))
            return "Invalid coordinates, selecting unit failed";
        boolean flag = false;
        for (Human warEquipment: currentGame.getMap().getABlock(xLocation, yLocation).getHumans()){
            if (warEquipment.getGovernment().equals(currentGame.getCurrentGovernment())){
                flag = true;
                selectedWarEquipment.add(warEquipment);
            }
        }
        if(!flag) return "there is no troop in that block!";
        return "Troops selected successfully";
    }

    public static String deselectUnits(){
        if (selectedWarEquipment.isEmpty()) return "You have no troops selected";
        else selectedWarEquipment.clear();
        return "Troops deselected successfully";
    }

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
    }

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
    }

    public static String attackTheBlock (Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if(selectedWarEquipment.isEmpty()) {
            return "You have to choose a unit first";
        }
        if(currentGame.getMap().checkBounds(x , y)) {
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
                    OpponentBlock.getBuilding().get(0).getHit(siegeMachine.getDamage());
                }
                if (!(human1 instanceof Troop human)) {
                    continue;
                }
                if(human.getFireRange() > 1) continue;
                if(human.isThereAWay(OpponentBlock)) {
                    OpponentBlock.getBuilding().get(0).getHit(human.getDamage());
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
                    block.getBuilding().get(0).getHit(engineer.getDamage());
                    continue;
                }
                for(Human enemy : block.getHumans()) {
                    if(!human.isVisible()) {
                        if(human instanceof Troop troop && troop.getTroopType() == TroopType.ASSASSIN) {
                            enemy.getHit(engineer.getDamage());
                        }
                        continue;
                    }
                    enemy.getHit(engineer.getDamage());
                }
            }
            engineer.useOil();
        }
        if(!flag) return "There is no engineer in the selected humans";
        return "Deployed fire successfully!";
    }

    public static String digTunnel (Matcher matcher) {
        return null;
    }

    public static String buildEquipment (Matcher matcher) {
        return null;
    }

    public static String disband(){
        return null;
    }


    public static String sellItems(Matcher matcher){
        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");

        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, selling failed";


        if (finalAmount <= 0) return "Invalid amount, selling failed";

        double finalPrice = (currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount)*4)/5;

        if (finalPrice == 0) return "The product you are looking for does not exit, selling failed";
        if (!capacityCheckerForSelling(item, finalAmount)) return "You do not have enough to sell, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                put(Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(Resources.GOLD) + finalPrice);
        changeStorage(item, finalAmount, -1);
        return "Item sold successfully";
    }

    public static String buyItems(Matcher matcher){

        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");

        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, buying failed";

        if (finalAmount <= 0) return "Invalid amount, buying failed";

        double finalPrice = currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount);

        if (finalPrice == 0) return "The product you are looking for does not exit, buying failed";

        if (finalPrice > currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD))
            return "You do not have enough gold to buy this item, buying failed";

        if (!capacityCheckerForBuying(item, finalAmount)) return "You do not have enough capacity to buy this item, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                put(Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(Resources.GOLD) - finalPrice);

        changeStorage(item, finalAmount, 1);
        return "Item purchased successfully";
    }

    public static boolean capacityCheckerForBuying(String name, int amount){
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

    public static boolean capacityCheckerForSelling(String name, int amount){
        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) - amount > 0)
                    return true;
            }
        }

        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.get(weapons) - amount > 0)
                    return true;
            }
        }

        for (Food food : Food.values()){
            if (food.getName().equals(name)) {
                if (currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(food) - amount > 0)
                    return true;
            }
        }
        return false;
    }


    public static void changeStorage(String name, int amount, int coefficient){
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
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    private static String getFoodPriceList(){
        String finalString = "";
        for (Map.Entry<Food, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getFoodStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }

    private static String getWeaponPriceList(){
        String finalString = "";
        for (Map.Entry<Weapons, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsStorage().entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue() + "\n");
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

    public static String dropStairs(Matcher matcher) { return null;}
    public static String EngineerGetOil(Matcher matcher) {return null;}
    public static String digDitch(Matcher matcher) {return null;}
    public static String fillDitch(Matcher matcher) {return null;}


}