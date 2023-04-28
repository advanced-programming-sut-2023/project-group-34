package model.government;

import model.Dictionaries;
import model.building.*;
import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.forces.human.Human;
import model.forces.human.Troop;
import model.map.Block;

public class AccountingDepartment {
    private final Government government;
    private int foodRate;
    private int fearRate;
    private int taxRate;
    private int religionPopularity;

    public int foodPopularityAccounting(){
        int foodPopularity = 0;
        foodPopularity = foodTypeCounter() -1;
        double foodNeeded = ((foodRate * 0.5) + 1) * government.getPopulation();
        if (foodNeeded > edibleFood()) foodRate = -2;
        foodPopularity = foodPopularity + foodRate * 4;
        return foodPopularity;
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

    private void religionPopularity(){
        for (Building building : government.getBuildings()){
            if (building.getBuildingType() == GeneralBuildingsType.CHURCH){
                religionPopularity++;
            } else if (building.getBuildingType() == GeneralBuildingsType.CATHEDRAL){
                religionPopularity += 2;
            }
        }
    }

    public int taxPopularityAccounting() {
        int taxPopularity = 0;
        if (government.getStorageDepartment().resourcesStorage.get(Resources.GOLD) == 0) return 1;
        if (taxRate <= 0){
            taxPopularity = (-2)*taxRate + 1;
        } else {
            taxPopularity = switch (taxRate) {
                case 1 -> -2;
                case 2 -> -4;
                case 3 -> -6;
                case 4 -> -8;
                case 5 -> -12;
                case 6 -> -16;
                case 7 -> -20;
                case 8 -> -24;
                default -> taxPopularity;
            };
        }
        return taxPopularity;
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
        int populationToBeAdded = (int) (foodLeft/(2 * ((foodRate * 0.5) + 1)));
        for (Building building : government.getBuildings()){
            if (building.getBuildingType().equals(GateType.KEEP)){
                block = building.getBlock();
            }
        }

        Human human = null;
        if (government.getPopulation() + populationToBeAdded > government.getMaxPopulation()){
            populationToBeAdded = government.getMaxPopulation() - government.getPopulation();
        }

        for (int i = 0; i < populationToBeAdded; i++) {
            human = new Human(block, government);
            government.addToHuman(human);
        }

        government.setPopulation(government.getPopulation()+populationToBeAdded);
    }

    public int getGovernmentPopularity() {
        return taxPopularityAccounting() + foodPopularityAccounting() - fearRate + religionPopularity;
    }

    private void buildingAccounting(){}
    public void nextTurnForThisUser(){
        buildingAccounting();
        giveFoodToPeople();
        getMoneyFromPeople();
        moralityAndIntegrity();
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

    public void setReligionPopularity(int religionPopularity) {
        this.religionPopularity = religionPopularity;
    }

}
