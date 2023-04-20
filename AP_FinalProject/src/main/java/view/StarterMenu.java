package view;

import model.enums.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StarterMenu {


    public static String run(){
        String input;
        Matcher matcher;
        while (true){
            input = controller.Runner.getScn ().nextLine ();
            input = input.trim ();
            if ((matcher = model.enums.Commands.getOutput (input, Commands.CREATE_USER)) != null) {

            }
            else if ((matcher = Commands.getOutput (input, Commands.LOGIN)) != null) {

            }
            else if ((matcher = Commands.getOutput (input, Commands.FORGOT_PASSWORD)) != null) {

            }
            else {

            }
        }
    }


}
