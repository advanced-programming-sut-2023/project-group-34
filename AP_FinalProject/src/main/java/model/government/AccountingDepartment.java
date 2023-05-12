package model.government;

import controller.GameController;
import model.building.*;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.human.Engineer;
import model.human.Human;
import model.human.Troop;
import model.map.Block;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountingDepartment {
    private final Government government;
    private int foodRate;
    private int fearRate;
    private int taxRate;
    private int religionPopularity;
    private int taxPopularity;
    private int foodPopularity;
    private int fearPopularity;

    public void foodPopularityAccounting(){
        int foodPopularity1 = 0;
        foodPopularity1 = foodTypeCounter() -1;
        double foodNeeded = ((foodRate * 0.5) + 1) * government.getPopulation();
        if (foodNeeded > edibleFood()) foodRate = -2;
        foodPopularity1 = foodPopularity1 + foodRate * 4;
        foodPopularity += foodPopularity1;
        government.setTotalPopularity(government.getTotalPopularity() + foodPopularity1);
    }
    public void giveFoodToPeople(){
        double foodNeeded = ((foodRate * 0.5) + 1) * government.getPopulation();
        while (foodNeeded > 0){
            if (government.getStorageDepartment().foodStorage.get(Food.BREAD) > 0.0) {
                government.getStorageDepartment().foodStorage.put(Food.BREAD, government.getStorageDepartment().foodStorage.get(Food.BREAD) - 0.5);
                foodNeeded -= 0.5;
                continue;
            }

            if (government.getStorageDepartment().foodStorage.get(Food.MEAT) > 0.0) {
                government.getStorageDepartment().foodStorage.put(Food.MEAT, government.getStorageDepartment().foodStorage.get(Food.MEAT) - 0.5);
                foodNeeded -= 0.5;
                continue;
            }

            if (government.getStorageDepartment().foodStorage.get(Food.APPLE) > 0.0){
                government.getStorageDepartment().foodStorage.put(Food.APPLE, government.getStorageDepartment().foodStorage.get(Food.APPLE) - 0.5);
                foodNeeded -= 0.5;
                continue;
            }

            if (government.getStorageDepartment().foodStorage.get(Food.CHEESE) > 0.0){
                government.getStorageDepartment().foodStorage.put(Food.CHEESE, government.getStorageDepartment().foodStorage.get(Food.CHEESE) - 0.5);
                foodNeeded -= 0.5;
            }
        }
    }
    private int foodTypeCounter(){
        int foodCounter = 0;
        if (government.getStorageDepartment().foodStorage.get(Food.BREAD) > 0)
            foodCounter++;
        if (government.getStorageDepartment().foodStorage.get(Food.CHEESE) > 0)
            foodCounter++;
        if (government.getStorageDepartment().foodStorage.get(Food.APPLE) > 0)
            foodCounter++;
        if (government.getStorageDepartment().foodStorage.get(Food.MEAT) > 0)
            foodCounter++;
        return  foodCounter;
    }
    private double edibleFood(){
        double edibleFoodCounter = 0;
        edibleFoodCounter = edibleFoodCounter + government.getStorageDepartment().foodStorage.get(Food.BREAD);
        edibleFoodCounter = edibleFoodCounter + government.getStorageDepartment().foodStorage.get(Food.MEAT);
        edibleFoodCounter = edibleFoodCounter + government.getStorageDepartment().foodStorage.get(Food.CHEESE);
        edibleFoodCounter = edibleFoodCounter + government.getStorageDepartment().foodStorage.get(Food.APPLE);
        return  edibleFoodCounter;
    }

    private void fearPopularityAccounting(){
        int fearPopularity1 = -fearRate;
        fearPopularity += fearPopularity1;
        government.setTotalPopularity(government.getTotalPopularity() - fearPopularity1);
    }
    private void moralityAndIntegrity(){
        for (Building building : government.getBuildings()){
            if (building instanceof Maker){
                ((Maker) building).setCurrentOutputRate(fearRate);
            }
        }

        for (Human human : government.getHumans()){
            if (human instanceof Troop){
                ((Troop) human).setCurrentRate(fearRate);
            }
        }
    }

    private void religionPopularityAccounting(){
        for (Building building : government.getBuildings()){
            if (building.getBuildingType() == GeneralBuildingsType.CHURCH){
                religionPopularity++;
                government.setTotalPopularity(government.getTotalPopularity() + 1);
            } else if (building.getBuildingType() == GeneralBuildingsType.CATHEDRAL){
                religionPopularity += 2;
                government.setTotalPopularity(government.getTotalPopularity() + 2);
            }
        }
    }

    public void taxPopularityAccounting() {
        int taxPopularity1 = 0;
        if (government.getStorageDepartment().resourcesStorage.get(Resources.GOLD) == 0){
            taxPopularity += 1;
            government.setTotalPopularity(government.getTotalPopularity()+1);
        }
        if (taxRate <= 0){
            taxPopularity1 = (-2)*taxRate + 1;
        } else {
            taxPopularity1 = switch (taxRate) {
                case 1 -> -2;
                case 2 -> -4;
                case 3 -> -6;
                case 4 -> -8;
                case 5 -> -12;
                case 6 -> -16;
                case 7 -> -20;
                case 8 -> -24;
                default -> taxPopularity1;
            };
            taxPopularity += taxPopularity1;
            government.setTotalPopularity(government.getTotalPopularity()+taxPopularity1);
        }

    }

    private void getMoneyFromPeople(){
        if (taxRate <= 0) {
            double goldToBeGiven = ((-0.2) * taxRate + 0.4) * government.getPopulation();
            government.getStorageDepartment().resourcesStorage.put(Resources.GOLD, government.getStorageDepartment()
                    .resourcesStorage.get(Resources.GOLD) - goldToBeGiven);
        } else {
            double goldToGet = ((0.2)*taxRate + 0.4) * government.getPopulation();
            government.getStorageDepartment().resourcesStorage.put(Resources.GOLD, government.getStorageDepartment()
                    .resourcesStorage.get(Resources.GOLD) + goldToGet);

        }
    }


    private void changeCurrentPopulation() {
        double foodLeft = edibleFood();
        Block block = null;
        for(Building building : government.getBuildings()) {
            if(building.getBuildingType().equals(GateType.KEEP)) {
                block = building.getBlock();
                break;
            }
        }
        int populationToBeAdded = (int) ((foodLeft + government.getTotalPopularity())/(2 * ((foodRate * 0.5) + 1.5)));
        for (Building building : government.getBuildings()){
            if (building.getBuildingType().equals(GateType.KEEP)){
                block = building.getBlock();
            }
        }

        Human human ;
        if (government.getPopulation() + populationToBeAdded > government.getMaxPopulation()){
            populationToBeAdded = government.getMaxPopulation() - government.getPopulation();
        }
        if(populationToBeAdded < 0) {
            int unemployed = 0;
            for (Human human1 : government.getHumans()){
                if (!(human1 instanceof Troop))
                    unemployed++;
            }
            for (int i = 0; i < Integer.min(-populationToBeAdded , unemployed); i++) {
                ArrayList<Human> humans = government.getHumans();
                for (int j = humans.size() - 1; j >= 0; j--) {
                    Human human1 = humans.get(j);
                    if (!(human1 instanceof Troop)) {
                        human1.die();
                        break;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < populationToBeAdded; i++) {
                human = new Human(block, government);
                government.addToHuman(human);
            }
        }
    }


    private void buildingAccounting(){
        for(Building building : government.getBuildings()) {
            if(building instanceof DeathPit || building instanceof CagedWarDog) continue;
            building.process();
        }
    }
    private void humanAccounting() {
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            human.applyMoves();
        }
        for (Human human : GameController.currentGame.getCurrentGovernment().getHumans()) {
            if(human instanceof Troop troop) troop.automaticAttack();
            if(human instanceof Engineer engineer) engineer.AutomaticAttack();
        }
    }
    public void nextTurnForThisUser(){
        buildingAccounting();
        humanAccounting();
        foodPopularityAccounting();
        giveFoodToPeople();
        taxPopularityAccounting();
        getMoneyFromPeople();
        fearPopularityAccounting();
        moralityAndIntegrity();
        religionPopularityAccounting();
        changeCurrentPopulation();
    }

    public AccountingDepartment(Government government) {
        this.government = government;
    }

    public Government getGovernment() {
        return government;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getReligionPopularity() {
        return religionPopularity;
    }

    public int getTaxPopularity() {
        return taxPopularity;
    }

    public void setTaxPopularity(int taxPopularity) {
        this.taxPopularity = taxPopularity;
    }

    public int getFoodPopularity() {
        return foodPopularity;
    }

    public void setFoodPopularity(int foodPopularity) {
        this.foodPopularity = foodPopularity;
    }

    public int getFearPopularity() {
        return fearPopularity;
    }

    public void setFearPopularity(int fearPopularity) {
        this.fearPopularity = fearPopularity;
    }

    public void setReligionPopularity(int religionPopularity) {
        this.religionPopularity = religionPopularity;
    }

}
