package model;

import com.google.gson.Gson;
import model.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayersToPlay {
    public static ArrayList<User> usersToPlay = new ArrayList<>();


    public static ArrayList<User> getUsersToPlay() {
        return usersToPlay;
    }


    public static void updateDataBase() {
        String usersListAsJSON = new Gson().toJson(usersToPlay);
        try (FileWriter file = new FileWriter("userstoplay.json")) {
            file.write(usersListAsJSON);
        } catch (IOException ignored) {
        }
    }
}
