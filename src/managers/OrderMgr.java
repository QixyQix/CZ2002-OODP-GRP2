package managers;

import java.util.HashMap;
import java.io.IOException;
import java.time.LocalDateTime;
import entities.Order;
import entities.MenuItem;
import entities.Customer;
import entities.Entities;
import entities.Table;
import exceptions.IDNotFoundException;
import entities.Staff;

/***
 * Represents an order manager
 * 
 * @author Lee Zong Yu
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public final class OrderMgr extends DataMgr {
    /**
     * The Instance of this Ordermgr
     */
    private static OrderMgr INSTANCE;
    /**
     * The mapping of Order ID to its respective object
     */
    private HashMap<Integer, Order> orders;
    /**
     * The next Id to be use in creating Order
     */
    private int nextId;

    /**
     * Constructor
     */
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

    /**
     * Downcast from entities to order
     * 
     * @param object the entities to downcast
     */
    public void downCast(HashMap<Integer, Entities> object) {
        for (int id : object.keySet()) {
            if (object.get(id) instanceof Order)
                this.orders.put(id, (Order) object.get(id));
            else
                throw new ClassCastException();
        }
    }

    /**
     * Upcast order to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast() {
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for (int id : orders.keySet()) {
            object.put(id, orders.get(id));
        }
        return object;
    }

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
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
        if (table == null) {

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
        order.addPendingItems(menuItem, 1);
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
     * @param order Order object
     * 
     */
    public void closeOrder(Order order) {
        // deallocateTable()
        InvoiceMgr.getInstance().createInvoice(order);
    }
    /**
     * Returns Order object corresponding to order id
     * 
     * @param orderId id of order
     * @return Order object
     * @throws IDNotFoundException
     */
    public Order getOrder(int orderId) throws IDNotFoundException {
        if (!this.orders.containsKey(orderId))
            throw new IDNotFoundException();
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
