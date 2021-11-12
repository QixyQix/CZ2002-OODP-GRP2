package entities;

import java.io.Serializable;

public class Membership implements Serializable, Entities{
    private Integer id;
    private DiscountFilter discount;

    public Membership(Integer id, DiscountFilter discount) {
        this.discount = discount;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    

    public DiscountFilter getDiscount() {
        return this.discount;
    }

    public void setDiscount(DiscountFilter discount) {
        this.discount = discount;
    }
}