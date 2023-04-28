package model.building;

import model.enums.make_able.Food;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.government.Government;
import model.map.Block;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

import static model.enums.make_able.Resources.GOLD;
import static model.enums.make_able.Resources.WOOD;

public enum MakerType implements BuildingType {
    IRON_MINE(null, 0, new ArrayList<>(List.of(Resources.IRON)) , 5, 5, 250,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 2),
    QUARRY(null, 0, new ArrayList<>(List.of(Resources.STONE)), 5, 20, 250,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 3),
    WOOD_CUTTER(null, 0, new ArrayList<>(List.of(WOOD)), 4, 0,  100,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 3))), 1),
    PITCH_RIG(null, 0, new ArrayList<>(List.of(Resources.GHIR)), 3, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD, 20))), 1),
    ARMOURER(Resources.IRON, 2, new ArrayList<>(List.of(Weapons.METAL_ARMOUR)), 1, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20) , Map.entry(GOLD , 100))), 1),
    FLETCHER(WOOD, 4, new ArrayList<>(Arrays.asList(Weapons.BOW, Weapons.CROSSBOW)), 1, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20) , Map.entry(GOLD , 100))), 1),
    BLACKSMITH(Resources.IRON, 4, new ArrayList<>(Arrays.asList(Weapons.SWORD , Weapons.MACE)), 2, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20) , Map.entry(GOLD , 100))), 1),
    POLETURNER(WOOD, 2, new ArrayList<>(Arrays.asList(Weapons.SPEAR , Weapons.PIKE)), 0, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 10) , Map.entry(GOLD , 100))), 1),
    STABLE(null, 0, new ArrayList<>(List.of(Weapons.HORSE)), 4, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20) , Map.entry(GOLD , 400))), 0) ,
    APPLE_GARDEN(null, 0, new ArrayList<>(List.of(Food.APPLE)), 3, 0, 120, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 5))), 1),
    DAIRY_FACTORY(null, 0, new ArrayList<>(Arrays.asList(Weapons.LEATHER_ARMOUR , Food.CHEESE)), 3, 0,  300, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 10))), 1),
    HOP_FARM(null, 0, new ArrayList<>(List.of(Food.HOP)), 3, 0,  150, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 15))), 1),
    BREWERY(Food.HOP , 4 , new ArrayList<>(List.of(Food.ALE)), 2 , 0 , 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 10))), 1),
    WHEAT_FARM(null, 0, new ArrayList<>(List.of(Food.WHEAT)), 3, 0, 15, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 15))), 1),
    HUNTING_GROUND(null, 0, new ArrayList<>(List.of(Food.MEAT)), 3, 0,  150, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 5))), 1),
    BAKERY(Food.FLOUR, 2, new ArrayList<>(List.of(Food.BREAD)), 5, 0, 200, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 10))), 1),
    MILL(Food.WHEAT, 3, new ArrayList<>(List.of(Food.FLOUR)), 2, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 3),
    SHOP(null, 0, null, 0, 0, 800, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 5))), 1);

    private final MakeAble input;
    private final int inputRate;
    private final ArrayList<MakeAble> output;
    private final int outputRate;
    private final int capacity;
    private final int HP;
    private final HashMap<Resources, Integer> cost;
    private final int numberOfWorkers;

    MakerType(MakeAble input, int inputRate, ArrayList<MakeAble> output, int outputRate, int capacity, int hp , HashMap<Resources, Integer> cost, int numberOfWorkers) {
        this.input = input;
        this.inputRate = inputRate;
        this.output = output;
        this.outputRate = outputRate;
        this.capacity = capacity;
        HP = hp;
        this.cost = cost;
        this.numberOfWorkers = numberOfWorkers;
    }

    public void create(Government government , Block block) {
            block.addBuilding(new Maker(government , block , this.output , this.outputRate , this.capacity , this.HP , this.numberOfWorkers , this.cost , this , this.inputRate , this.input));
    }
}
