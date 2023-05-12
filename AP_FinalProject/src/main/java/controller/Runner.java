package controller;


import model.user.User;

import java.util.Random;
import java.util.Scanner;

public class Runner {
    public static Scanner scn = new Scanner(System.in);
    public static Random random = new Random();
    
    public static void run () {
        while (true) {
            if (User.getCurrentUser() == null) {
                if (UserController.run().equals("exit")) return;
            }
            if (MainController.run().equals("game menu")) {
                GameController.run();
            }
        }
    }
    
    public static Scanner getScn () {
        return scn;
    }
    
    public static int getRandomNumber (int cases) {
        return random.nextInt(0, cases);
    }
}
