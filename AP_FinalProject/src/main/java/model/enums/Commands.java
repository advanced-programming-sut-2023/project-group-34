package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    LOGIN(null),
    CHANGE_USER("profile\\s+change\\s+-u\\s+(?<username>\\S+)\\s+"),
    CHANGE_NICKNAME("profile\\s+change\\s+-n\\s+(?<nickname>\\S+)\\s+"),
    CHANGE_EMAIL("profile\\s+change\\s+-e\\s+(?<email>\\S+)\\s+");

    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getOutput(String input , Commands commands) {
        Matcher matcher = Pattern.compile(commands.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
