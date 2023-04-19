package view;

import controller.MainController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class ProfileMenu {
    public static String run(){
        Matcher matcher;
        while (true){
            String command = Runner.getScn().nextLine();
            command = command.trim();
            if ((matcher = Commands.getOutput(command, Commands.CHANGE_USER)) != null){
                System.out.println(MainController.changeUsername(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_NICKNAME)) != null){
                System.out.println(MainController.changeNickname(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.CHANGE_EMAIL)) != null){
                System.out.println(MainController.changeEmail(matcher));
            }
        }
    }
}
