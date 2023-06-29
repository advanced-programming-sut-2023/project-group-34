package server;

public enum ServerInputs {
    PV_MESSAGE("send message pv -user \\S+"),
    READ_PV("read pv -user \\S+"),
    EDIT_TEXT("");
    private final String input;

    ServerInputs(String input) {
        this.input = input;
    }

}
