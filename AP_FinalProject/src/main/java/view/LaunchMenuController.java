package view;

import com.google.gson.Gson;
import javafx.scene.input.MouseEvent;
import model.Connect;
import model.user.User;
import view.main.MainMenu;
import view.starter.RegisterAndLoginMenu;

import java.io.IOException;
import java.util.Scanner;

public class LaunchMenuController {
    public static Connect connect = new Connect();

    public static void authentication() {
        Scanner scanner = new Scanner(System.in);
        String nextLine;
        System.out.println("enter your token or q to continue");
        while (true) {
            nextLine = scanner.nextLine();
            if(nextLine.equals("q")) break;
            String username = connect.getUsernameFromToken(nextLine);
            if(username == null) {
                System.out.println("failed try again!");
                continue;
            }
            try {
                LaunchMenu.dataOutputStream.writeUTF("get user -username " + username);
                User.currentUser = new Gson().fromJson(LaunchMenu.dataInputStream.readUTF() , User.class);
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void launchTheGame(MouseEvent mouseEvent) throws Exception {
        if (User.currentUser == null){
            new RegisterAndLoginMenu().start(LaunchMenu.getStage());
        } else {
            new MainMenu().start(LaunchMenu.getStage());
        }
    }
}
