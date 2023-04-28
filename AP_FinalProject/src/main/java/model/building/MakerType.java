package model.building;

import model.enums.make_able.Food;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.enums.make_able.Weapons;
import model.government.Government;
import model.map.Block;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static model.enums.make_able.Resources.WOOD;

public enum MakerType implements BuildingType {
    IRON_MINE(null, 0, new ArrayList<>(Arrays.asList(Resources.IRON)) , 3, 0, 250,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 2),
    QUARRY(null, 0, new ArrayList<>(Arrays.asList(Resources.STONE)), 2, 20, 250,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 3),
    WOOD_CUTTER(null, 0, new ArrayList<>(Arrays.asList(WOOD)), 2, 0,  100,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 3))), 1),
    PITCH_RIG(null, 0, null, 0, 0, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD, 0))), 0),
    ARMOURER(Resources.IRON, 2, new ArrayList<>(Arrays.asList(Weapons.METAL_ARMOUR)), 1, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 1),
    FLETCHER(WOOD, 2, new ArrayList<>(Arrays.asList(Weapons.BOW, Weapons.CROSSBOW)), 1, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 1),
    BLACKSMITH(Resources.IRON, 2, null, 3, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    POLETURNER(null, 2, null, 0, 0,  250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    STABLE(null, 0, new ArrayList<>(Arrays.asList(Weapons.HORSE)), 4, 0, 250, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0) ,
    APPLE_GARDEN(null, 0, new ArrayList<>(Arrays.asList(Food.APPLE)), 3, 0, 120, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 4),
    DAIRY_FACTORY(null, 0, null, 0, 0,  0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    HOP_FARM(null, 0, null, 0, 0,  0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    BREWERY(null , 0 , null , 0 , 0 , 0 , null, 0),
    WHEAT_FARM(null, 0, null, 0, 0, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    HUNTING_GROUND(null, 0, null, 0, 0,  0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    BAKERY(null, 0, null, 0, 0, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0),
    MILL(null, 0, null, 0, 0, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))), 0);

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
