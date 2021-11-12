package entities;


public class Customer extends Person implements Entities {
    private Membership membership;
    private int id;

    public Customer(){
        super();
    }

    public Customer(Membership membership, int customerid, String name, String gender, String contact) {
        super(name, gender, contact);
        this.membership = membership;
        this.id = customerid;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
    
    public int getId() {
        return this.id;
    }

  

}
