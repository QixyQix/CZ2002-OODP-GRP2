package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import entities.Customer;
import entities.Membership;

public final class CustomerMgr{

    private static CustomerMgr instance;
    private HashMap<Integer,Customer> customers;
    private HashMap<String, Integer> phonetoid;
    private int customerId;

    private CustomerMgr() {
        try {
            this.customers = new HashMap<Integer, Customer>();
            this.phonetoid = new HashMap<String, Integer>();
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
     * @throws IOException
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
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @throws IOException
     * @throws ClassNotFoundException
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
        try{
            FileInputStream fileInputStream = new FileInputStream("customerId");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.customerId = (int) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            this.customerId = 1;
        }
    }
    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * 
     * @return
     */
    public static CustomerMgr getInstance(){
        if(instance == null){
            instance = new CustomerMgr();
        }
        return instance;
    }

    public HashMap<Integer,Customer> getInvoicesMap() {
        return this.customers;
    }


    public Customer createCustomer(Membership membership, String name, String gender, String contact){
        if (checkExistingCustomer(contact)) {
            System.out.println("Customer Contains already");
            return null;
        }

        Customer customer = new Customer(membership,  customerId,  name,  gender,  contact);
        
        this.phonetoid.put(contact,customerId);
        this.customers.put(customerId,customer);

        customerId++;
        
        return customer;

    }

    public Customer getExistingCustomer(String phoneNumber){
        int cusid = this.phonetoid.get(phoneNumber);
        return this.customers.get(cusid);
    }


    public boolean checkExistingCustomer(String phoneNumber) {
        return this.phonetoid.containsKey(phoneNumber);
    }

    public void updateMembership(String phoneNumber, Membership membership){
        Customer customer = getExistingCustomer(phoneNumber);
        
        if(customer == null) {
            System.out.println("customer not found");
            return;
        }

        customer.setMembership(membership);
        return;

    }
}
