package controller;

import model.Game;
import model.building.Building;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.forces.human.Human;
import view.gameMenu.GameMenu;
import view.gameMenu.MapMenu;
import view.gameMenu.ShopMenu;
import view.gameMenu.TradeMenu;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController {
    private static Game currentGame;
    private Building selectedBuilding;
    private static ArrayList<Human> selectedHumans = new ArrayList<>();

    public static String run(){
        while (true) {
            String response = GameMenu.run();
            if (response.equals("shop menu"))
                ShopMenu.run();
            else if (response.equals("trade menu"))
                TradeMenu.run();
            else if (response.equals("map menu"))
                MapMenu.run();
            else if (response.equals("back"))
                return "back";
        }
    }

    public static Game getGame() {
        return currentGame;
    }

    public static void setGame(Game game) {
        currentGame = game;
    }

    private void setCurrentBuilding(Building currentBuilding) {
        this.selectedBuilding = currentBuilding;
    }

    public Building getCurrentBuilding() {
        return selectedBuilding;
    }

    private String deselectCurrentBuilding () { return null;}

    private static String deselectHumans () {
        selectedHumans.clear();
        return null;
    }

    public static ArrayList<Human> getSelectedHumans() {
        return selectedHumans;
    }

    public static void setSelectedHumans(ArrayList<Human> selectedHumans) {
        GameController.selectedHumans = selectedHumans;
    }

    public static String showMiniMap () {
        return null;
    }

    public static String moveMiniMap (Matcher matcher) {
        return null;
    }

    public static String getBlockDetails (Matcher matcher) {
        return null;
    }

    public static String showPopularity () {
        return null;
    }

    public static String showPopularityFactors () {
        return null;

    }

    public static String showFoodList () {
        //check storages
        return null;

    }

    public static String showFoodRate(Matcher matcher) {
        return null;
    }
    public static String taxFoodRate(Matcher matcher) {
        return null;
    }
    public static String fearFoodRate(Matcher matcher) {
        return null;
    }
    public static void changeResourceAmount (Resources resource, int amount) {}



    public static String setTaxRate (Matcher matcher) {
        return null;

    }

    public static String setFearRate (Matcher matcher) {
        return null;
    }

    public static String setFoodRate(Matcher matcher){
        return null;
    }

    public static String createUnit (Matcher matcher) {
        return null;
    }

    public static String repairCurrentBuilding () {
        return null;
    }
    public static String selectBuilding (Matcher matcher) {
        return null;
    }


    public static String selectUnits (Matcher matcher) {
        return null;
    }

    public static String moveSelectedUnits (Matcher matcher) {
        return null;
    }

    public static String patrolUnit(Matcher matcher) { return null;}

    public static String changeSelectedUnitState (Matcher matcher) {
        return null;
    }

    public static String attackTheBlock (Matcher matcher) {
        return null;
    }

    public static String arialAttack(Matcher matcher) { return null;}

    public static String pourOil (Matcher matcher) {
        return null;
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
        int finalAmount = 0;

        if (matcher.group("amount").isEmpty() && (matcher.group("amount1").isEmpty()))
            return "The required field is empty, selling failed";

        finalAmount = defineAmount(matcher);

        String item;
        item = defineItem(matcher);

        if (item.isEmpty()) return "The required field is empty, selling failed";

        if (finalAmount <= 0) return "Invalid amount, selling failed";

        int finalPrice = (currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount)*4)/5;

        if (finalPrice == 0) return "The product you are looking for does not exit, selling failed";
        if (!capacityChecker(item, finalAmount, -1)) return "You do not have enough to sell, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put
                (Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage
                        .get(Resources.GOLD) + finalPrice);
        changeStorage(item, finalAmount, -1);
        return "Item sold successfully";


    }

    public static String defineItem(Matcher matcher){
        String item;
        if (!matcher.group("item").isEmpty())
            item = matcher.group("item");
        else
            item = matcher.group("item1");
        return item;
    }

    public static int defineAmount(Matcher matcher){
        int finalAmount = 0;
        if (!matcher.group("amount").isEmpty())
            finalAmount = Integer.parseInt(matcher.group("amount"));
        else
            finalAmount = Integer.parseInt(matcher.group("amount1"));
        return finalAmount;
    }

    public static String buyItems(Matcher matcher){
        int finalAmount = 0;

        if (matcher.group("amount").isEmpty() && (matcher.group("amount1").isEmpty()))
            return "The required field is empty, buying failed";

        finalAmount = defineAmount(matcher);

        String item;
        item = defineItem(matcher);

        if (item.isEmpty()) return "The required field is empty, buying failed";

        if (finalAmount <= 0) return "Invalid amount, buying failed";

        int finalPrice = currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount);

        if (finalPrice == 0) return "The product you are looking for does not exit, buying failed";

        if (finalPrice > currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD))
            return "You do not have enough gold to buy this item, buying failed";

        if (!capacityChecker(item, finalAmount, 1)) return "You do not have enough capacity to buy this item, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put
                (Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage
                        .get(Resources.GOLD) - finalPrice);

        changeStorage(item, finalAmount, 1);
        return "Item purchased successfully";
    }

    public static boolean capacityChecker(String name, int amount, int coefficient){
        amount *= coefficient;
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


    public static String showPriceList(){
        String finalString = new String();
        finalString = finalString.concat(currentGame.getCurrentGovernment().
                getStorageDepartment().getResourcesPriceList());
        finalString = finalString.concat(currentGame.getCurrentGovernment().
                getStorageDepartment().getFoodPriceList());
        finalString = finalString.concat(currentGame.getCurrentGovernment().
                getStorageDepartment().getWeaponPriceList());
        return finalString;
    }

    public static String trade(Matcher matcher){
        return null;
    }

    public static String showTradeList(){
        return null;
    }

    public static String acceptTradeItem(Matcher matcher){
        return null;
    }

    public static String showTradeHistory(){ return null; }

    public static String dropStairs(Matcher matcher) { return null;}
    public static String EngineerGetOil(Matcher matcher) {return null;}
    public static String digDitch(Matcher matcher) {return null;}
    public static String fillDitch(Matcher matcher) {return null;}

    public static String enterTradeMenu(Matcher matcher) {return  null;}

}