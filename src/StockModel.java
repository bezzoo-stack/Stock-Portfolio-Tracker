//child class
public class StockModel extends AbstractStock {

    private double previousPrice;

    public StockModel(String company, String industry, String symbol, double price, double previousPrice) {
        super(company, industry, symbol, price);
        this.previousPrice = previousPrice;
    }

    @Override
    public double calculateGainLoss() {
        return getPrice() - previousPrice;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }
}



