package entities;

import java.io.Serializable;

public class Membership implements Serializable{
    private Integer id;
    private DiscountFilter discount;

    public Membership(Integer id, DiscountFilter discount) {
        this.discount = discount;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DiscountFilter getDiscount() {
        return this.discount;
    }

    public void setDiscount(DiscountFilter discount) {
        this.discount = discount;
    }
}