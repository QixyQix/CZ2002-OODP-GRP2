package managers;

import java.util.HashMap;
import entities.Customer;
import entities.Membership;

public final class CustomerMgr{

    private static CustomerMgr instance;
    private HashMap<Integer,Customer> customers;
    private HashMap<String, Integer> phonetoid;
    private int customerId;

    private CustomerMgr(){
        importing();
        // this.invoiceid = this.invoices.size() +1;
    };

    private void importing(){
        // this.invoices = ... //import from Serialization;
        // this.invoiceid import from Serialization 
    }
    public void exporting(){
        // export to Json;
    }
    public static CustomerMgr getInstance(){
        if(instance == null){
            instance = new CustomerMgr();
        }
        return instance;
    }

    public HashMap<Integer,Customer> getInvoicesMap() {
        return this.customers;
    }


    public Customer createCustomer(Membership membership, int customerid, String name, String gender, String contact){
        if (getExistingCustomer(contact) != null) {
            System.out.println("Customer Contains already");
        }

        Customer customer = new Customer( membership,  customerid,  name,  gender,  contact);
        
        this.phonetoid.put(contact,customerId);
        this.customers.put(customerId,customer);

        customerId++;
        
        return customer;

    }

    public Customer getExistingCustomer(  String phoneNumber){
        if(! this.phonetoid.containsKey(phoneNumber)) return null;

        int cusid = this.phonetoid.get(phoneNumber);
        return this.customers.get(cusid);

    }

    public void updateMembership(String phoneNumber, Membership membership){
        Customer customer = getExistingCustomer(phoneNumber);
        
        if(customer == null) {
            System.out.println("cusotmer not found");
            return;
        }

        customer.setMembership(membership);
        return;

    }
}
