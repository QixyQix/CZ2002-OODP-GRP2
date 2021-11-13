package entities;
import java.util.TreeMap;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * Represents a order entity
 * 
 * @author Cho Qi Xiang
 */
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

    /**
     * Constructor
     */
    public Order(Staff staff, Customer customer, Table table, LocalDateTime date, int id){
        this.servedBy = staff;
        this.customer = customer;
        this.table = table;
        this.date = date;
        this.id = id;
        this.orderedItems = new TreeMap<MenuItem, Integer>();
        this.pendingItems = new TreeMap<MenuItem, Integer>();
    }

    /**
     * Returns staff
     * 
     * @return staff
     */
    public Staff getServeby(){
        return this.servedBy;
    }

    /**
     * Sets staff to serve the order
     * 
     * @param servedBy
     */
    public void setServedby(Staff servedBy){
        this.servedBy = servedBy;
    }

    /**
     * Returns total price
     * 
     * @return total price
     */
    public double getTotalPrice(){
        calculateTotalPrice();
        return this.totalPrice;
    }
    

    /**
     * Calculates total price of order
     * 
     */
    private void calculateTotalPrice(){
        this.totalPrice = 0;
        for (MenuItem item : this.orderedItems.keySet()){
            int quantity = this.orderedItems.get(item);
            double price = item.getPrice();
            this.totalPrice += quantity*price;
        }
    }   

    /**
     * Adds items
     * 
     * @param target target
     * @param menuitem menu item
     * @param quantity quantity
     */
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

    /**
     * Add items to ordered items
     * 
     */
    public void addToOrderedItems(){
        for( MenuItem item : pendingItems.keySet()){
            addItems(orderedItems,item,pendingItems.get(item));
        }
        this.pendingItems.clear();
    }

    /**
     * Adds oending items
     * 
     * @param item menu item
     * @param quantity
     */
    public void addPendingItems(MenuItem item, int quantity){
        this.addItems(pendingItems,item,quantity);
    }

    /**
     * Prints items
     * 
     * @param target target
     * @param status order status
     */
    private void printItem(TreeMap<MenuItem,Integer> target, String status){
        for (MenuItem item : target.keySet()) {
            int quantity = target.get(item);
            System.out.println(quantity + " " + item.getName() + " Price: " + item.getPrice()*quantity + " Status: " +status);
        }
    }
    
    /**
     * prints order
     * 
     */
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

    /**
     * Returns ordered items
     * 
     * @return Treemap of ordered items
     */
    public TreeMap<MenuItem, Integer> getOrderedItems(){
        return this.orderedItems;
    }

    /**
     * Returns pending items
     * 
     * @return Treemap of pending items
     */
    public TreeMap<MenuItem, Integer> getPendingItems(){
        return this.pendingItems;
    }

    /**
     * Returns status
     * 
     * @return status of order
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * Sets order to close
     * 
     */
    public void closeStatus(){
        this.status = "Close";
    }
    /**
     * Returns customer
     * 
     * @return customer
     */
    public Customer getCustomer(){
        return this.customer;
    }

    /**
     * Sets customer
     * 
     * @param customer customer
     */
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    /**
     * Returns table
     * 
     * @return table
     */
    public Table getTable(){
        return this.table;
    }

    /**
     * Sets table
     * 
     * @param table
     */
    public void setTable(Table table){
        this.table = table;
    }

    /**
     * Returns date
     * 
     * @return date
     */
    public LocalDateTime getDate(){
        return this.date;
    }

    /**
     * Sets date
     * 
     */
    public void setDate(){
        this.date = LocalDateTime.now();
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Deletes order items
     * 
     * @param orderItem order item
     * @param qty quantity
     */
    public void deleteOrderItem(MenuItem orderItem, int qty) {
        if (this.pendingItems.get(orderItem) <= qty) {
            this.pendingItems.remove(orderItem);
        } else {
            this.pendingItems.put(orderItem, this.pendingItems.get(orderItem)-qty);
        }
    }

    
}
