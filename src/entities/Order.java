package entities;
import java.util.TreeMap;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * Represents an order entity
 * 
 * @author Lee Zong Yu
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class Order implements Serializable, Entities{
    /**
     * The staff that serve this Order
     */
    private Staff servedBy;
    /**
     * The totalPrice of this Order
     */
    private double totalPrice;
    /**
     * The MenuItems that are confirmed ordered and its Quantity
     */
    private TreeMap<MenuItem, Integer> orderedItems;
    /**
     * The MenuItems that are pending to be ordered and its Quantity
     */
    private TreeMap<MenuItem, Integer> pendingItems;
    /**
     * The Customer of this Order
     */
    private Customer customer;
    /**
     * The table allocated to order
     */
    private Table table;
    /**
     * The Date of this order
     */
    private LocalDateTime date;   
    /**
     * The Id of this order
    */  
    private int id;
    /**
     * The status of this order ("Open" or "Close")
     */
    private String status = "Open";

    /**
     * Constructor
     */
    public Order(){}

    /**
     * Constructor
     * @param staff     Staff that serve the order
     * @param customer  Customer of the order
     * @param table     Table allocated to the order
     * @param date      Date of the order
     * @param id        Id of the order
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
            System.out.printf("%-3d   %-25s  %-10.2f  %s\n", quantity , item.getName() , item.getPrice()*quantity , status);
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
            System.out.printf("%-2s   %-25s  %-10s  %s\n", "qty" , "Item Name" , "Price(Sgd)" , "Status");
            this.printItem(this.orderedItems, "Confirmed");
        }
        if(this.pendingItems.size()!=0){
            System.out.println("..............[Pending].................");
            System.out.printf("%-2s   %-25s  %-10s %s\n", "qty" , "Item Name" , "Price(Sgd)" , "Status");
            this.printItem(this.pendingItems, "Pending");
        }
        System.out.println("............................................");

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
