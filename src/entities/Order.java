package entities;

import java.util.ArrayList;
import java.time.LocalDateTime;
import entities.*;

public class Order {
    private Staff servedBy;
    private double totalPrice;
    private TreeMap<OrderItem, Integer> orderedItems;
    private ArrayList<OrderItem> pendingItems;
    private Invoice invoice;
    private Customer customer;
    private Table table;
    private LocalDateTime date;     
    private String orderid;

    public Staff getServeby(){
        return this.servedBy;
    }

    public void setServedby(Staff servedBy){
        this.servedBy = servedBy;
    }

    public void getTotalPrice(){
        calculateTotalPrice();
        return this.totalPrice;
    }

    private void calculateTotalPrice(){
        this.totalPrice = 0;
        for (OrderItem item : this.orderedItems.keySet()){
            int quantity = this.orderedItems.get(item);
            double price = item.getPrice();
            this.totalPrice += quantity*price;
        }
    }   

    public void addToOrderedItems(){
        for(int i =0 ; i<pendingItems(); i++){
            OrderItem item = this.pendingItems.get(i);
            int val = 0;
            if( this.orderedItems.containsKey(item))  val= this.orderedItems.get(item);
            val +=1;
            // not sure will overwrite
            this.orderedItems.put(item,val);
        }
        this.pendingItems.clear();
    }

    public void getOrderedItems(){
        return this.orderedItems;
    }

    public void addPendingItems(OrderItem item){
        this.pendingItems.add(item);
    }
    public ArrayList<OrderItem> getPendingItems(){
        return this.pendingItems;
    }

    // Suggest no setter for ordereditems (not make sesnse)

    public Invoice getInvoice(){
        return this.invoice;
    }

    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Table getTable(){
        return this.table;
    }

    public void setTableid(Table table){
        this.table = table;
    }

    public Date getDate(){
        return this.date;
    }

    public Date setDate(){
        this.date = LocalDateTime.now();
    }


    

    public String getid(){
        return this.orderid;
    }

    public void setid(String id){
        this.orderid = id;
    }


    
}
