package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ServerInputs {
    CREATE_GROUP("create group -name (?<name>\\S+)"),
    CREATE_PV("create pv"),
    GET_CHAT("get chat -id (?<id>\\d+)"),
    UPDATE_CHAT("update chat"),
    ADD_USER("add user to group -id (?<id>\\d+) -u (?<username>\\S+)"),
    GET_USER("get user -username (?<username>\\S+)"),
    USER_LOGIN("user login"),
    GET_PUBLIC_CHAT("get public chat"),
    GET_USERS("get users"),
    SEND_FRIEND_REQUEST("send req -username (?<username>\\S+)"),
    GET_REQUESTS("get requests"),
    ANSWER_REQUEST("answer request -id (?<id>\\d+) -answer (?<answer>\\w+)"),
    GET_LOBBIES("get lobbies"),
    GET_LOBBY("get lobby -id (?<id>\\d+)"),
    START_GAME("start game -id (?<id>\\d+)"),
    JOIN_LOBBY("join lobby -id (?<id>\\d+) -username (?<username>\\w+)"),
    DELETE_LOBBY("delete lobby -id (?<id>\\d+)"),
    CHANGE_LOBBY_STATUS("change lobby -id (?<id>\\d+) (?<status>\\w+)"),
    CREATE_LOBBY("create lobby -name (?<name>\\w+) -id (?<id>\\d+)"),
    GET_CHATS("get chats");

    private final String input;

    ServerInputs(String input) {
        this.input = input;
    }
    public Matcher getMatcher(String command) {
        return Pattern.compile(this.input).matcher(command);
    }
}
