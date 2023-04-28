package model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import model.building.*;

public class Dictionaries  {
    public static BiMap<String , BuildingType> buildingDictionary = HashBiMap.create();
    static {
        buildingDictionary.put("small stone gatehouse" , GateType.SMALL_GATE_HOUSE);
        buildingDictionary.put("big stone gatehouse" , GateType.BIG_GATE_HOUSE);
        buildingDictionary.put("keep" , GeneralBuildingsType.KEEP);
        buildingDictionary.put("drawbridge" , DrawBridgeType.DRAW_BRIDGE);
        buildingDictionary.put("lookout tower" , DefenciveBuildingType.LOOKOUT_TOWER);
        buildingDictionary.put("perimeter tower" , DefenciveBuildingType.PERIMETER_TOWER);
        buildingDictionary.put("defencive turret" , DefenciveBuildingType.DEFENCIVE_TURRET);
        buildingDictionary.put("square tower" , DefenciveBuildingType.SQUARE_TOWER);
        buildingDictionary.put("circle tower" , DefenciveBuildingType.CIRCLE_TOWER);
        buildingDictionary.put("low wall" , DefenciveBuildingType.LOW_WALL);
        buildingDictionary.put("high wall" , DefenciveBuildingType.HIGH_WALL);
        buildingDictionary.put("caged war dogs" , CagedWarDogType.CAGED_WAR_DOG);
        buildingDictionary.put("cathedral" , GeneralBuildingsType.CATHEDRAL);
        buildingDictionary.put("church" , GeneralBuildingsType.CHURCH);
        buildingDictionary.put("death pit" , DeathPitType.DEATH_PIT);
        buildingDictionary.put("houses" , GeneralBuildingsType.HOUSE);
        buildingDictionary.put("food storage" , GeneralBuildingsType.FOOD_STORAGE);
        buildingDictionary.put("armour storage" , GeneralBuildingsType.ARMOUR_STORAGE);
        buildingDictionary.put("resource storage" , GeneralBuildingsType.RESOURCES_STORAGE);
        buildingDictionary.put("shop" , MakerType.SHOP);
        buildingDictionary.put("barrack" , GeneralBuildingsType.BARRACK);
        buildingDictionary.put("mercenary" , GeneralBuildingsType.MERCENARY);
        buildingDictionary.put("engineer guild" , GeneralBuildingsType.ENGINEER_GUILD);
        buildingDictionary.put("tunnelers guild" , GeneralBuildingsType.TUNNELERS_GUILD);
        buildingDictionary.put("siege tent" , GeneralBuildingsType.SIEGE_TENT);
        buildingDictionary.put("inn" , InnType.INN);
        buildingDictionary.put("iron mine" , MakerType.IRON_MINE);
        buildingDictionary.put("quarry" , MakerType.QUARRY);
        buildingDictionary.put("wood cutter" , MakerType.WOOD_CUTTER);
        buildingDictionary.put("pitch rig" , MakerType.PITCH_RIG);
        buildingDictionary.put("armourer" , MakerType.ARMOURER);
        buildingDictionary.put("fletcher" , MakerType.FLETCHER);
        buildingDictionary.put("blacksmith" , MakerType.BLACKSMITH);
        buildingDictionary.put("poleturner" , MakerType.POLETURNER);
        buildingDictionary.put("stable" , MakerType.STABLE);
        buildingDictionary.put("apple garden" , MakerType.APPLE_GARDEN);
        buildingDictionary.put("dairy factory" , MakerType.DAIRY_FACTORY);
        buildingDictionary.put("hop farm" , MakerType.HOP_FARM);
        buildingDictionary.put("brewery" , MakerType.BREWERY);
        buildingDictionary.put("wheat farm" , MakerType.WHEAT_FARM);
        buildingDictionary.put("hunting ground" , MakerType.HUNTING_GROUND);
        buildingDictionary.put("bakery" , MakerType.BAKERY);
        buildingDictionary.put("mill" , MakerType.MILL);
        buildingDictionary.put("oil smelter" , OilSmelterType.OIL_SMELTER);
        buildingDictionary.put("ox tether" , OxTetherType.OX_TETHER);
    }
}
