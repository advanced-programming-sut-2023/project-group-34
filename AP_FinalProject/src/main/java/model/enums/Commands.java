package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {

    //TODO: Handle spaces in qutations
    LOGIN(null),
    CHANGE_USER("Profile\\s+change\\s+-u\\s* (?<username>\".+\"|\\S+)*"),
    CHANGE_NICKNAME("Profile\\s+change\\s+-u\\s* (?<nickname>\".+\"|\\S+)*"),
    CHANGE_EMAIL("Profile\\s+change\\s+-e\\s* (?<email>.+)*"),
    CHANGE_SLOGAN("Profile\\s+change\\s+-u\\s* (?<slogan>\".+\"|\\S+)*"),
    CHANGE_PASSWORD("(?=.*-o (?<oldPass>\".+\"|\\S+))(?=.*-n (?<newPass>\".+\"|\\S+))^Profile\\s+change\\s+password\\s*( *-[on] (\".+\"|\\S+)){2}$"),
    CHANGE_PASSWORD_RANDOMLY("(?=.*-o (?<oldPass>\".+\"|\\S+))(?=.*-n (?<newPass>random))^Profile\\s+change\\s+password\\s*( *-[on] (\".+\"|\\S+)){2}$"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan"),
    DISPLAY_HIGHSCORE("Profile\\s+display\\s+highscore"),
    DISPLAY_RANK("Profile\\s+display\\s+rank"),
    DISPLAY_PROFILE("Profile\\s+display"),
    DISPLAY_SLOGAN("Profile\\s+display\\s+slogan"),
    CURRENT_MENU("Show\\s+current\\s+menu"),
    SHOW_PRICE_LIST("Show\\s+price\\s+list"),
    BUY_ITEM("(?=.*-i (?<item>\\S+))(?=.*-a (?<amount>\\d+))^Buy\\s*( *-[ia] \\S+){2}$"),
    SELL_ITEM("(?=.*-i (?<item>\\S+))(?=.*-a (?<amount>\\d+))^Sell\\s*( *-[ia] \\S+){2}$"),
    TRADE_HISTORY("Trade\\s+history"),
    SHOW_POPULARITY_FACTORS("Show\\s+popularity\\s+factors"),
    SHOW_POPULARITY("Show\\s+popularity"),
    SHOW_FOOD_LIST("Show\\s+food\\s+list"),
    SET_FOOD_RATE("Food\\s+rate\\s+-r\\s+(?<foodRate>-?[0-9]+)"),
    SHOW_FOOD_RATE("Show\\s+food\\s+rate"),
    SET_TAX_RATE("Tax\\s+rate\\s+-r\\s+(?<taxRate>-?[0-9]+)"),
    SHOW_TAX_RATE("Show\\s+tax\\s+rate"),
    SET_FEAR_RATE("Fear\\s+rate\\s+-r\\s+(?<fearRate>-?[0-9]+)"),
    SHOW_FEAR_RATE("Show\\s+fear\\s+rate");

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
