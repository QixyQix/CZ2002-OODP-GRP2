package entities;

public interface PriceFilter{
    public double execute(double rawPrice);
    public String getDescription();
    
}