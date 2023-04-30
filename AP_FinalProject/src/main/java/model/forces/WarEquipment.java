package model.forces;

import model.government.Government;
import model.map.Block;

public interface WarEquipment {
    int getFireRange();
    void fight()
    void setVisible(boolean visible);
    void fight(WarEquipment opponent);
    void addRange(int amount);
    Block getBlock();
    void getHurt(int amount);
    int getDamage();
    int getHP();
    void die();
    Government getGovernment();

}
