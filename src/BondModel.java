public class BondModel extends AbstractStock {
    private double faceValue;
    private double interestRate;

    public BondModel(String company, String industry, String symbol, 
                    double price, double faceValue, double interestRate) {
        super(company, industry, symbol, price);
        this.faceValue = faceValue;
        this.interestRate = interestRate;
    }

    //polymorphism
    @Override
    public double calculateGainLoss() {
        //Bonds calculate gain/loss differently than stocks
        return (faceValue * interestRate / 100) + (faceValue - getPrice());
    }

    public double getFaceValue() { return faceValue; }
    public double getInterestRate() { return interestRate; }
}
