package model.government;

import model.building.Building;
import model.human.Human;
import model.user.Color;
import model.user.User;

import java.util.ArrayList;

public class Government {
    private final User owner;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Human> humans = new ArrayList<>();
    private int totalPopularity;
    private int population;
    private int maxPopulation;
    private final Color color;
    private final StorageDepartment storageDepartment = new StorageDepartment();
    private final AccountingDepartment accountingDepartment = new AccountingDepartment(this);

    public Government(User owner,String color) {
        this.owner = owner;
        this.color = new Color(color , this);
    }

    public void setTotalPopularity(int totalPopularity) {
        this.totalPopularity = totalPopularity;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void changeMaxPopulation(int amount) {
        this.maxPopulation += amount;
    }

    public void addBuilding(Building building){
        buildings.add(building);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addToHuman(Human human){
        humans.add(human);
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public int getTotalPopularity() {
        return totalPopularity;
    }

    public User getOwner() {
        return owner;
    }

    public AccountingDepartment getAccountingDepartment() {
        return accountingDepartment;
    }

    public StorageDepartment getStorageDepartment() {
        return storageDepartment;
    }


    public Color getColor() {
        return color;
    }
}
