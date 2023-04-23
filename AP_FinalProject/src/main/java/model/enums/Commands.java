package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    CREATE_USER("\\s*user\\s+create(?=.*\\s-u\\s+(?<username>\"[^\"]+\"|\\S+))(?=.*(\\s-p\\s+(?<password>\"[^\"]+\"|\\S+)\\s+(?<passwordConfirmation>\"[^\"]+\"|\\S+)|\\s-p\\s+random))(?=.*\\s-e\\s+(?<email>\"[^\"]+\"|\\S+))(?=.*\\s-n\\s+(?<nickname>\"[^\"]+\"|\\S+))(?=.*\\s(?<sloganFlag>-s)\\s+(?<slogan>\"[^\"]+\"|\\S+))?((\\s+-[unse]\\s+(\"[^\"]+\"|\\S+))|(\\s+-p\\s+(\"[^\"]+\"|\\S+)\\s+(\"[^\"]+\"|\\S+)|\\s+-p\\s+random)){4,5}"),
    PICK_QUESTION("\\s*question\\s+pick\\s+-q\\s+<questionNumber>\\s+-a\\s+<answer>\\s+-c\\s+<answerConfirm>\\s*"),
    LOGIN("(?=.*-u (?<username>\\S+))(?=.*-p (?<password>\\S+))(?=.*(?<flag>--stay-logged-in))?^user\\s+login(\\s+-[up]\\s+\\S+|\\s+--stay-logged-in){2,3}"),
    ENTER_FORGOT_PASSWORD_MENU("\\s*enter\\s+forgot\\s+password\\s+menu\\s*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password(\\s+-u\\s+(?<username>\\S+))?\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    CHANGE_USER("Profile\\s+change\\s+slogan\\s+-s\\s*( ?<slogan>.+)*"),
    CHANGE_NICKNAME("Profile\\s+change\\s+-n\\s+( ?<nickname>.+)*"),
    CHANGE_EMAIL("Profile\\s+change\\s+-e\\s+( ?<email>.+)*"),
    CHANGE_SLOGAN("Profile\\s+change\\s+slogan\\s+-s\\s+( ?<slogan>.+)*"),
    CHANGE_PASSWORD("Profile\\s+change\\s+password\\s*( -o\\s+(?<oldPass>\\S+)\\s+-n\\s+(?<newPass>\\S+)|-n\\s+(?<newPass1>\\S+)\\s+-o\\s+(?<oldPass1>\\S+))*"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan"),
    SET_TEXTURE("\\s*settexture(\\s+-x\\s+(?<singleX>\\d+)\\s+-y\\s+(?<singleY>\\d+)|\\s+-x1\\s+(?<x1>\\d+)\\s+-y1\\s+(?<y1>\\d+)\\s+-x2\\s+(?<x2>\\d+)\\s+-y2\\s+(?<y2>\\d+))\\s+-t\\s+(?<type>\\S+)\\s*"),
    CLEAR("clear(?=.*\\s+-x\\s+(?<xIndex>\\d+))(?=.*\\s+-y\\s+(?<yIndex>\\d+))(\\s+-[xy]\\s+\\d+){2}"),
    DROP_ROCK("droprock(?=.*\\s+-x\\s+(?<xIndex>\\d+))(?=.*\\s+-y\\s+(?<yIndex>\\d+))(?=.*\\s+-d\\s+(?<direction>\\S+))(\\s+-[xyd]\\s+\\S+){3}"),
    DROP_TREE("droptree(?=.*\\s+-x\\s+(?<xIndex>\\d+))(?=.*\\s+-y\\s+(?<yIndex>\\d+))(?=.*\\s+-t\\s+(?<type>\\S+))(\\s+-[xyt]\\s+\\S+){3}"),
    DROP_BUILDING("dropbuilding(?=.*\\s+-x\\s+(?<xIndex>\\d+))(?=.*\\s+-y\\s+(?<yIndex>\\d+))(?=.*\\s+-t\\s+(?<type>\\S+))(\\s+-[xyt]\\s+\\S+){3}"),
    DROP_UNIT("droptree(?=.*\\s+-x\\s+(?<xIndex>\\d+))(?=.*\\s+-y\\s+(?<yIndex>\\d+))(?=.*\\s+-c\\s+(?<count>\\d+))(?=.*\\s+-t\\s+(?<type>\\S+))(\\s+-[xytc]\\s+\\S+){4}"),
    BACK("\\s*back\\s*");

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
