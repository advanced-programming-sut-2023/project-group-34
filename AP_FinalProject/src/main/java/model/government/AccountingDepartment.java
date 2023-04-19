package model.government;

public class AccountingDepartment {
    private final Government government;
    private int foodRate;
    private int fearRate;
    private int taxRate;

    public int  foodAccounting(){return  0;}
    public int  fearAccounting() {return  0;}
    public int  taxAccounting() {return  0;}
    private int populationAccounting() {
        return 0;
    }

    private void changeCurrentPopulation(int change) {

    }
    public void addGovernmentPopularity() {

    }

    private void buildingAccounting() {}
    public void nextTurnForThisUser(){}

    public AccountingDepartment(Government government) {
        this.government = government;
    }

    public Government getGovernment() {
        return government;
    }

    public void nextTurn() {}
}
