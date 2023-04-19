package src.model.government;

import src.model.enums.make_able.Food;
import src.model.enums.make_able.Resources;
import src.model.enums.make_able.Weapons;

import java.util.HashMap;
import java.util.Map;

public class StorageDepartment {
    public HashMap<Resources , Integer> resourcesStorage = new HashMap<>(Map.ofEntries(Map.entry(Resources.GOLD , 0), Map.entry(Resources.IRON, 0), Map.entry(Resources.STONE, 0), Map.entry(Resources.WOOD, 0), Map.entry(Resources.GHIR, 0), Map.entry(Resources.RESOURCES_CAPACITY, 0)));
    public HashMap<Food, Integer> foodStorage = new HashMap<>(Map.ofEntries(Map.entry(Food.ALE, 0), Map.entry(Food.HOP, 0), Map.entry(Food.APPLE, 0), Map.entry(Food.BREAD, 0), Map.entry(Food.CHEESE, 0), Map.entry(Food.FLOUR, 0), Map.entry(Food.WHEAT, 0), Map.entry(Food.MEAT, 0), Map.entry(Food.FOOD_CAPACITY, 0)));
    public HashMap<Weapons, Integer> weaponsStorage = new HashMap<>(Map.ofEntries(Map.entry(Weapons.WEAPONS_CAPACITY, 0), Map.entry(Weapons.CROSSBOW, 0), Map.entry(Weapons.BOW, 0), Map.entry(Weapons.MACE, 0), Map.entry(Weapons.LEATHER_ARMOUR, 0), Map.entry(Weapons.METAL_ARMOUR, 0), Map.entry(Weapons.SWORD, 0), Map.entry(Weapons.SPEAR, 0), Map.entry(Weapons.PIKE, 0)));

}
