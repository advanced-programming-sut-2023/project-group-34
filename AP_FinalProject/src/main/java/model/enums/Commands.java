package model.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    CREATE_USER("\\s*user\\s+create(?=.*\\s+-u((\\s+|$)(?<username>\"[^\"]*\"|[^- ]*))?)(?=.*(\\s+-p(\\s+|$)(?<password>\"[^\"]*\"|[^- ]*)(\\s+|$)(?<passwordConfirmation>\"[^\"]*\"|[^- ]*)|\\s+-p\\s+random|\\s+-p))(?=.*\\s-e(\\s+|$)(?<email>\"[^\"]*\"|[^- ]*))(?=.*\\s-n(\\s+|$)(?<nickname>\"[^\"]*\"|[^- ]*))(?=.*\\s(?<sloganFlag>-s)\\s+(?<slogan>\"[^\"]*\"|[^- ]*))?((\\s+-[unse](\\s+(\"[^\"]*\"|[^- ]*))?)|\\s+-p|(\\s+-p\\s+(\"[^\"]*\"|[^- ]*)\\s+(\"[^\"]*\"|[^- ]*)|\\s+-p\\s+random)){4,5}"),
    PICK_QUESTION("\\s*question\\s+pick(?=.*\\s+-q(\\s+|$)(?<questionNumber>\\d*))(?=.*\\s+-a(\\s+|$)(?<answer>\"[^\"]*\"|\\S*))(?=.*\\s+-c(\\s+|$)(?<answerConfirm>\"[^\"]*\"|\\S*))(\\s+-[qac](\\s+(\"[^\"]*\"|[^- ]*))?){3}\\s*"),
    LOGIN("(?=.*-u(\\s+|$)(?<username>\"[^\"]*\"|[^- ]*))(?=.*-p(\\s+|$)(?<password>\"[^\"]*\"|[^- ]*))^\\s*user\\s+login(\\s+-[up]\\s*(\"[^\"]*\"|\\S*)|\\s+(?<flag>--stay-logged-in)){2,3}\\s*"),
    ENTER_FORGOT_PASSWORD_MENU("\\s*enter\\s+forgot\\s+password\\s+menu\\s*"),
    FORGOT_PASSWORD("\\s*forgot\\s+my\\s+password\\s+-u(\\s+|$)(?<username>\"[^\"]*\"|\\S*)\\s*"),
    LOGOUT("\\s*user\\s+logout\\s*"),
    CHANGE_USER("\\s*Profile\\s+change\\s+username\\s+-u(\\s+|$)(?<username>\"[^\"]*\"|\\S*)\\s*"),
    CHANGE_NICKNAME("\\s*Profile\\s+change\\s+-n(\\s+|$)(?<nickname>\"[^\"]*\"|\\S*)\\s*"),
    CHANGE_EMAIL("\\s*Profile\\s+change\\s+-e(\\s+|$)(?<email>\"[^\"]*\"|\\S*)\\s*"),
    CHANGE_SLOGAN("\\s*Profile\\s+change\\s+slogan\\s+-s(\\s+|$)(?<slogan>\"[^\"]*\"|\\S*)\\s*"),
    CHANGE_PASSWORD("\\s*Profile\\s+change\\s+password(?=.*\\s+-o(\\s+|$)(?<oldPass>(\"[^\"]*\"|[^- ]*)))(?=.*\\s+-n(\\s+|$)(?<newPass>(\"[^\"]*\"|[^- ]*)))(\\s+-[on]\\s*(\"[^\"]*\"|\\S*)){2}\\s*"),
    CHANGE_PASSWORD_RANDOMLY("(?=.*\\s+-o(\\s+|$)(?<oldPass>\".+\"|[^- ]*))(?=.*\\s+-n(\\s+|$)(?<newPass>random))^\\s*Profile\\s+change\\s+password(\\s+-[on]\\s*(\".+\"|\\S*)){2}\\s*$"),
    CHANGE_SLOGAN_RANDOMLY("Profile\\s+change\\s+slogan\\s+random"),
    REMOVE_SLOGAN("Profile\\s+remove\\s+slogan"),
    SET_TEXTURE("\\s*settexture(?=.*\\s+-t\\s+(?<type>\\S*))((?=.*\\s+-x(\\s+|$)(?<singleX>\\d*))(?=.*\\s+-y(\\s+|$)(?<singleY>\\d*))(\\s*-[xyt]\\s+\\S*){3}|(?=.*\\s+-x1\\s*(?<x1>\\d*))(?=.*\\s+-y1\\s*(?<y1>\\d*))(?=.*\\s+-x2\\s*(?<x2>\\d*))(?=.*\\s+-y2\\s*(?<y2>\\d*))(\\s*-([xy][12]|t)\\s+\\S*){5})\\s*"),
    CLEAR("\\s*clear(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(\\s+-[xy]\\s+\\d*){2}\\s*"),
    DROP_ROCK("\\s*droprock(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-d\\s+(?<direction>\\S*))(\\s+-[xyd]\\s+\\S*){3}\\s*"),
    DROP_TREE("\\s*droptree(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-t\\s+(?<type>\\S*))(\\s+-[xyt]\\s+\\S*){3}\\s*"),
    DROP_BUILDING("\\s*dropbuilding(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-t\\s+(?<type>.+))(\\s+-[xyt]\\s+\\S*|.+){3}\\s*"),
    DROP_UNIT("\\s*dropunit(?=.*\\s+-x\\s+(?<xIndex>\\d*))(?=.*\\s+-y\\s+(?<yIndex>\\d*))(?=.*\\s+-c\\s+(?<count>\\d*))(?=.*\\s+-t\\s+(?<type>(\\S*|\".+\")))(\\s+-[xytc]\\s+(\\S*|\".+\")){4}\\s*"),
    BACK("\\s*back\\s*"),
    DISPLAY_HIGHS_CORE("Profile\\s+display\\s+highscore"),
    DISPLAY_RANK("Profile\\s+display\\s+rank"),
    DISPLAY_PROFILE("Profile\\s+display"),
    DISPLAY_SLOGAN("Profile\\s+display\\s+slogan"),
    CURRENT_MENU("Show\\s+current\\s+menu"),
    SHOW_PRICE_LIST("Show\\s+price\\s+list"),
    BUY_ITEM("(?=.*-i\\s+(?<item>(\\S*|\".+\")))(?=.*-a\\s+(?<amount>[-]?\\d*))^Buy\\s*(\\s+-[ia]\\s+(\\S*|\".+\")){2}$"),
    SELL_ITEM("(?=.*-i\\s+(?<item>\\S*))(?=.*-a\\s+(?<amount>[-]?\\d*))^Sell\\s*(\\s+-[ia]\\s+\\S*){2}$"),
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
    NEW_GAME("New game -m (?<mapName>\".+\"|\\S*)"),
    MAP_EDITING_MENU("Enter\\s+map\\s+editing\\s+menu"),
    SHOW_MAP_LIST("Show\\s+maps"),
    EDIT_OLD_MAP("Edit -m (?<mapName>\".+\"|\\S*)"),
    NEW_MAP("New map -n (?<mapName>\".+\"|\\S*)"),
    TRADE_MENU("Enter\\s+trade\\s+menu"),
    SHOW_MAP("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Show\\s+map(\\s+-[xy]\\s+\\d*){2}$"),
    SHOW_MAP_DETAILS("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Show\\s+map\\s+details(\\s+-[xy]\\s+\\d*){2}$"),
    MOVE_MAP("map(?=.*\\s+up\\s+(?<up>-?\\d*))?(?=.*\\s+left\\s+(?<left>-?\\d*))?(?=.*\\s+right\\s+(?<right>-?\\d*))?(?=.*\\s+down\\s+(?<down>-?\\d*))?(\\s+(down|up|right|left)\\s+-?\\d*){1,4}"),
    SELECT_BUILDING("(?=.*-x (?<x>\\d+))(?=.*-y (?<y>\\d+))^Select\\s+building\\s*( *-[xy] \\S+){2}$"),
    DESELECT_BUILDING("Deselect\\s+building"),
    CREATE_UNIT("(?=.*-t (?<type>(\".+\"|\\S+)))(?=.*-c (?<count>\\d+))^Create\\s+unit\\s*( *-[tc] (\".+\"|\\S+)){2}$"),
    REPAIR("Repair"),
    SELECT_UNIT("\\s*Select\\s+unit(?=.*\\s+-x(\\s+|$)(?<x>\\d*))(?=.*\\s+-y(\\s+|$)(?<y>\\d*))(?=.*\\s+-t(\\s+|$)(?<type>\"[^\"]*\"|\\S*))(\\s+-[xty](\\s+(\"[^\"]*\"|[^- ]*))?){3}\\s*"),
    ATTACK("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Attack(\\s+-[xy]\\s+\\d*){2}$"),
    MOVE_UNIT("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Move\\s+unit(\\s+-[xy]\\s+\\d*){2}$"),
    ARIAL_ATTACK("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Arial\\s+attack(\\s+-[xy]\\s+\\d*){2}$"),
    CLOSE_BRIDGE("Close\\s+bridge"),
    OPEN_BRIDGE("Open\\s+bridge"),
    DESELECT_UNIT("Deselect\\s+unit"),
    PATROL_UNIT("(?=.*-x1\\s+(?<x1>\\d*))(?=.*-x2\\s+(?<x2>\\d*))(?=.*-y1\\s+(?<y1>\\d*))(?=.*-y2\\s+(?<y2>\\d*))^Patrol(\\s+-[xy][1-2]\\s+\\d*){4}"),
    DIG_TUNNEL("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Dig\\s+tunnel(\\s+-[xy]\\s+\\d*){2}$"),
    DISBAND_UNIT("Disband\\s+unit"),
    BUILD_EQUIPMENT("(?=.*-x(\\s+|$)(?<x>\\d*))(?=.*-y(\\s+|$)(?<y>\\d*))(?=.*-t(\\s+|$)(?<type>(\".+\"|\\S+)))^Build\\s+equipment(\\s+-[xyt]\\s+(\".+\"|\\S+)){3}$"),
    POUR_OIL("(?=.*-d(\\s+|$)(?<d>\\S+))^Pour\\s+oil(\\s+-[d]\\s+\\S+){1}$"),
    DIG_DITCH("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Dig\\s+ditch(\\s+-[xy]\\s+\\d*){2}$"),
    FILL_DITCH("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Fill\\s+ditch(\\s+-[xy]\\s+\\d*){2}$"),
    DROP_STAIRS("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Drop\\s+stairs(\\s+-[xy]\\s+\\d*){2}$"),
    DEPLOY_CAGED("Deploy\\s+caged\\s+war\\s+dog"),
    ADD_WORKER("Add\\s+worker\\s+(?<n>\\d+)"),
    BUILD_EQUIP_ON_TOWER("(?=.*-x(\\s+|$)(?<x>\\d*))(?=.*-y(\\s+|$)(?<y>\\d*))(?=.*-t(\\s+|$)(?<type>(\".+\"|\\S+)))^Build\\s+equipment\\s+on\\s+tower(\\s+-[xyt]\\s+(\".+\"|\\S+)){3}$"),
    PUT_LADDER("(?=.*-x\\s+(?<x>\\d*))(?=.*-y\\s+(?<y>\\d*))^Put\\s+ladder(\\s+-[xy]\\s+\\d*){2}$"),
    SET_TROOP_STATE("Set\\s+state(\\s+|$)(?<state>(\\S+))*"),
    FOOD_LEFT("Food\\s+left"),
    RESOURCE_LEFT("Resource\\s+left"),
    WEAPONS_LEFT("Weapons\\s+left");



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
