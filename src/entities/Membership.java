package entities;

import java.io.Serializable;

public class Membership implements Serializable{
    private DiscountFilter discount;

    public Membership(DiscountFilter discount) {
        this.discount = discount;
    }

    public DiscountFilter getDiscount() {
        return this.discount;
    }

    public void setDiscount(DiscountFilter discount) {
        this.discount = discount;
    }
    
}