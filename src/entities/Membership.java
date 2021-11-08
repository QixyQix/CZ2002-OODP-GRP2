package entities;

public class Membership {
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