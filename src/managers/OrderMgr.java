package managers;

import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import entities.Order;
import entities.MenuItem;
import entities.Customer;
import entities.Table;
import entities.Staff;

public final class OrderMgr {
    private static OrderMgr instance;
    private HashMap<Integer, Order> orders;
    private int orderId;

    private OrderMgr() {
        try {
            this.orders = new HashMap<Integer, Order>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load orders data");
        }
    }

    /***
     * Serializes and saves the Staffs objects into the data/staffs folder Creates
     * the data/staffs folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/orders");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int orderID : this.orders.keySet()) {
            Order order = this.orders.get(orderID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/orders/" + orderID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(order);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/orderId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(orderId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/orders");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try {
            File file = new File("./data/orderId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.orderId = (int) objectInputStream.readInt();
            objectInputStream.close();
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            this.orderId = 1;
        }

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Order order = (Order) objectInputStream.readObject();
            this.orders.put(order.getId(), order);
            objectInputStream.close();
        }

    }

    /**
     * Returns the OrderMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static OrderMgr getInstance() {
        if (instance == null) {
            instance = new OrderMgr();
        }

        return instance;
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
    public Order createOrder(Staff staff, Customer customer, LocalDateTime date, int noofpax) {
        Table table = this.allocateTable(date, noofpax);

        Order order = new Order(staff, customer, table, date, orderId);
        orders.put(orderId, order);

        orderId++;
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
        order.addPendingItems(menuItem);
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
