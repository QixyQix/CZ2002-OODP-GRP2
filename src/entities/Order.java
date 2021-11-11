package entities;
import java.util.TreeMap;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Order implements Serializable{
    private Staff servedBy;
    private double totalPrice;
    private TreeMap<MenuItem, Integer> orderedItems;
    private ArrayList<MenuItem> pendingItems;
    //private Invoice invoice;
    private Customer customer;
    private Table table;
    private LocalDateTime date;     
    private int orderid;
    
    public Order(){}

    public Order(Staff staff, Customer customer, Table table, LocalDateTime date, int orderid){
        this.servedBy = staff;
        this.customer = customer;
        this.table = table;
        this.date = date;
        this.orderid = orderid;
        this.orderedItems = new TreeMap<MenuItem, Integer>();
        this.pendingItems = new ArrayList<MenuItem>();
    }

    public Staff getServeby(){
        return this.servedBy;
    }

    public void setServedby(Staff servedBy){
        this.servedBy = servedBy;
    }

    public double getTotalPrice(){
        calculateTotalPrice();
        return this.totalPrice;
    }
    

    private void calculateTotalPrice(){
        this.totalPrice = 0;
        for (MenuItem item : this.orderedItems.keySet()){
            int quantity = this.orderedItems.get(item);
            double price = item.getPrice();
            this.totalPrice += quantity*price;
        }
    }   

    public void addToOrderedItems(){
        for(int i =0 ; i<pendingItems.size(); i++){
            MenuItem item = this.pendingItems.get(i);
            int val = 0;
            if( this.orderedItems.containsKey(item))  val= this.orderedItems.get(item);
            val +=1;
            // not sure will overwrite
            this.orderedItems.put(item,val);
        }
        this.pendingItems.clear();
    }

    public void printOrder() {
        System.out.println("Your current order contains:");
        System.out.println("................................");
        System.out.println();

        TreeMap<MenuItem, Integer> items = this.getOrderedItems();
        for (MenuItem item : items.keySet()) {
            int quantity = items.get(item);
            System.out.println(quantity + " " + item.getName() + " " + item.getPrice()*quantity);
        }

        System.out.println("................................");

    }

    public TreeMap<MenuItem, Integer> getOrderedItems(){
        return this.orderedItems;
    }

    public void addPendingItems(MenuItem item){
        this.pendingItems.add(item);
    }
    public ArrayList<MenuItem> getPendingItems(){
        return this.pendingItems;
    }

    // Suggest no setter for ordereditems (not make sesnse)
    /*
    public Invoice getInvoice(){
        return this.invoice;
    }

    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }
    */
    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Table getTable(){
        return this.table;
    }

    public void setTable(Table table){
        this.table = table;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public void setDate(){
        this.date = LocalDateTime.now();
    }

    public int getId(){
        return this.orderid;
    }

    public void deleteOrderItem(MenuItem orderItem, int qty) {
        if (this.orderedItems.get(orderItem) <= qty) {
            this.orderedItems.remove(orderItem);
        } else {
            this.orderedItems.put(orderItem, this.orderedItems.get(orderItem)-qty);
        }
    }

    
}
