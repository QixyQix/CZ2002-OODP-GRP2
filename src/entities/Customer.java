package entities;

public class Customer extends Person {
    private Membership membership;
    private String customerid;

    public Customer(Membership membership, String customerid, String name, String gender, int contact) {
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
    
    public String getCustomerid() {
        return this.customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

}
