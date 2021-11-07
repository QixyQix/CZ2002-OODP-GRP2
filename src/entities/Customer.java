package entities;

public class Customer {
    private Membership membership;
    private String name;
    private String customerid;

    public Customer(Membership membership, String name, String customerid) {
        this.membership = membership;
        this.name = name;
        this.customerid = customerid;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerid() {
        return this.customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

}
