package controller;

import model.enums.make_able.Food;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;

import java.util.Map;
import java.util.regex.Matcher;

public class ShopController {
    public static String buyItems (Matcher matcher) {
        
        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");
        item = item.replaceAll("\"", "");
        
        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, buying failed";
        
        if (finalAmount <= 0) return "Invalid amount, buying failed";
        
        double finalPrice = GameController.currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount);
        
        if (finalPrice == 0 && !item.equals("gold"))
            return "The product you are looking for does not exit, buying failed";
        
        if (finalPrice > GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD))
            return "You do not have enough gold to buy this item, buying failed";
        
        if (!capacityCheckerForBuying(item, finalAmount))
            return "You do not have enough capacity to buy this item, buying failed";
        
        GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(Resources.GOLD,
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD) - finalPrice);
        
        changeStorage(item, finalAmount, 1);
        return "Item purchased successfully";
    }
    
    public static boolean capacityCheckerForBuying (String name, int amount) {
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().getResourcesMaxCapacity() >=
                        amount + GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesOccupied())
                    return true;
            }
        }
        
        for (Weapons weapons : Weapons.values()) {
            if (weapons.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsMaxCapacity() >=
                        amount + GameController.currentGame.getCurrentGovernment().getStorageDepartment().weaponsOccupied())
                    return true;
            }
        }
        
        for (Food food : Food.values()) {
            if (food.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().getFoodMaxCapacity() >=
                        amount + GameController.currentGame.getCurrentGovernment().getStorageDepartment().foodOccupied())
                    return true;
            }
        }
        return false;
    }
    
    static boolean capacityCheckerForSelling (String name, int amount) {
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) - amount >= 0)
                    return true;
            }
        }
        
        for (Weapons weapons : Weapons.values()) {
            if (weapons.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.get(weapons) - amount >= 0)
                    return true;
            }
        }
        
        for (Food food : Food.values()) {
            if (food.getName().equals(name)) {
                if (GameController.currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(food) - amount >= 0)
                    return true;
            }
        }
        return false;
    }
    
    public static String sellItems (Matcher matcher) {
        int finalAmount = Integer.parseInt(matcher.group("amount"));
        String item = matcher.group("item");
        
        if (matcher.group("amount").isEmpty() && (matcher.group("item").isEmpty()))
            return "The required field is empty, selling failed";
        
        
        if (finalAmount <= 0) return "Invalid amount, selling failed";
        
        double finalPrice = (GameController.currentGame.getCurrentGovernment().getStorageDepartment().priceOfASource(item, finalAmount) * 4) / 5;
        
        if (finalPrice == 0) return "The product you are looking for does not exit, selling failed";
        if (!capacityCheckerForSelling(item, finalAmount)) return "You do not have enough to sell, selling failed";
        
        GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(Resources.GOLD,
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(Resources.GOLD) + finalPrice);
        changeStorage(item, finalAmount, -1);
        return "Item sold successfully";
    }
    
    private static void changeStorage (String name, int amount, int coefficient) {
        amount *= coefficient;
        for (Resources resources : Resources.values()) {
            if (resources.getName().equals(name)) {
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.put(resources,
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().resourcesStorage.get(resources) + amount);
                return;
            }
        }
        
        for (Weapons weapons : Weapons.values()) {
            if (weapons.getName().equals(name)) {
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.put(weapons,
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().weaponsStorage.get(weapons) + amount);
                return;
            }
        }
        
        for (Food food : Food.values()) {
            if (food.getName().equals(name)) {
                GameController.currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.put(food,
                        GameController.currentGame.getCurrentGovernment().getStorageDepartment().foodStorage.get(food) + amount);
                return;
            }
        }
    }
    
    private static String getResourcesPriceList () {
        String finalString = "";
        for (Map.Entry<Resources, Double> entry : GameController.currentGame.getCurrentGovernment().getStorageDepartment().getResourcesStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " + entry.getKey().getPrice() +
                    " Selling Price: " + (entry.getKey().getPrice() * 4) / 5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    private static String getFoodPriceList () {
        String finalString = "";
        for (Map.Entry<Food, Double> entry : GameController.currentGame.getCurrentGovernment().getStorageDepartment().getFoodStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " + entry.getKey().getPrice() +
                    " Selling Price: " + (entry.getKey().getPrice() * 4) / 5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    private static String getWeaponPriceList () {
        String finalString = "";
        for (Map.Entry<Weapons, Double> entry : GameController.currentGame.getCurrentGovernment().getStorageDepartment().getWeaponsStorage().entrySet()) {
            finalString = finalString.concat("Item: " + entry.getKey().getName() + " Buying Price: " + entry.getKey().getPrice() +
                    " Selling Price: " + (entry.getKey().getPrice() * 4) / 5 + " Amount: " + entry.getValue() + "\n");
        }
        return finalString;
    }
    
    public static String showPriceList () {
        String finalString = "";
        finalString = finalString.concat(getResourcesPriceList());
        finalString = finalString.concat(getFoodPriceList());
        finalString = finalString.concat(getWeaponPriceList());
        
        return finalString.substring(0, finalString.length() - 2);
    }
}
