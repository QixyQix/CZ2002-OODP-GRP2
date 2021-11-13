package entities;

import java.io.Serializable;

/***
 * Represents a membership entity
 * 
 * @author Lim Yan Kai
 */
public class Membership implements Serializable, Entities{
    private Integer id;
    private DiscountFilter discount;

    /**
     * Constructor
     */
    public Membership(Integer id, DiscountFilter discount) {
        this.discount = discount;
        this.id = id;
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    

    /**
     * Returns discount filter
     * 
     * @return discount
     */
    public DiscountFilter getDiscount() {
        return this.discount;
    }

    /**
     * Set discount
     * 
     * @return discount
     */
    public void setDiscount(DiscountFilter discount) {
        this.discount = discount;
    }
}