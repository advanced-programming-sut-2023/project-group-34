package model.enums;

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
}
