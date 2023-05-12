package model.human;

import controller.GameController;
import model.building.Building;
import model.building.DefenciveBuilding;
import model.building.OilSmelter;
import model.government.Government;
import model.map.Block;

import java.util.ArrayList;

public class Engineer extends Human{
    private boolean isEquippedWithOil = false;
    public Engineer(Block block, Government government) {
        super(0, 0 , block, 0, 0, false, true , government , 4);
        if(government != null) useOil();
    }

    public boolean isEquippedWithOil() {
        return isEquippedWithOil;
    }

    public void setEquippedWithOil(boolean equippedWithOil) {
        isEquippedWithOil = equippedWithOil;
    }
    public void AutomaticAttack() {
        int counter = 0;
        int x = getBlock().getLocationI();
        int y = getBlock().getLocationJ();
        Block tempBlock;
        int numberOfOpponent;
        int tempDamage;
        switch (getTroopStage()) {
            case AGGRESSIVE -> {
                tempDamage = getCurrentDamage();
                numberOfOpponent = 1;
            }
            case DEFENSIVE -> {
                numberOfOpponent = 3;
                tempDamage = (getCurrentDamage() * 8) / 10;
            }
            default -> {
                numberOfOpponent = 5;
                tempDamage = (getCurrentDamage() * 7) / 10;
            }
        }
        if(!this.getBlock().getBuilding().isEmpty() && (this.getBlock().getBuilding().get(0) instanceof DefenciveBuilding defenciveBuilding)) {
            tempDamage += defenciveBuilding.getDamage();
        }
        for (int i = x - 3 ; i <= x + 3; i++) {
            for (int j = y - 3 ; j <= y + 3 ; j++) {
                if(!GameController.getGame().getMap().checkBounds(i , j)) continue;
                tempBlock = GameController.currentGame.getMap().getABlock(i , j);
                ArrayList<Human> humans = tempBlock.getHumans();
                for (int k = humans.size() - 1; k >= 0; k--) {
                    Human human = humans.get(k);
                    if (human.getGovernment().equals(getGovernment()) || !human.isVisible()) continue;
                    counter++;
                }
            }
        }
        if(counter < numberOfOpponent) return;
        for (int i = x - 3 ; i <= x + 3; i++) {
            for (int j = y - 3 ; j <= y + 3 ; j++) {
                if(!GameController.getGame().getMap().checkBounds(i , j)) continue;
                tempBlock = GameController.currentGame.getMap().getABlock(i , j);
                ArrayList<Human> humans = tempBlock.getHumans();
                for (int k = humans.size() - 1; k >= 0; k--) {
                    Human human = humans.get(k);
                    if (human.getGovernment().equals(getGovernment()) || !human.isVisible()) continue;
                    human.getHit(tempDamage);
                }
            }
        }
    }

    public void useOil() {
        boolean flag = false;
        for(Building building : getGovernment().getBuildings()) {
            if(!(building instanceof OilSmelter oilSmelter)) {
                continue;
            }
            if(oilSmelter.getNumberOfOils() < 1 || oilSmelter.getEngineer() == null) {
                continue;
            }
            flag = true;
            oilSmelter.giveOil();
            isEquippedWithOil = true;
        }
        if(!flag) isEquippedWithOil = false;
    }

}
