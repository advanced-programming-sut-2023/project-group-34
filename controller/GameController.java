package controller;

import model.Game;
import model.building.Building;
import model.enums.make_able.Resources;
import model.forces.human.Human;
import model.forces.human.Troop;
import view.gameMenu.GameMenu;
import view.gameMenu.MapMenu;
import view.gameMenu.ShopMenu;
import view.gameMenu.TradeMenu;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
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
        return null;
    }

    public static String buyItems(Matcher matcher){
        return null;
    }

    public static String showPriceList(){
        return null;
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