package model.enums;

public enum BlockFillerType {
    DESERT_SHRUB,
    CHERRY,
    OLIVE,
    COCONUT,
    DATE,
    NORTH_BOLDER,
    SOUTH_BOLDER,
    WEST_BOLDER,
    EAST_BOLDER,
    STAIR;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }
}
