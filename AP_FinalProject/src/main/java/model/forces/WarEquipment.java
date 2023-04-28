package model.forces;

public interface WarEquipment {
    int getFireRange();
    void setVisible(boolean visible);
    void fight(WarEquipment opponent);
    void addRange(int amount);
    void die();

}
