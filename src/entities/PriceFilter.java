package entities;

/***
 * Represents a price filter class
 * 
 * @author Lim Yan Kai
 */
public interface PriceFilter {
    /**
     * Apply price filter
     * 
     * @param rawPrice
     * @return discounted price
     */
    public double execute(double rawPrice);
    /**
     * Returns description
     * 
     * @return description
     */
    public String getDescription();
    
}