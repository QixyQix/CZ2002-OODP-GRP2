package entities;

/***
 * Represents a customer entity
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class Customer extends Person implements Entities {
    /**
     * The membership of this Customer
     */
    private Membership membership;
    /**
     * The id of this Customer.
     */
    private int id;

    /**
     *  Constructor 
     */
    public Customer() {
        super();
    }

    /**
     * Constructor
     * @param membership    membership of the customer
     * @param customerid    id of customer
     * @param name          name of the customer
     * @param gender        gender of the customer
     * @param contact       contact number (Singapore Number) of the customer
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
