package model.enums;

public enum BlockFillerType {
    DESERT_SHRUB(100),
    CHERRY(100),
    OLIVE(100),
    COCONUT(100),
    DATE(100);
    private int amount;

    BlockFillerType(int amount) {
        this.amount = amount;
    }

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

    public int getAmount() {
        return amount;
    }

    public void use(int amount) {
        this.amount -= amount;
    }
}
