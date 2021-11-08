package entities;

public class Customer extends Person {
    private Membership membership;
    private int customerid;

    public Customer(Membership membership, int customerid, String name, String gender, String contact) {
        super(name, gender, contact);
        this.membership = membership;
        this.customerid = customerid;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
    
    public int getCustomerid() {
        return this.customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

}
