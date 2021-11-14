package entities;

/***
 * Represents a price filter class
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public interface PriceFilter {
    /**
     * Apply price filter
     * 
     * @param rawPrice 
     * @return adjusted price
     */
    public double execute(double rawPrice);
    /**
     * Returns description
     * 
     * @return description
     */
    public String getDescription();
    
}