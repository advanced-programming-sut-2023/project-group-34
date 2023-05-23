package controller;

import model.Dictionaries;
import model.Game;
import model.building.*;
import model.enums.Direction;
import model.enums.make_able.Food;
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

import java.util.*;
import java.util.regex.Matcher;

public class GameController {
    public static Game currentGame;
    public static Building selectedBuilding;
    public static ArrayList<Human> selectedWarEquipment = new ArrayList<>();
    
    public static String run () {
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
    
    public static Game getGame () {
        return currentGame;
    }
    
    
    public static String stopSelectedUnits () {
        if (selectedWarEquipment.size() == 0) return "No soldiers Selected!";
        for (Human human : selectedWarEquipment) {
            human.stop();
        }
        return "All soldiers stopped!";
    }
    
    public static String selectBuilding (Matcher matcher) {
        int xLocation = Integer.parseInt(matcher.group("x"));
        int yLocation = Integer.parseInt(matcher.group("y"));
        
        if (xLocation < 0 || yLocation < 0 || xLocation > 399 || yLocation > 399)
            return "Invalid coordinates, selecting building failed";
        
        if (currentGame.getMap().getABlock(yLocation, xLocation).getBuilding() == null ||
                currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().isEmpty())
            return "There is no building in this block, selecting building failed";
        
        if (!currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().get(0).getGovernment().getOwner().getName().
                equals(currentGame.getCurrentGovernment().getOwner().getName()))
            return "You do not own this building, selecting building failed";
        
        selectedBuilding = currentGame.getMap().getABlock(yLocation, xLocation).getBuilding().get(0);
        return "Building selected successfully";
    }
    
    public static String deselectBuilding () {
        if (selectedBuilding == null) return "You have no building selected, deselecting building failed";
        else selectedBuilding = null;
        return "Building deselected successfully";
    }
    
    public static String moveUnit (Matcher matcher) {
        if (selectedWarEquipment.size() == 0) return "Select some units to move!";
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!currentGame.getMap().checkBounds(y, x)) return "Out of bounds!";
        boolean someoneCouldntGoThere = false;
        boolean someoneCouldGoThere = false;
        for (Human human : selectedWarEquipment) {
            Router router = new Router(currentGame.getMap(), human.getBlock(), currentGame.getMap().getABlock(y, x), human);
            ArrayList<Block> route = router.findBestRoute();
            if (route == null) someoneCouldntGoThere = true;
            else {
                if (route.size() == 0) return "You are already in the destination given!";
                someoneCouldGoThere = true;
                human.setDestination(currentGame.getMap().getABlock(y, x));
                human.applyMoves();
            }
        }
        if (!someoneCouldGoThere) return "Couldn't move selected units!";
        if (someoneCouldntGoThere) return "Some of selected troops couldn't go there!";
        return "Units are moving successfully!";
    }
    
    public static String deployBatteringRam (Matcher matcher) {
        SiegeMachine batteringRam = null;
        for (Human human : selectedWarEquipment) {
            if ((human instanceof SiegeMachine siegeMachine) && siegeMachine.getType().equals(SiegeType.BATTERING_RAM)) {
                batteringRam = siegeMachine;
            }
        }
        if (batteringRam == null) {
            return "You have not selected a battering ram!";
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!currentGame.getMap().checkBounds(y, x)) return "out of bound!";
        Block block = currentGame.getMap().getABlock(y, x);
        if (block.getBuilding().isEmpty() || !(block.getBuilding().get(0) instanceof Gate gate)) {
            return "ypu have not chosen a gate!";
        }
        if (!gate.getBuildingType().equals(GateType.BIG_GATE_HOUSE) && gate.getBuildingType().equals(GateType.SMALL_GATE_HOUSE)) {
            return "ypu have not chosen a gate!";
        }
        if (gate.getGovernment().equals(batteringRam.getGovernment())) return "That gate is yours!";
        if (GameMap.getDistanceBetweenTwoBlocks(batteringRam.getBlock(), gate.getBlock()) > batteringRam.getSpeed()) {
            return "that gate is out of range!";
        }
        batteringRam.getHit(300000);
        gate.getHit(batteringRam.getCurrentDamage());
        return "Gate was hit successfully!";
    }
    
    public static String setMapLocation (int x, int y) {
        if (!(currentGame.getMap().checkBounds(y, x) && currentGame.getMap().checkBounds(y + currentGame.getMap().minimapSize,
                x + currentGame.getMap().minimapSize)))
            return "Wrong coordinates";
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
        for (int i = 0; i < currentGame.getMap().minimapSize; i++) {
            for (int j = 0; j < currentGame.getMap().minimapSize; j++) {
                if (map[i][j].getTroops().length != 0)
                    output.append(BackgroundColor.dictionary(map[i][j])).append("\u001B[31m").append(" S ").append("\u001B[0m");
                else if (map[i][j].getBuilding().size() != 0)
                    if (map[i][j].getBuilding().get(0).getBuildingType().equals(DeathPitType.DEATH_PIT) &&
                            !map[i][j].getBuilding().get(0).getGovernment().equals(currentGame.getCurrentGovernment()))
                        output.append(BackgroundColor.dictionary(map[i][j])).append(" ").
                                append(map[i][j].getBlockType().toString(), 0, 2).append("\u001B[0m");
                    else
                        output.append(BackgroundColor.dictionary(map[i][j])).append("\u001B[35m").append(" B ").append("\u001B[0m");
                else if (map[i][j].getBLockFiller() != null)
                    output.append(BackgroundColor.dictionary(map[i][j])).append(" T ").append("\u001B[0m");
                else {
                    String abbreviation = map[i][j].getBlockType().toString().substring(0, 2);
                    output.append(BackgroundColor.dictionary(map[i][j])).append(' ').append(abbreviation).append("\u001B[0m");
                }
                output.append(BackgroundColor.dictionary(map[i][j])).append(" ").append("\u001B[0m");
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
        if ((down != null && up != null) || (left != null && right != null))
            return "Invalid command: opposite directions used!";
        if (up != null) currentGame.getMap().moveMiniMap(Direction.NORTH, Integer.parseInt(up));
        if (down != null) currentGame.getMap().moveMiniMap(Direction.SOUTH, Integer.parseInt(down));
        if (left != null) currentGame.getMap().moveMiniMap(Direction.WEST, Integer.parseInt(left));
        if (right != null) currentGame.getMap().moveMiniMap(Direction.EAST, Integer.parseInt(right));
        return showMiniMap();
    }
    
    public static String getBlockDetails (Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return WarController.showDetails(currentGame.getMap(), x, y);
    }
    
    public static String showPopularity () {
        int totalPopularity = currentGame.getCurrentGovernment().getAccountingDepartment().getFearPopularity();
        totalPopularity += currentGame.getCurrentGovernment().getAccountingDepartment().getFoodPopularity();
        totalPopularity += currentGame.getCurrentGovernment().getAccountingDepartment().getTaxPopularity();
        totalPopularity += currentGame.getCurrentGovernment().getAccountingDepartment().getReligionPopularity();
        return "Your current popularity is: " + totalPopularity;
    }
    
    public static String showPopularityFactors () {
        String finalString = "";
        finalString = finalString.concat("Food: " +
                currentGame.getCurrentGovernment().getAccountingDepartment().getFoodPopularity() + "\n");
        finalString = finalString.concat("Fear: " +
                currentGame.getCurrentGovernment().getAccountingDepartment().getFearPopularity() + "\n");
        finalString = finalString.concat("Tax: " +
                currentGame.getCurrentGovernment().getAccountingDepartment().getTaxPopularity() + "\n");
        finalString = finalString.concat("Religion: " +
                currentGame.getCurrentGovernment().getAccountingDepartment().getReligionPopularity());
        return finalString;
    }
    
    public static String showFoodList () {
        String finalString = "";
        finalString = finalString.concat("Bread: " +
                currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(Food.BREAD) + "\n");
        finalString = finalString.concat("Meat: " +
                currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(Food.MEAT) + "\n");
        finalString = finalString.concat("Apple: " +
                currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(Food.APPLE) + "\n");
        finalString = finalString.concat("Cheese: " +
                currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(Food.CHEESE));
        return finalString;
    }
    
    public static String showFoodRate () {
        return "Your current food rate is: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFoodRate();
    }
    
    public static String showTaxRate () {
        return "Your current tax rate is: " + currentGame.getCurrentGovernment().getAccountingDepartment().getTaxRate();
    }
    
    public static String showFearRate () {
        return "Your current fear rate is: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFearRate();
    }
    
    public static String setTaxRate (Matcher matcher) {
        if (selectedBuilding == null || !(selectedBuilding.getBuildingType() == GateType.BIG_GATE_HOUSE ||
                selectedBuilding.getBuildingType() == GateType.SMALL_GATE_HOUSE ||
                selectedBuilding.getBuildingType() == GateType.KEEP)) {
            return "You have not selected the right building";
        }
        int taxRate = Integer.parseInt(matcher.group("taxRate"));
        if (taxRate < -3 || taxRate > 8) return "Invalid tax rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setTaxRate(taxRate);
        return "Tax rate successfully";
    }
    
    public static String setFearRate (Matcher matcher) {
        int fearRate = Integer.parseInt(matcher.group("fearRate"));
        if (fearRate < -5 || fearRate > 5) return "Invalid fear rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFearRate(fearRate);
        return "Fear rate set successfully";
    }
    
    public static String setFoodRate (Matcher matcher) {
        if (selectedBuilding == null || !selectedBuilding.getBuildingType().equals(GeneralBuildingsType.FOOD_STORAGE))
            return "you have not chosen a food storage";
        int foodRate = Integer.parseInt(matcher.group("foodRate"));
        if (foodRate < -2 || foodRate > 2) return "Invalid food rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFoodRate(foodRate);
        return "Food rate set successfully";
    }
    
    
    public static String nextTurn () {
        int index = currentGame.getPlayers().indexOf(currentGame.getCurrentGovernment().getOwner());
        if (index == -1) return "ERROR in next turn";
        int numberOfPlayers = currentGame.getPlayers().size();
        currentGame.setCurrentGovernment(currentGame.getPlayers().get((index + 1) % numberOfPlayers).getGovernment());
        currentGame.getCurrentGovernment().getAccountingDepartment().nextTurnForThisUser();
        selectedBuilding = null;
        selectedWarEquipment.clear();
        return "next turn done successfully";
    }
    
    public static String buildEquipmentOnTower (Matcher matcher) {
        if (!(selectedBuilding.getBuildingType().equals(GeneralBuildingsType.SIEGE_TENT)))
            return "You have ot choose a siege tent first!";
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (!currentGame.getMap().checkBounds(y, x)) {
            return "please enter a point in the map";
        }
        Block place = currentGame.getMap().getABlock(y, x);
        String type = matcher.group("type").replaceAll("\"", "");
        if (!Dictionaries.siegeMachineDictionary.containsKey(matcher.group("type"))) {
            return "siege tent can not build that!";
        }
        SiegeType type1 = Dictionaries.siegeMachineDictionary.get(type);
        if (place.getBuilding().isEmpty() || (!place.getBuilding().get(0).getBuildingType().equals(DefenciveBuildingType.SQUARE_TOWER) &&
                !place.getBuilding().get(0).getBuildingType().equals(DefenciveBuildingType.CIRCLE_TOWER))) {
            return "there is no useful tower on that block!";
        }
        Government government = currentGame.getCurrentGovernment();
        if (type1.getPrice() > Resources.GOLD.getAmount(government)) {
            return "You don't have enough money!";
        }
        int numberOfEngineer = 0;
        for (Human human : government.getHumans()) {
            if (human instanceof Engineer) {
                numberOfEngineer++;
            }
        }
        if (type1.getNumberOfEngineer() > numberOfEngineer) {
            return "there is not enough engineer to build!";
        }
        int amount = type1.getNumberOfEngineer();
        int size = government.getHumans().size();
        for (int i = size - 1; i > -1; i--) {
            Human human = government.getHumans().get(i);
            if (human instanceof Engineer engineer && engineer.isUnemployed()) {
                amount--;
                engineer.die();
            }
            if (amount == 0) break;
        }
        Resources.GOLD.use(type1.getPrice(), government);
        type1.creator(place, government);
        DefenciveBuilding defenciveBuilding = (DefenciveBuilding) place.getBuilding().get(0);
        defenciveBuilding.addHuman(place.getHumans().get(place.getHumans().size() - 1));
        return "The equipment was built successfully!";
    }
    
    
    public static String foodResource () {
        String finalString = "";
        for (Map.Entry<Food, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getFoodStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    public static String Resource () {
        String finalString = "";
        for (Map.Entry<Resources, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getResourcesStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    public static String weaponsResource () {
        String finalString = "";
        for (Map.Entry<Weapons, Double> entry : currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    public static User getPlayerByUsername (String username) {
        for (User user : currentGame.getPlayers()) {
            if (user.getName().equals(username)) return user;
        }
        return null;
    }
    
    public static boolean checkResourceName (String name) {
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(name)) return true;
        }
        return false;
    }
    
    public static String populationInformation () {
        String finalString = "";
        finalString = finalString.concat("Population: " + currentGame.getCurrentGovernment().getPopulation() + "\n");
        finalString = finalString.concat("Max Population: " + currentGame.getCurrentGovernment().getMaxPopulation() + "\n");
        finalString = finalString.concat("Unemployed: " + TroopsController.findUnemployed().size());
        return finalString;
    }
    
    
}