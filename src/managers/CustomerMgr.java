package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import entities.Customer;
import entities.Entities;
import entities.Membership;

/***
 * Represents a customer manager
 * 
 * @author Zong Yu Lee
 * @author Lim Yan Kai
 */
public final class CustomerMgr extends DataMgr{

    private static CustomerMgr instance;
    private HashMap<Integer, Customer> customers;
    private HashMap<String, Integer> phonetoid;
    private int nextId;

    private CustomerMgr() {
        try {
            this.customers = new HashMap<Integer, Customer>();
            this.phonetoid = new HashMap<String, Integer>();
            
            downcast(super.loadSavedData("customers"));
            this.nextId  = super.loadNextIdData("CustomernextId");

            this.convertToPhone();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load customers data");
        }
    };

    private void downcast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Customer)
                this.customers.put(id,(Customer) object.get(id));
            else throw new ClassCastException();
        }
    }

    private HashMap<Integer, Entities> upcast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: customers.keySet()){
           object.put(id,customers.get(id)); 
        }
        return object;
    }

    public void saveData() throws IOException{
        super.saveDataSerialize(upcast(), nextId, "customers","CustomernextId");
    }
    
    private void convertToPhone(){
        for(int id : customers.keySet()){
            Customer cus = (Customer)customers.get(id);
            phonetoid.put(cus.getContact(), id);
        }
    }

    

    /**
     * Returns the CustomerMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static CustomerMgr getInstance() {
        if (instance == null) {
            instance = new CustomerMgr();
        }
        return instance;
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
        
        return (Customer)this.customers.get(cusid);
    }

    /**
     * Check if customer exists by contact number
     * 
     * @param phoneNumber customer contact number
     * @return true if Customer object exists, false if customer does not exist
     */
    public boolean checkExistingCustomer(String phoneNumber) {
        return this.phonetoid.containsKey(phoneNumber);
    }

    /**
     * Update customer membership
     * 
     * @param phoneNumber customer contact number
     * @param membership  customer membership
     * 
     */
    public void updateMembership(String phoneNumber, Membership membership) {
        Customer customer = getExistingCustomer(phoneNumber);

        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }

        customer.setMembership(membership);
        return;

    }

}
