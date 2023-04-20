package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //TODO: improve login and create regexes
    CREATE_USER("\\s*user\\s+create((\\s+-u\\s+(?<username>\\S+))|(\\s+-p\\s+(?<password>\\S+))|(\\s+(?<passwordConfirmation>\\S+))|(\\s+–email\\s+(?<email>\\S+))|(\\s+-n\\s+(?<nickname>\\S+))|(\\s+-s\\s+(?<slogan>.+)))*\\s*"),
    LOGIN("\\s*user\\s+login(?=.*\\s+-u\\s+(?<username>\\S+))(?=.*\\s+-p\\s+(?<password>\\S+))(.*\\s+--stay-logged-in.*)?.*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password(\\s+-u\\s+(?<username>\\S+))?\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    CHANGE_USER("Profile\\s+change\\s+slogan\\s+-s\\s*( ?<slogan>.+)*"),
    CHANGE_NICKNAME("Profile\\s+change\\s+-n\\s+( ?<nickname>.+)*"),
    CHANGE_EMAIL("Profile\\s+change\\s+-e\\s+( ?<email>.+)*"),
    CHANGE_SLOGAN("Profile\\s+change\\s+slogan\\s+-s\\s+( ?<slogan>.+)*"),
    CHANGE_PASSWORD("Profile\\s+change\\s+password\\s*( -o\\s+(?<oldPass>\\S+)\\s+-n\\s+(?<newPass>\\S+)|-n\\s+(?<newPass1>\\S+)\\s+-o\\s+(?<oldPass1>\\S+))*"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan");

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

    public static boolean matches(String input, Commands command) {
        return Pattern.matches(command.regex, input);
    }
}
