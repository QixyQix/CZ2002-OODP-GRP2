package managers;

import java.util.HashMap;
import entities.Customer;
import enums.PriceFilterTypeEnum;
import enums.TaxFilterNameEnum;


class CustomerMgr{

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
    private Integer

    public Customer createCustomer(String name, String phoneNumber, Membership membership){
        if (getExistingCustomer(phoneNumber)) {
            System.out.println("Customer Contains already");
        }

        Customer customer = new Customer(name,phoneNumber,customerId,membership);
        
        this.phonetoid.put(phoneNumber,customerId);
        this.customers.put(customerId,customer);

        customerId++;
        

    }

    public boolean getExistingCustomer(  String phoneNumber){
        return this.phonetoid.containsKey(phoneNumber);

    }

    public void updateMembership(String phoneNumber, Membership membership){

    }
}
