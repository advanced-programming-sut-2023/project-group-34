package model.enums;

import java.util.regex.Matcher;

public enum Commands {
    LOGIN(null);
    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static String getOutput(Matcher matcher , Commands commands) {
        return null;
    }
}
