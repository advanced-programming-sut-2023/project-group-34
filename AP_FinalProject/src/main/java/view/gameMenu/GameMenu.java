package view.gameMenu;

import controller.GameController;
import controller.MainController;
import controller.Runner;
import model.building.Maker;
import model.building.MakerType;
import model.enums.Commands;
import model.user.User;
import org.checkerframework.checker.units.qual.C;

import java.util.regex.Matcher;

public class GameMenu {
    public static String run(){
        Matcher matcher;

        while (true){
            if (GameController.selectedBuilding != null && GameController.selectedBuilding.getBuildingType().equals(MakerType.SHOP)){
                return "shop menu";
            }
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
            } else if (Commands.getOutput(command, Commands.CURRENT_MENU) != null){
                System.out.println("Gaming Menu");
            } else if ((matcher = Commands.getOutput(command, Commands.MOVE_MAP)) != null){
                System.out.println(GameController.moveMiniMap(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.SHOW_MAP)) != null){
                String response = GameController.showMiniMap(matcher);
                if (!response.equals("Wrong coordinates")) {
                    System.out.println(response);
                    return "map menu";
                }
                System.out.println(response);
            } else if ((matcher = Commands.getOutput(command, Commands.SELECT_BUILDING)) != null){
                System.out.println(GameController.selectBuilding(matcher));
            } else if ((Commands.getOutput(command, Commands.REPAIR) != null)){
                System.out.println(GameController.repairCurrentBuilding());
            } else if ((matcher = Commands.getOutput(command, Commands.SELECT_UNIT)) != null){
                System.out.println(GameController.selectUnits(matcher));
            } else if (Commands.getOutput(command, Commands.DESELECT_BUILDING) != null){
                System.out.println(GameController.deselectBuilding());
            } else if (Commands.getOutput(command, Commands.DESELECT_UNIT) != null){
                System.out.println(GameController.deselectUnits());
            } else if ((matcher = Commands.getOutput(command, Commands.CREATE_UNIT)) != null){
                System.out.println(GameController.createUnit(matcher));
            } else if((matcher = Commands.getOutput(command, Commands.MOVE_UNIT)) != null){
                System.out.println(GameController.moveSelectedUnits(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.ARIAL_ATTACK)) != null){
                System.out.println(GameController.arialAttack(matcher));
            } else if (Commands.getOutput(command, Commands.CLOSE_BRIDGE) != null){
                System.out.println(GameController.closeBridge());
            } else if ((matcher = Commands.getOutput(command, Commands.PATROL_UNIT)) != null){
                System.out.println(GameController.patrolUnit(matcher));
            } else if (Commands.getOutput(command, Commands.OPEN_BRIDGE) != null){
                System.out.println(GameController.openBridge());
            } else if ((matcher = Commands.getOutput(command, Commands.ATTACK)) != null){
                System.out.println(GameController.attackTheBlock(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.POUR_OIL))!= null){
                System.out.println(GameController.pourOil(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.DROP_STAIRS)) != null){
                System.out.println(GameController.dropStairs(matcher));
            } else if (Commands.getOutput(command, Commands.DEPLOY_CAGED) != null) {
                System.out.println(GameController.deployCagedWarDog());
            } else if ((matcher = Commands.getOutput(command, Commands.ADD_WORKER)) != null){
                System.out.println(GameController.addWorker(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.DIG_TUNNEL)) != null){
                System.out.println(GameController.digTunnel(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.DIG_DITCH)) != null){
                System.out.println(GameController.digDitch(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.BUILD_EQUIP_ON_TOWER)) != null){
                System.out.println(GameController.buildEquipmentOnTower(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.PUT_LADDER)) != null) {
                System.out.println(GameController.putLadder(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.BUILD_EQUIPMENT)) != null) {
                System.out.println(GameController.buildEquipment(matcher));
            } else if (Commands.getOutput(command, Commands.DISBAND_UNIT) != null){
                System.out.println(GameController.disband());
            } else if ((matcher = Commands.getOutput(command, Commands.FILL_DITCH)) != null) {
                System.out.println(GameController.fillDitch(matcher));
            } else if (command.equals("exit")){
                System.out.println("Are you sure you want to finish this game?");
                String response = Runner.getScn().nextLine();
                if (response.equals("yes")) {
                    for (User user : GameController.currentGame.getPlayers())
                        user.setGovernment(null);
                    GameController.currentGame = null;
                    return "back";
                }
            } else if ((matcher = Commands.getOutput(command, Commands.DROP_BUILDING)) != null){
                System.out.println(MainController.dropBuilding(GameController.currentGame.getMap(), matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.SET_TROOP_STATE)) != null){
                System.out.println(GameController.setTroopState(matcher));
            } else if(Commands.getOutput(command, Commands.FOOD_LEFT) != null){
                System.out.println(GameController.foodResource());
            } else if (Commands.getOutput(command, Commands.RESOURCE_LEFT) != null){
                System.out.println(GameController.Resource());
            } else if (Commands.getOutput(command, Commands.WEAPONS_LEFT) != null){
                System.out.println(GameController.weaponsResource());
            } else {
                System.out.println("Invalid command!");
            }
            if (GameController.currentGame.getPlayers().size() == 1){
                String username = GameController.currentGame.getPlayers().get(0).getName();
                GameController.currentGame.getPlayers().get(0).setScore(GameController.currentGame.getPlayers().get(0).getScore()+100);
                //TODO set every player's government in keep destroy method to null after they lose
                //TODO reset the map
                GameController.currentGame.getPlayers().get(0).setGovernment(null);
                GameController.currentGame.getPlayers().clear();
                System.out.println(username + ", You are the winner. Congratulation!");
                return "back";
            }
        }
    }
}
