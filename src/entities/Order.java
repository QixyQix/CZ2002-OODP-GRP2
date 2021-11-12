package entities;
import java.util.TreeMap;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Order implements Serializable, Entities{
    private Staff servedBy;
    private double totalPrice;
    private TreeMap<MenuItem, Integer> orderedItems;
    private TreeMap<MenuItem, Integer> pendingItems;
    private Customer customer;
    private Table table;
    private LocalDateTime date;     
    private int id;
    private String status = "Open";

    public Order(){}

    public Order(Staff staff, Customer customer, Table table, LocalDateTime date, int id){
        this.servedBy = staff;
        this.customer = customer;
        this.table = table;
        this.date = date;
        this.id = id;
        this.orderedItems = new TreeMap<MenuItem, Integer>();
        this.pendingItems = new TreeMap<MenuItem, Integer>();
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

    private void addItems(TreeMap<MenuItem,Integer> target, MenuItem menuitem, int quantity){
        int originalQuantitiy = 0;
        try{
            originalQuantitiy = target.get(menuitem);
        }
        catch(NullPointerException ex){
            originalQuantitiy = 0;
        }   
            
        quantity += originalQuantitiy;
        // not sure will overwrite
        target.put(menuitem,quantity);
    }

    public void addToOrderedItems(){
        for( MenuItem item : pendingItems.keySet()){
            addItems(orderedItems,item,pendingItems.get(item));
        }
        this.pendingItems.clear();
    }

    public void addPendingItems(MenuItem item, int quantitiy){
        this.addItems(pendingItems,item,quantitiy);
    }

    private void printItem(TreeMap<MenuItem,Integer> target, String status){
        for (MenuItem item : target.keySet()) {
            int quantity = target.get(item);
            System.out.println(quantity + " " + item.getName() + " Price: " + item.getPrice()*quantity + " Status: " +status);
        }
    }
    public void printOrder() {
  

        if( this.orderedItems.size()+ this.pendingItems.size()==0){
            System.out.println("Your current order does not contain any items.");
            return;
        }

        System.out.println("Your current order contains:");
        if(this.orderedItems.size() !=0){
            System.out.println(".............[Confirmed]................");
            System.out.println();
        
            printItem(this.orderedItems, "Confirmed");
        }
        if(this.pendingItems.size()!=0){
            System.out.println("..............[Pending].................");
            
            printItem(this.pendingItems, "Pending");
        }
        System.out.println("................................");

    }

    public TreeMap<MenuItem, Integer> getOrderedItems(){
        return this.orderedItems;
    }

    
    public TreeMap<MenuItem, Integer> getPendingItems(){
        return this.pendingItems;
    }

    public String getStatus(){
        return this.status;
    }

    public void closeStatus(){
        this.status = "Close";
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
        return this.id;
    }

    public void deleteOrderItem(MenuItem orderItem, int qty) {
        if (this.pendingItems.get(orderItem) <= qty) {
            this.pendingItems.remove(orderItem);
        } else {
            this.pendingItems.put(orderItem, this.pendingItems.get(orderItem)-qty);
        }
    }

    
}
