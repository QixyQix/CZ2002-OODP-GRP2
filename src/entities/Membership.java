package entities;

import java.io.Serializable;

/***
 * Represents a membership entity
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class Membership implements Serializable, Entities{
    /**
     * The id of this Membership
     */
    private int id;
    /**
     * The DiscountFilter associated with the discount
     */
    private DiscountFilter discount;

    /**
     * Constructor
     * @param id id of the Membership
     * @param discount discountFilter associated with the membership
     */
    public Membership(int id, DiscountFilter discount) {
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
     * @param discount discount
     */
    public void setDiscount(DiscountFilter discount) {
        this.discount = discount;
    }
}