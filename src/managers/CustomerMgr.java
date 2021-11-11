package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import entities.Customer;
import entities.Membership;

public final class CustomerMgr {

    private static CustomerMgr instance;
    private HashMap<Integer, Customer> customers;
    private HashMap<String, Integer> phonetoid;
    private int customerId;

    private CustomerMgr() {
        try {
            this.customers = new HashMap<Integer, Customer>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load customers data");
        }
    };

    /***
     * Serializes and saves the Customers objects into the data/customers folder
     * Creates the data/customers folder if it does not exist
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/customers");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int customerID : this.customers.keySet()) {
            Customer customer = this.customers.get(customerID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/customers/" + customerID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(customer);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/customerId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(customerId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    /***
     * Reads Serialized Customer data in the data/customers folder and stores it
     * into the customers HashMap
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/customers");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Customer customer = (Customer) objectInputStream.readObject();
            this.customers.put(customer.getCustomerid(), customer);
            objectInputStream.close();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("customerId");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.customerId = (int) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            this.customerId = 1;
        }
    }

    /**
     * Returns the CustomerMgr instance and creates an instance if it does not exist
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @return instance
     */
    public static CustomerMgr getInstance() {
        if (instance == null) {
            instance = new CustomerMgr();
        }
        return instance;
    }

    public HashMap<Integer, Customer> getInvoicesMap() {
        return this.customers;
    }

    /**
     * Creates Customer object
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @param memebership customer membership
     * @param name        customer name
     * @param gender      customer gender
     * @param contact     customer contact number
     * @return Customer object if customer has not been created before, null if
     *         customer data exists
     */
    public Customer createCustomer(Membership membership, String name, String gender, String contact) {
        if (getExistingCustomer(contact) != null) {
            System.out.println("Customer Contains already");
            return null;
        }

        Customer customer = new Customer(membership, customerId, name, gender, contact);

        this.phonetoid.put(contact, customerId);
        this.customers.put(customerId, customer);

        customerId++;

        return customer;

    }

    /**
     * Returns Customer object corresponding to the contact number
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
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
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @param phoneNumber customer contact number
     * @return true if Customer object exists, false if customer does not exist
     */
    public boolean checkExististingCustomer(String phoneNumber) {
        return this.phonetoid.containsKey(phoneNumber);
    }

    /**
     * Update customer membership
     * 
     * @author Zong Yu Lee
     * @author Lim Yan Kai
     * @param phoneNumber customer contact number
     * @param membership  customer membership
     * 
     */
    public void updateMembership(String phoneNumber, Membership membership) {
        Customer customer = getExistingCustomer(phoneNumber);

        if (customer == null) {
            System.out.println("cusotmer not found");
            return;
        }

        customer.setMembership(membership);
        return;

    }
}
