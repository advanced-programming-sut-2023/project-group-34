package model.messenger;

import java.util.ArrayList;

public class Chat {
    public static int ChatCounter = 0;
    private final int ID;
    private final ArrayList<Message> messages = new ArrayList<>();
    Chat() {
        ChatCounter++;
        ID = ChatCounter;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
