package model.enums;

public enum BlockFillerType {
    DESERT_SHRUB,
    CHERRY,
    OLIVE,
    COCONUT,
    DATE,
    STAIR;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }
}
