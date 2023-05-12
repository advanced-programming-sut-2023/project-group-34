package view.gameMenu;

import controller.*;
import model.building.MakerType;
import model.enums.Commands;
import model.user.User;

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
            } else if ((matcher = Commands.getOutput(command, Commands.MOVE_UNIT)) != null) {
                System.out.println(GameController.moveUnit(matcher));
            } else if ((Commands.getOutput(command, Commands.REPAIR) != null)){
                System.out.println(BuildingController.repairCurrentBuilding());
            } else if ((matcher = Commands.getOutput(command, Commands.SELECT_UNIT)) != null){
                System.out.println(TroopsController.selectUnits(matcher));
            } else if (Commands.getOutput(command, Commands.DESELECT_BUILDING) != null){
                System.out.println(GameController.deselectBuilding());
            } else if (Commands.getOutput(command, Commands.DESELECT_UNIT) != null){
                System.out.println(TroopsController.deselectUnits());
            } else if ((matcher = Commands.getOutput(command, Commands.CREATE_UNIT)) != null){
                System.out.println(TroopsController.createUnit(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.ARIAL_ATTACK)) != null){
                System.out.println(WarController.arialAttack(matcher));
            } else if (Commands.getOutput(command, Commands.CLOSE_BRIDGE) != null){
                System.out.println(BuildingController.closeBridge());
            } else if ((matcher = Commands.getOutput(command, Commands.PATROL_UNIT)) != null){
                System.out.println(TroopsController.patrolUnit(matcher));
            } else if (Commands.getOutput(command, Commands.OPEN_BRIDGE) != null){
                System.out.println(BuildingController.openBridge());
            } else if ((matcher = Commands.getOutput(command, Commands.ATTACK)) != null){
                System.out.println(WarController.attackTheBlock(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.POUR_OIL))!= null){
                System.out.println(WarController.pourOil(matcher));
            } else if (Commands.getOutput(command, Commands.DEPLOY_CAGED) != null) {
                System.out.println(BuildingController.deployCagedWarDog());
            } else if ((matcher = Commands.getOutput(command, Commands.ADD_WORKER)) != null){
                System.out.println(BuildingController.addWorker(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.DIG_TUNNEL)) != null){
                System.out.println(WarController.digTunnel(matcher));
            } else if (Commands.getOutput(command, Commands.STOP_UNIT) != null) {
                System.out.println(GameController.stopSelectedUnits());
            } else if ((matcher = Commands.getOutput(command, Commands.DIG_DITCH)) != null){
                System.out.println(WarController.digDitch(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.BUILD_EQUIP_ON_TOWER)) != null){
                System.out.println(GameController.buildEquipmentOnTower(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.PUT_LADDER)) != null) {
                System.out.println(BuildingController.putLadder(matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.BUILD_EQUIPMENT)) != null) {
                System.out.println(TroopsController.buildEquipment(matcher));
            } else if (Commands.getOutput(command, Commands.DISBAND_UNIT) != null){
                System.out.println(TroopsController.disband());
            } else if ((matcher = Commands.getOutput(command, Commands.FILL_DITCH)) != null) {
                System.out.println(WarController.fillDitch(matcher));
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
                System.out.println(BuildingController.dropBuilding(GameController.currentGame.getMap(), matcher));
            } else if ((matcher = Commands.getOutput(command, Commands.SET_TROOP_STATE)) != null){
                System.out.println(TroopsController.setTroopState(matcher));
            } else if(Commands.getOutput(command, Commands.FOOD_LEFT) != null){
                System.out.println(GameController.foodResource());
            } else if (Commands.getOutput(command, Commands.RESOURCE_LEFT) != null){
                System.out.println(GameController.Resource());
            } else if (Commands.getOutput(command, Commands.WEAPONS_LEFT) != null){
                System.out.println(GameController.weaponsResource());
            } else if ((matcher = Commands.getOutput(command, Commands.DROP_UNIT)) != null){
                System.out.println(TroopsController.dropUnit(GameController.currentGame.getMap(), matcher));
            } else if (Commands.getOutput(command, Commands.NEXT_TURN) != null){
                System.out.println(GameController.nextTurn());
            } else if (Commands.getOutput(command, Commands.SHOW_CURRENT_POPULATION) != null){
                System.out.println(GameController.populationInformation());
            } else if ((matcher = Commands.getOutput(command, Commands.BATTERING_RAM)) != null){
                System.out.println(GameController.deployBatteringRam(matcher));
            } else {
                System.out.println("Invalid command!");
            }
            if (GameController.currentGame.getPlayers().size() == 1){
                String username = GameController.currentGame.getPlayers().get(0).getName();
                GameController.currentGame.getPlayers().get(0).setScore(GameController.currentGame.getPlayers().get(0).getScore()+100);
                GameController.currentGame.getPlayers().get(0).setGovernment(null);
                GameController.currentGame = null;
                System.out.println(username + ", You are the winner. Congratulation!");
                return "back";
            }
        }
    }
}
