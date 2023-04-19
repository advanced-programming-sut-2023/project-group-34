package model.building;

import model.enums.BlockType;
import model.enums.make_able.MakeAble;
import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.enums.make_able.Resources.WOOD;

public enum MakerType implements BuildingType{
    IRON_MAKER(null, 0, null , 0, 0, null, 0,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    STONE_MAKER(null, 0, null, 0, 0, null, 0,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    WOOD_MAKER(null, 0, null, 0, 0, null, 0,  new HashMap<>(Map.ofEntries(Map.entry(WOOD , 3)))),
    GHIR_MAKER(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    ARMOUR_MAKER(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    BOW_MAKER(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    SWORD_MAKER(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    SPEAR_MAKER(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    STABLE(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))) ,
    APPLE_GARDEN(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    CHEESE_FACTORY(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    HOP_FARM(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    ALE_MAKER(null , 0 , null , 0 , 0 , null ,0 , null),
    WHEAT_FARM(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    HUNTING_GROUND(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    BAKERY(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20)))),
    MILL(null, 0, null, 0, 0, null, 0, new HashMap<>(Map.ofEntries(Map.entry(WOOD , 20))));

    private final MakeAble input;
    private final int inputRate;
    private final ArrayList<MakeAble> output;
    private final int outputRate;
    private final int capacity;
    private final BlockType requiredBlock;
    private final int HP;
    private final HashMap<Resources, Integer> cost;

    MakerType(MakeAble input, int inputRate, ArrayList<MakeAble> output, int outputRate, int capacity, BlockType requiredBlock, int hp , HashMap<Resources, Integer> cost) {
        this.input = input;
        this.inputRate = inputRate;
        this.output = output;
        this.outputRate = outputRate;
        this.capacity = capacity;
        this.requiredBlock = requiredBlock;
        HP = hp;
        this.cost = cost;
    }

    public static Maker resourceMakerCreator(Government government , Block block , MakerType makerType) {
        return null;
    }
}
