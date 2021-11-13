package managers;

import java.util.HashMap;
import java.io.IOException;
import java.time.LocalDateTime;
import entities.Order;
import entities.MenuItem;
import entities.Customer;
import entities.Entities;
import entities.Table;
import entities.Staff;

/***
 * Represents an order manager
 * 
 * @author Eang Sokunthea
 */
public final class OrderMgr extends DataMgr{
    private static OrderMgr INSTANCE;
    private HashMap<Integer, Order> orders;
    private int nextId;

    private OrderMgr() {
        try {
            this.orders = new HashMap<Integer, Order>();
            downCast(loadSavedData("orders"));
            nextId = loadNextIdData("orderNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load orders data");
        }
    }

    public void downCast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Order)
                this.orders.put(id,(Order) object.get(id));
            else throw new ClassCastException();
        }
    }

    public HashMap<Integer, Entities> upCast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: orders.keySet()){
           object.put(id,orders.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "orders", "orderNextId");
    }

    /**
     * Returns the OrderMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static OrderMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderMgr();
        }

        return INSTANCE;
    }

    /**
     * Returns a Table object that is available
     * 
     * @param date    date and time
     * @param noOfPax number of pax at the table
     * @return Table object if available, null if no available tables
     */
    public Table allocateTable(LocalDateTime date, int noOfPax) {
        Table table = TableMgr.getInstance().findAvailTable(date, noOfPax);
        return table;
    }

    /**
     * Creates and returns Order object
     * 
     * @param staff    staff object
     * @param customer customer object
     * @param date     date and time
     * @param noOfPax  number of pax at the table
     * @return Order object
     */
    public Order createOrder(Staff staff, Customer customer, LocalDateTime date, int noOfPax, Table table) {
        // Table table = this.allocateTable(date, noOfPax);
        if(table == null) {
            
            return null;
        }
        
        Order order = new Order(staff, customer, table, date, nextId);
        orders.put(nextId, order);

        nextId++;
        return order;
    }

    /**
     * Add MenuItem object to the order
     * 
     * @param menuItem MenuItem object
     * @param order    Order object
     * 
     */
    public void addItem(MenuItem menuItem, Order order) {
        order.addPendingItems(menuItem,1);
    }

    /**
     * Create Order object
     * 
     * @param order Order object
     * 
     */
    public void makeOrder(Order order) {
        order.addToOrderedItems();
    }

    /**
     * Close order and create invoice by calling invoiceMgr
     * 
     * @param order      Order object
     * @param invoiceMgr InvoiceMgr obejct
     * 
     */
    public void closeOrder(Order order, InvoiceMgr invoiceMgr) {
        // deallocateTable()
        invoiceMgr.createInvoice(order);
    }

    /**
     * Returns true or false depending on whether the order corresponding to the
     * order id exists
     * 
     * @param orderId id of order
     * @return true if order exists, false if order does not exist
     */
    public boolean checkAvailableOrder(int orderId) {
        return this.orders.containsKey(orderId);
    }

    /**
     * Returns Order object corresponding to order id
     * 
     * @param orderId id of order
     * @return Order object
     */
    public Order getOrder(int orderId) {
        return this.orders.get(orderId);
    }

    /**
     * Delete menuItem item from a speciifc order
     * 
     * @param menuItem MenuItem object
     * @param qty      quantity of MenuItem
     * @param order    Order object
     * 
     */
    public void deleteOrderItem(MenuItem menuItem, int qty, Order order) {
        // Order order = this.getOrder(orderId);
        order.deleteOrderItem(menuItem, qty);
    }

    /*
     * public HashMap<Integer, Order> getorders(){ return orders; }
     */

}
