package model.enums;

import model.map.Block;

public enum BlockType {
    GROUND,
    BOULDER,
    STONY_GROUND,
    IRON,
    GRASS, // چمن
    MEADOW, // علفزار
    DENSE_MEADOW, // علفزار پر تراکم
    OIL,
    PLAIN, //جلگه
    SHALLOW_WATER,
    RIVER,
    BEACH,
    SEA,
    LAKE,
    NORTH_BOULDER,
    SOUTH_BOULDER,
    WEST_BOULDER,
    EAST_BOULDER,
    DITCH;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }

    public BlockType stringToBlockType(String string) {
        for (BlockType value : BlockType.values()) {
            if (value.toString().equals(string)) return value;
        }
        return null;
    }
}
