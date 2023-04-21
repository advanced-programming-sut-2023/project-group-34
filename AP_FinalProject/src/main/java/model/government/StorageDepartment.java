package model.government;

import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;

import java.util.HashMap;
import java.util.Map;

public class StorageDepartment {
    public HashMap<Resources , Integer> resourcesStorage = new HashMap<>(Map.ofEntries(Map.entry(Resources.IRON, 0), Map.entry(Resources.STONE, 0), Map.entry(Resources.WOOD, 0), Map.entry(Resources.GHIR, 0)));
    public HashMap<Weapons, Integer> weaponsStorage = new HashMap<>(Map.ofEntries(Map.entry(Weapons.CROSSBOW, 0), Map.entry(Weapons.BOW, 0), Map.entry(Weapons.MACE, 0), Map.entry(Weapons.LEATHER_ARMOUR, 0), Map.entry(Weapons.METAL_ARMOUR, 0), Map.entry(Weapons.SWORD, 0), Map.entry(Weapons.SPEAR, 0), Map.entry(Weapons.PIKE, 0)));
    public HashMap<Food, Double> foodStorage = new HashMap<>(Map.ofEntries(Map.entry(Food.ALE, 0.0), Map.entry(Food.HOP, 0.0), Map.entry(Food.CHEESE, 0.0), Map.entry(Food.APPLE, 0.0), Map.entry(Food.BREAD, 0.0), Map.entry(Food.FLOUR, 0.0), Map.entry(Food.WHEAT, 0.0), Map.entry(Food.MEAT, 0.0)));

    private double gold;
    private int resourcesMaxCapacity;
    private int weaponsMaxCapacity;
    private Double foodMaxCapacity;

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getResourcesMaxCapacity() {
        return resourcesMaxCapacity;
    }

    public void setResourcesMaxCapacity(int resourcesMaxCapacity) {
        this.resourcesMaxCapacity = resourcesMaxCapacity;
    }

    public int getWeaponsMaxCapacity() {
        return weaponsMaxCapacity;
    }

    public void setWeaponsMaxCapacity(int weaponsMaxCapacity) {
        this.weaponsMaxCapacity = weaponsMaxCapacity;
    }

    public Double getFoodMaxCapacity() {
        return foodMaxCapacity;
    }

    public void setFoodMaxCapacity(Double foodMaxCapacity) {
        this.foodMaxCapacity = foodMaxCapacity;
    }

    public String getResourcesPriceList(){
        String finalString = new String();
        for (Map.Entry<Resources, Integer> entry : resourcesStorage.entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue());
        }
        return finalString;
    }

    public String getFoodPriceList(){
        String finalString = new String();
        for (Map.Entry<Food, Double> entry : foodStorage.entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue());
        }
        return finalString;
    }

    public String getWeaponPriceList(){
        String finalString = new String();
        for (Map.Entry<Weapons, Integer> entry : weaponsStorage.entrySet()){
            finalString = finalString.concat("Item: " + entry.getKey().getName() + "Buying Price: " +
                    entry.getKey().getPrice() + "Selling Price: " + (entry.getKey().getPrice()*4)/5 + "Amount: " + entry.getValue());
        }
        return finalString;
    }

    public int resourcesOccupied() {
        int spaceOccupied = 0;
        for (Map.Entry<Resources, Integer> entry : resourcesStorage.entrySet()) {
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public int foodOccupied() {
        int spaceOccupied = 0;
        for (Map.Entry<Food, Double> entry : foodStorage.entrySet()) {
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public int weaponsOccupied(){
        int spaceOccupied = 0;
        for (Map.Entry<Weapons, Integer> entry : weaponsStorage.entrySet()){
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public int priceOfASource(String name, int amount){
        for (Weapons weapons : Weapons.values()){
            if (weapons.getName().equals(name)){
                return amount * weapons.getPrice();
            }
        }

        for (Resources resources : Resources.values()){
            if (resources.getName().equals(name)) {
                return amount * resources.getPrice();
            }
        }

        for (Food food : Food.values()){
            if (food.getName().equals(name))
                return amount * food.getPrice();
        }
        return 0;
    }

}
