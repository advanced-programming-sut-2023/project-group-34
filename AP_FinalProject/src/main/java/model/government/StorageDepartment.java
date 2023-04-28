package model.government;

import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;

import java.util.HashMap;
import java.util.Map;

public class StorageDepartment {
    public final HashMap<Resources , Double> resourcesStorage = new HashMap<>(Map.ofEntries(Map.entry(Resources.GHIR, 0.0), Map.entry(Resources.IRON, 0.0), Map.entry(Resources.WOOD, 0.0), Map.entry(Resources.STONE, 0.0), Map.entry(Resources.GOLD, 0.0)));
    public final HashMap<Weapons, Double> weaponsStorage = new HashMap<>(Map.ofEntries(Map.entry(Weapons.PIKE , 0.0) , Map.entry(Weapons.SWORD , 0.0) , Map.entry(Weapons.SPEAR , 0.0) , Map.entry(Weapons.MACE , 0.0) , Map.entry(Weapons.BOW , 0.0) , Map.entry(Weapons.CROSSBOW ,0.0) , Map.entry(Weapons.METAL_ARMOUR , 0.0) , Map.entry(Weapons.LEATHER_ARMOUR , 0.0), Map.entry(Weapons.HORSE, 0.0)));
    public final HashMap<Food, Double> foodStorage = new HashMap<>(Map.ofEntries(Map.entry(Food.ALE, 0.0), Map.entry(Food.HOP, 0.0), Map.entry(Food.CHEESE, 0.0), Map.entry(Food.APPLE, 0.0), Map.entry(Food.BREAD, 0.0), Map.entry(Food.FLOUR, 0.0), Map.entry(Food.WHEAT, 0.0), Map.entry(Food.MEAT, 0.0)));

    private double resourcesMaxCapacity;

    private double weaponsMaxCapacity;
    private double foodMaxCapacity;

    public double getResourcesMaxCapacity() {
        return resourcesMaxCapacity;
    }

    public void setResourcesMaxCapacity(double resourcesMaxCapacity) {
        this.resourcesMaxCapacity = resourcesMaxCapacity;
    }

    public double getWeaponsMaxCapacity() {
        return weaponsMaxCapacity;
    }

    public void setWeaponsMaxCapacity(double weaponsMaxCapacity) {
        this.weaponsMaxCapacity = weaponsMaxCapacity;
    }

    public double getFoodMaxCapacity() {
        return foodMaxCapacity;
    }

    public void setFoodMaxCapacity(double foodMaxCapacity) {
        this.foodMaxCapacity = foodMaxCapacity;
    }



    public double resourcesOccupied() {
        double spaceOccupied = 0.0;
        for (Map.Entry<Resources, Double> entry : resourcesStorage.entrySet()) {
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public double foodOccupied() {
        double spaceOccupied = 0;
        for (Map.Entry<Food, Double> entry : foodStorage.entrySet()) {
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public double weaponsOccupied(){
        double spaceOccupied = 0;
        for (Map.Entry<Weapons, Double> entry : weaponsStorage.entrySet()){
            spaceOccupied += entry.getValue();
        }
        return spaceOccupied;
    }

    public double priceOfASource(String name, double amount){
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


    public HashMap<Resources, Double> getResourcesStorage() {
        return resourcesStorage;
    }

    public HashMap<Weapons, Double> getWeaponsStorage() {
        return weaponsStorage;
    }

    public HashMap<Food, Double> getFoodStorage() {
        return foodStorage;
    }
}
