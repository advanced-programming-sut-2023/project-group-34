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
            } else if (Commands.getOutput(command, Commands.TRADE_MENU) != null){
                System.out.println("You just entered trade menu");
                return "trade menu";
            } else if (Commands.getOutput(command, Commands.SHOP_MENU) != null){
                System.out.println("You just entered shop menu");
                return "shop menu";
            } else if (Commands.getOutput(command, Commands.CURRENT_MENU) != null){
                System.out.println("Gaming Menu");
            } else if ((matcher = Commands.getOutput(command, Commands.MOVE_MAP)) != null){
                System.out.println(GameController.moveMiniMap(matcher));
            } else if (Commands.getOutput(command, Commands.SHOW_MAP) != null){
                String response = GameController.showMiniMap();
                if (!response.equals("Wrong coordinates")) {
                    System.out.println(response);
                    return "map menu";
                }
                System.out.println(response);
            }
        }
    }
}
