package view.gameMenu;

import controller.GameController;
import controller.Runner;
import model.enums.Commands;

import java.util.regex.Matcher;

public class GameMenu {
    public static String run(){
        Matcher matcher;

        while (true){
            String command = Runner.getScn().nextLine();
            command = command.trim();

            if (Commands.getOutput(command, Commands.SHOW_POPULARITY_FACTORS) != null){
                System.out.println(GameController.showPopularityFactors());
            } else if (Commands.getOutput(command, Commands.SHOW_POPULARITY) != null){
                System.out.println(GameController.showPopularity());
            } else if (Commands.getOutput(command, Commands.SHOW_FOOD_LIST) != null){
                System.out.println(GameController.showFoodList());
            } else if ((matcher = Commands.getOutput(command,Commands.SET_FOOD_RATE))!= null){
                System.out.println(GameController.setFoodRate(matcher));
            } else if (Commands.getOutput(command, Commands.SHOW_FOOD_RATE) != null){
                System.out.println(GameController.showFoodRate());
            } else if ((matcher = Commands.getOutput(command, Commands.SET_TAX_RATE)) != null){
                System.out.println(GameController.setTaxRate(matcher));
            } else if (Commands.getOutput(command, Commands.SHOW_TAX_RATE) != null){
                System.out.println(GameController.showTaxRate());
            } else if ((matcher = Commands.getOutput(command, Commands.SET_FEAR_RATE)) != null){
                System.out.println(GameController.setFearRate(matcher));
            } else if (Commands.getOutput(command, Commands.SHOW_FEAR_RATE) != null){
                System.out.println(GameController.showFearRate());
            }
        }
    }
}
