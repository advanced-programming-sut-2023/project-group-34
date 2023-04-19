package model.government;

import model.user.User;

public class Government {
    private final User owner;
    private int totalPopularity;
    private int population;
    private int maxPopulation;
    private final StorageDepartment storageDepartment = new StorageDepartment();
    private final AccountingDepartment accountingDepartment = new AccountingDepartment(this);

    public Government(User owner) {
        this.owner = owner;
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

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
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
}
