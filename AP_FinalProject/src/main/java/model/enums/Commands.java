package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    CREATE_USER("\\s*user\\s+create(?=.*\\s+-u(\\s+(?<username>\"[^\"]*\"|\\S*))?)(?=.*(\\s+-p\\s+(?<password>\"[^\"]*\"|\\S*)\\s+(?<passwordConfirmation>\"[^\"]*\"|\\S*)|\\s+-p\\s+random))(?=.*\\s-e\\s+(?<email>\"[^\"]*\"|\\S*))(?=.*\\s-n\\s+(?<nickname>\"[^\"]*\"|\\S*))(?=.*\\s(?<sloganFlag>-s)\\s+(?<slogan>\"[^\"]*\"|\\S*))?((\\s+-[unse](\\s+(\"[^\"]*\"|\\S*))?)|(\\s+-p\\s+(\"[^\"]*\"|\\S*)\\s+(\"[^\"]*\"|\\S*)|\\s+-p\\s+random)){4,5}"),
    PICK_QUESTION("\\s*question\\s+pick\\s+-q\\s+(?<questionNumber>\\d*)\\s+-a\\s+(?<answer>\"[^\"]*\"|\\S*)\\s+-c\\s+(?<answerConfirm>\"[^\"]*\"|\\S*)\\s*"),
    LOGIN("(?=.*-u\\s+(?<username>\"[^\"]*\"|\\S*))(?=.*-p\\s+(?<password>\"[^\"]*\"|\\S*))^\\s*user\\s+login(\\s+-[up]\\s+(\"[^\"]*\"|\\S*)|\\s+(?<flag>--stay-logged-in)){2,3}\\s*"),
    ENTER_FORGOT_PASSWORD_MENU("\\s*enter\\s+forgot\\s+password\\s+menu\\s*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u\\s+(?<username>\"[^\"]*\"|\\S*)\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    CHANGE_USER("\\s*Profile\\s+change\\s+username\\s+-u(\\s+(?<username>\"[^\"]*\"|\\S*))?\\s*"),
    CHANGE_NICKNAME("\\s*Profile\\s+change\\s+-n(\\s+(?<nickname>\"[^\"]*\"|\\S*))?\\s*"),
    CHANGE_EMAIL("\\s*Profile\\s+change\\s+-e(\\s+(?<email>\"[^\"]*\"|\\S*))?\\s*"),
    CHANGE_SLOGAN("\\s*Profile\\s+change\\s+slogan\\s+-s(\\s+(?<slogan>\"[^\"]*\"|\\S*))?\\s*"),
    CHANGE_PASSWORD("\\s*Profile\\s+change\\s+password(?=.*\\s+-o\\s*(?<oldPass>(\"[^\"]*\"|[^- ]*)))(?=.*\\s+-n\\s*(?<newPass>(\"[^\"]*\"|[^- ]*)))(\\s+-[on]\\s*(\"[^\"]*\"|\\S*)){2}\\s*"),
    CHANGE_PASSWORD_RANDOMLY("(?=.*\\s+-o\\s+(?<oldPass>\".+\"|\\S*))(?=.*\\s+-n\\s+random)^\\s*Profile\\s+change\\s+password(\\s+-[on]\\s+(\".+\"|\\S*)){2}\\s*$"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan"),
    SET_TEXTURE("\\s*settexture(?=.*\\s+-t\\s+(?<type>\\S*))((?=.*\\s+-x\\s+(?<singleX>\\d*))(?=.*\\s+-y\\s+(?<singleY>\\d*))(\\s+-[xyt]\\s+\\S*){3}|(?=.*\\s+-x1\\s+(?<x1>\\d*))(?=.*\\s+-y1\\s+(?<y1>\\d*))(?=.*\\s+-x2\\s+(?<x2>\\d*))(?=.*\\s+-y2\\s+(?<y2>\\d*))(\\s+-([xy][12]|t)\\s+\\S*){5})\\s*"),
    //empty field error handled up to here
    CLEAR("\\s*clear(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(\\s+-[xy]\\s+\\d*){2}\\s*"),
    DROP_ROCK("\\s*droprock(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-d\\s+(?<direction>\\S*))(\\s+-[xyd]\\s+\\S*){3}\\s*"),
    DROP_TREE("\\s*droptree(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-t\\s+(?<type>\\S*))(\\s+-[xyt]\\s+\\S*){3}\\s*"),
    DROP_BUILDING("\\s*dropbuilding(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-t\\s+(?<type>\\S*))(\\s+-[xyt]\\s+\\S*){3}\\s*"),
    DROP_UNIT("\\s*droptree(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-c\\s+(?<count>\\d*))(?=.*\\s+-t\\s+(?<type>\\S*))(\\s+-[xytc]\\s+\\S*){4}\\s*"),
    BACK("\\s*back\\s*"),
    DISPLAY_HIGHS_CORE("Profile\\s+display\\s+highscore"),
    DISPLAY_RANK("Profile\\s+display\\s+rank"),
    DISPLAY_PROFILE("Profile\\s+display"),
    DISPLAY_SLOGAN("Profile\\s+display\\s+slogan"),
    CURRENT_MENU("Show\\s+current\\s+menu"),
    SHOW_PRICE_LIST("Show\\s+price\\s+list"),
    BUY_ITEM("(?=.*-i\\s+(?<item>\\S*))(?=.*-a\\s+(?<amount>\\d*))^Buy\\s*(\\s+-[ia]\\s+\\S*){2}$"),
    SELL_ITEM("(?=.*-i\\s+(?<item>\\S*))(?=.*-a\\s+(?<amount>\\d*))^Sell\\s*(\\s+-[ia]\\s+\\S*){2}$"),
    SHOW_POPULARITY_FACTORS("Show\\s+popularity\\s+factors"),
    SHOW_POPULARITY("Show\\s+popularity"),
    SHOW_FOOD_LIST("Show\\s+food\\s+list"),
    SET_FOOD_RATE("Food\\s+rate\\s+-r\\s+(?<foodRate>-?[0-9]+)"),
    SHOW_FOOD_RATE("Show\\s+food\\s+rate"),
    SET_TAX_RATE("Tax\\s+rate\\s+-r\\s+(?<taxRate>-?[0-9]+)"),
    SHOW_TAX_RATE("Show\\s+tax\\s+rate"),
    SET_FEAR_RATE("Fear\\s+rate\\s+-r\\s+(?<fearRate>-?[0-9]+)"),
    SHOW_FEAR_RATE("Show\\s+fear\\s+rate"),
    SHOW_TRADE_LIST("Show\\s+trade\\s+list"),
    SHOW_TRADE_HISTORY("Show\\s+trade\\s+history"),
    ACCEPT_TRADE("(?=.*-m\\s+(?<message>\".+\"|\\S*))(?=.*-i\\s+(?<id>\\d*))^Accept\\s+trade\\s*(\\s+-[im]\\s+(\".+\"|\\S*)){2}$"),
    TRADE("(?=.*-m\\s+(?<message>((\"[a-zA-Z0-9 !@#$%^&*()_=+\\/,.]+\")|\\S*)))(?=.*-oa\\s+(?<offeredAmount>\\d*))" +
            "(?=.*-wa\\s+(?<wantedAmount>\\d*))(?=.*-w\\s+(?<wanted>\\S*))(?=.*-o\\s+(?<offered>\\S*))" +
            "(?=.*-r\\s+(?<receiver>(\".+\"|\\S*)))^Trade(\\s+-[wmoar]{1,2}\\s+(\"[^\"]*\"|\\S*)){6}$"),
    PROFILE_MENU("Enter\\s+profile\\s+menu"),
    MAP_EDITING_MENU("Enter\\s+map\\s+editing\\s+menu"),
    SHOP_MENU("Enter\\s+shop\\s+menu"),
    TRADE_MENU("Enter\\s+shop\\s+menu"),
    SHOW_MAP("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Show\\s+map(\\s+-[xy]\\s+\\d*){2}$"),
    SHOW_MAP_DETAILS("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Show\\s+map\\s+details(\\s+-[xy]\\s+\\d*){2}$"),
    MOVE_MAP("map(?=.*\\s+up\\s+(?<up>-?\\d*))?(?=.*\\s+left\\s+(?<left>-?\\d*))?(?=.*\\s+right\\s+(?<right>-?\\d*))?(?=.*\\s+down\\s+(?<down>-?\\d*))?(\\s+(down|up|right|left)\\s+-?\\d*){1,4}"),

    SELECT_BUILDING("(?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+))^Select\\s+building\\s*( *-[xy] \\S+){2}$"),
    DESELECT_BUILDING("Deselect\\s+building"),
    CREATE_UNIT("(?=.*-t (?<type>(\".+\"|\\S+)))(?=.*-c (?<count>\\d+))^Create\\s+unit\\s*( *-[tc] (\".+\"|\\S+)){2}$"),
    REPAIR("Repair"),
    SELECT_UNIT("(?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+))^Select\\s+unit\\s*( *-[xy] \\S+){2}$"),
    DESELECT_TROOP("Deselect\\s+troop");


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
