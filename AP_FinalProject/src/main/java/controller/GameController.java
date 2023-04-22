package controller;

import model.Game;
import model.Trade;
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
        return "Your current popularity is: " + currentGame.getCurrentGovernment().getAccountingDepartment()
                .addGovernmentPopularity();
    }

    public static String showPopularityFactors () {
        String finalString = new String();
        finalString = finalString.concat("Food: " + currentGame.getCurrentGovernment().getAccountingDepartment().foodPopularityAccounting());
        finalString = finalString.concat("Fear: " + currentGame.getCurrentGovernment().getAccountingDepartment().getFearRate());
        finalString = finalString.concat("Tax: " + currentGame.getCurrentGovernment().getAccountingDepartment().taxPopularityAccounting());
        finalString = finalString.concat("Religion: " + currentGame.getCurrentGovernment().getAccountingDepartment().getReligionPopularity());
        return finalString;
    }

    public static String showFoodList () {
        String finalString = new String();
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

    public static String setTaxRate (Matcher matcher) {
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
        int foodRate = Integer.parseInt(matcher.group("foodRate"));
        if (foodRate < -2 || foodRate > 2) return "Invalid food rate";
        currentGame.getCurrentGovernment().getAccountingDepartment().setFoodRate(foodRate);
        return "Food rate set successfully";
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
        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");

        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, selling failed";


        if (finalAmount <= 0) return "Invalid amount, selling failed";

        int finalPrice = (currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount)*4)/5;

        if (finalPrice == 0) return "The product you are looking for does not exit, selling failed";
        if (!capacityChecker(item, finalAmount, -1)) return "You do not have enough to sell, buying failed";

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

        int finalPrice = currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount);

        if (finalPrice == 0) return "The product you are looking for does not exit, buying failed";

        if (finalPrice > currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD))
            return "You do not have enough gold to buy this item, buying failed";

        if (!capacityChecker(item, finalAmount, 1)) return "You do not have enough capacity to buy this item, buying failed";

        currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                put(Resources.GOLD, currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.
                        get(Resources.GOLD) - finalPrice);

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

    public static String showTradeDetails(Trade trade){
        String finalString = new String();
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
        String finalString = new String();

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


        return null;
    }

    public static String showTradeList(){
        String finalString = new String();
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
        String finalString = new String();
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

    public static String enterTradeMenu(Matcher matcher) {return  null;}

}