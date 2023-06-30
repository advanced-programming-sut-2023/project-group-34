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
    GET_CHATS("get chats");

    private final String input;

    ServerInputs(String input) {
        this.input = input;
    }
    public Matcher getMatcher(String command) {
        return Pattern.compile(this.input).matcher(command);
    }
}
