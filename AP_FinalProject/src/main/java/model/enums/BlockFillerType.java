package model.enums;

public enum BlockFillerType {
    DESERT_SHRUB,
    CHERRY,
    OLIVE,
    COCONUT,
    DATE;

    @Override
    public String toString() {
        return super.toString().toLowerCase().replace("_", " ");
    }

    public static BlockFillerType stringToType(String string) {
        for (BlockFillerType value : BlockFillerType.values()) {
            if (value.toString().equals(string)) return value;
        }
        return null;
    }
}
