package view;

import model.enums.Commands;

import java.util.regex.Matcher;

public class MainMenu {
    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = controller.Runner.getScn ().nextLine ();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput(input, Commands.LOGOUT)) != null) {

            }
            else {

            }
        }
    }
}
