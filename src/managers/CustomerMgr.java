package managers;

import java.io.IOException;
import java.util.HashMap;
import entities.Customer;
import entities.Entities;
import entities.Membership;
import exceptions.IDNotFoundException;

/***
 * Represents a customer manager
 * 
 * @author Lee Zong Yu 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public final class CustomerMgr extends DataMgr {
    /**
     * The Instance of this CustomerMgr
     */
    private static CustomerMgr INSTANCE;
    /**
     * The mapping of Customer ID to its respective object
     */
    private HashMap<Integer, Customer> customers;
    /**
     * The mapping of Contact Number to its respective Customer ID
     */
    private HashMap<String, Integer> phonetoid;
    /**
     * The next Id to be use in creating Customer
     */
    private int nextId;

    /**
     * Constructor
     */
    private CustomerMgr() {
        try {
            this.customers = new HashMap<Integer, Customer>();
            this.phonetoid = new HashMap<String, Integer>();

            downCast(super.loadSavedData("customers"));
            this.nextId = super.loadNextIdData("CustomerNextId");

            this.convertToPhone();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load customers data");
        }
    };

    /**
     * Downcast from entities to customer
     * 
     * @param object the entities to downcast
     */
    public void downCast(HashMap<Integer, Entities> object) {
        for (int id : object.keySet()) {
            if (object.get(id) instanceof Customer)
                this.customers.put(id, (Customer) object.get(id));
            else
                throw new ClassCastException();
        }
    }

    /**
     * Upcast customer to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast() {
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for (int id : customers.keySet()) {
            object.put(id, customers.get(id));
        }
        return object;
    }

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        super.saveDataSerialize(upCast(), nextId, "customers", "CustomernextId");
    }

    /**
     * Convert phone number to customer id
     * 
     */
    private void convertToPhone() {
        for (int id : customers.keySet()) {
            Customer cus = (Customer) customers.get(id);
            phonetoid.put(cus.getContact(), id);
        }
    }

    /**
     * Returns the CustomerMgr instance and creates an instance if it does not exist
     * 
     * @return CustomerMgr instance
     */
    public static CustomerMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerMgr();
        }
        return INSTANCE;
    }

    /**
     * Returns hashmap of invoice id and Customer object
     * 
     * @return hashmap of invoice id and Customer object
     */
    public HashMap<Integer, Customer> getCustomersMap() {
        return this.customers;
    }

    /**
     * Creates Customer object
     * 
     * @param membership customer membership
     * @param name       customer name
     * @param gender     customer gender
     * @param contact    customer contact number
     * @return Customer object if customer has not been created before, null if
     *         customer data exists
     */
    public Customer createCustomer(Membership membership, String name, String gender, String contact) {

        Customer customer = new Customer(membership, nextId, name, gender, contact);

        this.phonetoid.put(contact, nextId);
        this.customers.put(nextId, customer);

        nextId++;
        return customer;
    }

    /**
     * Returns Customer object corresponding to the contact number
     * 
     * @param phoneNumber customer contact number
     * @return Customer object corresponding to the phone number
     */
    public Customer getExistingCustomer(String phoneNumber) {
        int cusid = this.phonetoid.get(phoneNumber);

        return this.customers.get(cusid);
    }

    /**
     * Check if customer exists by contact number
     * 
     * @param phoneNumber customer contact number
     * @return true if Customer object exists, false if customer does not exist
     */
    /*
     * public boolean checkExistingCustomer(String phoneNumber) { return
     * this.phonetoid.containsKey(phoneNumber); }
     */
    /**
     * Update customer membership
     * 
     * @param phoneNumber customer contact number
     * @param membership  customer membership
     * @throws IDNotFoundException if no such Customer corresponding to the id
     *                             exists
     */
    public void updateMembership(String phoneNumber, Membership membership) throws IDNotFoundException {
        Customer customer = getExistingCustomer(phoneNumber);

        customer.setMembership(membership);
        return;

    }

}
