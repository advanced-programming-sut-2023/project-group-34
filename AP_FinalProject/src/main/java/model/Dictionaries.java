package model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import model.human.SiegeType;
import model.human.TroopType;
import model.building.*;

import static model.human.SiegeType.*;

public class Dictionaries  {
    public static BiMap<String , BuildingType> buildingDictionary = HashBiMap.create();
    static {
        buildingDictionary.put("small stone gatehouse" , GateType.SMALL_GATE_HOUSE);
        buildingDictionary.put("big stone gatehouse" , GateType.BIG_GATE_HOUSE);
        buildingDictionary.put("keep" , GateType.KEEP);
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
        buildingDictionary.put("stairs" , DefenciveBuildingType.STAIRS);
        buildingDictionary.put("houses" , GateType.HOUSE);
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

    public static BiMap<String, TroopType> troopDictionary = HashBiMap.create();
    static {
        troopDictionary.put("archer", TroopType.ARCHER);
        //troopDictionary.put("engineer", TroopType.ARCHER);
        troopDictionary.put("cross bow man", TroopType.CROSS_BOW_MAN);
        troopDictionary.put("spear man", TroopType.SPEAR_MAN);
        troopDictionary.put("pike man", TroopType.PIKE_MAN);
        troopDictionary.put("mace man", TroopType.MACE_MAN);
        troopDictionary.put("swords man", TroopType.SWORDS_MAN);
        troopDictionary.put("knight", TroopType.KNIGHT);
        //troopDictionary.put("tunneler", TroopType.ARCHER);
        //troopDictionary.put("ladder man", TroopType.ARCHER);
        troopDictionary.put("black monk", TroopType.BLACK_MONK);
        troopDictionary.put("archer bow", TroopType.ARCHER_BOW);
        troopDictionary.put("slave", TroopType.SLAVE);
        troopDictionary.put("slinger", TroopType.SLINGER);
        troopDictionary.put("assassin", TroopType.ASSASSIN);
        troopDictionary.put("horse archer", TroopType.HORSE_ARCHER);
        troopDictionary.put("arab sword man", TroopType.ARAB_SWORD_MAN);
        troopDictionary.put("fire thrower", TroopType.FIRE_THROWERS);
    }

    public static BiMap<String , SiegeType> siegeMachineDictionary = HashBiMap.create();
    static {
        siegeMachineDictionary.put("battering ram" , BATTERING_RAM);
        siegeMachineDictionary.put("siege tower" , SIEGE_TOWER);
        siegeMachineDictionary.put("catapult" , CATAPULT);
        siegeMachineDictionary.put("stable catapult",STABLE_CATAPULT);
        siegeMachineDictionary.put("fire xbow" , FIRE_XBOW);
    }
}
