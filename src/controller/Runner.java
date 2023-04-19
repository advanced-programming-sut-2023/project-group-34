package src.controller;

import src.model.user.User;

import java.util.Scanner;

public class Runner {
    public static Scanner scn = new Scanner(System.in);
    public static void run(){
        while (true) {
            if (!User.getCurrentUser().getLoggedIn()) { //This part checks if the user was logged in before
                if (UserController.run().equals("exit")) return;
            }
            if (MainController.run().equals("game menu")) {
                GameController.run();
            }
        }
    }
}
