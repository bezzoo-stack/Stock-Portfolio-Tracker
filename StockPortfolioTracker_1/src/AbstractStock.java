//parent class
public abstract class AbstractStock {

    private String company;
    private String industry;
    private String symbol;
    private double price;

    public AbstractStock(String company, String industry, String symbol, double price) {
        this.company = company;
        this.industry = industry;
        this.symbol = symbol;
        this.price = price;
    }

    // ABSTRACT METHOD (required in OOP)
    public abstract double calculateGainLoss();

    // Getters
    public String getCompany() { return company; }
    public String getIndustry() { return industry; }
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
}


