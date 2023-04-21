package model.government;

public class AccountingDepartment {
    private final Government government;
    private int foodRate;
    private int fearRate;
    private int taxRate;

    public int  foodAccounting(){return  0;}
    public int  fearAccounting() {return  0;}
    public int  taxAccounting() {return  0;}
    public int religionAccounting(){return 0;}
    private int populationAccounting() {
        return 0;
    }

    private void changeCurrentPopulation(int change) {

    }
    public int addGovernmentPopularity() {
        return taxAccounting()+foodAccounting()+fearAccounting()+religionAccounting();
    }

    private void buildingAccounting() {}
    public void nextTurnForThisUser(){}

    public AccountingDepartment(Government government) {
        this.government = government;
    }

    public Government getGovernment() {
        return government;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }


    public void nextTurn() {}
}
