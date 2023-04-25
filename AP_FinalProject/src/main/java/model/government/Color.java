package model.government;

public class Color {
    private final String colorName;

    private final Government government;
    public Color(String colorName, Government government) {
        this.colorName = colorName;
        this.government = government;
    }

    public Government getGovernment() {
        return government;
    }

    public String getColorName() {
        return colorName;
    }
}
