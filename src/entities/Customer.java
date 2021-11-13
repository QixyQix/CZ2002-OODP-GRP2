package entities;

/***
 * Represents a customer entity
 * 
 * @author Zong Yu Lee
 * @author Lim Yan Kai
 */
public class Customer extends Person implements Entities {
    private Membership membership;
    private int id;

    public Customer() {
        super();
    }

    /**
     * Constructor
     */
    public Customer(Membership membership, int customerid, String name, String gender, String contact) {
        super(name, gender, contact);
        this.membership = membership;
        this.id = customerid;
    }

    /**
     * Returns membership object
     * 
     * @return membership object
     */
    public Membership getMembership() {
        return this.membership;
    }

    /**
     * Sets membership object
     * 
     * @param membership membership object
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Returns id object
     * 
     * @return id object
     */
    public int getId() {
        return this.id;
    }

}
