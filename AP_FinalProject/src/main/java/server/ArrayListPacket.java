package server;

import model.messenger.Chat;

import java.util.ArrayList;

public class ArrayListPacket {
    public final ArrayList<Chat> arrayList;

    public ArrayListPacket(ArrayList<Chat> arrayList) {
        this.arrayList = arrayList;
    }
}
