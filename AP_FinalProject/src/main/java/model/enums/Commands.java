package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    LOGIN(null),
    CHANGE_USER("Profile\\s+change\\s+slogan\\s+-s\\s*( ?<slogan>.+)*"),
    CHANGE_NICKNAME("Profile\\s+change\\s+-n\\s+( ?<nickname>.+)*"),
    CHANGE_EMAIL("Profile\\s+change\\s+-e\\s+( ?<email>.+)*"),
    CHANGE_SLOGAN("Profile\\s+change\\s+slogan\\s+-s\\s+( ?<slogan>.+)*"),
    CHANGE_PASSWORD("Profile\\s+change\\s+password\\s*(-o\\s+(?<oldPass>\\S+)\\s+-n\\s+(?<newPass>\\S+)|-n\\s+(?<newPass1>\\S+)\\s+-o\\s+(?<oldPass1>\\S+))*"),
    CHANGE_PASSWORD_RANDOMLY("Profile\\s+change\\s+password\\s*(-o\\s+(?<oldPass>\\S+)\\s+-n\\s+random|-n\\s+random\\s+-o\\s+(?<oldPass1>\\S+))*"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan"),
    DISPLAY_HIGHSCORE("Profile\\s+display\\s+highscore"),
    DISPLAY_RANK("Profile\\s+display\\s+rank"),
    DISPLAY_PROFILE("Profile\\s+display"),
    DISPLAY_SLOGAN("Profile\\s+display\\s+slogan"),
    CURRENT_MENU("Show\\s+current\\s+menu"),
    SHOW_PRICE_LIST("Show\\s+price\\s+list"),
    BUY_ITEM("Buy\\s+(-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>[0-9]+)|-a\\s+(?<amount1>[0-9]+)\\s+-i\\s+(?<item1>\\S+))"),
    SELL_ITEM("Sell\\s+(-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>[0-9]+)|-a\\s+(?<amount1>[0-9]+)\\s+-i\\s+(?<item1>\\S+))"),
    TRADE("");

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
