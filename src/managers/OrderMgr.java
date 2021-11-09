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
    private static OrderMgr INSTANCE;
    private HashMap<Integer, Order> orders;
    private int orderId;
    
    /**
     * Constructor
     */
    private OrderMgr(){
        try {
            this.orders = new HashMap<Integer,Order>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load orders data");
        }
    }

     /***
     * Serializes and saves the Staffs objects into the data/staffs folder
     * Creates the data/staffs folder if it does not exist
     * 
     * @throws IOException
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
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/orders");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try{
            File file = new File("./data/orderId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.orderId = (int) objectInputStream.readInt();
            objectInputStream.close();
        }catch(Exception e){
            //System.out.println(e.getMessage());
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


    public static OrderMgr getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrderMgr();
        }

        return INSTANCE;
    }
    public Table allocateTable(LocalDateTime date, int noofpax){
        // TO BE DONE
        Table table = TableMgr.getInstance().findAvailTable(date,noofpax);
        return table;
    }

    public Order createOrder(Staff staff, Customer customer, LocalDateTime date, int noofpax){
        Table table = this.allocateTable(date,noofpax);

        Order order = new Order(staff,  customer, table, date, orderId);
        orders.put(orderId,order);

        orderId++;
        return order;
    }

    public void addItem(MenuItem item, Order order){
        order.addPendingItems(item);
    }

    public void makeOrder(Order order){
        order.addToOrderedItems();
    }

    public void closeOrder(Order order, InvoiceMgr invoiceMgr){
        // deallocateTable()
        invoiceMgr.createInvoice(order);
    }

    public boolean checkAvailableOrder(int orderid) {
        return this.orders.containsKey(orderid);
    }

    public Order getOrder(int orderid) {
        return this.orders.get(orderid);
    }

    public void deleteOrderItem(MenuItem MenuItem, int qty, Order order) {
        //Order order = this.getOrder(orderId);
        order.deleteOrderItem(MenuItem, qty);
    }

    /*
    public HashMap<Integer, Order> getorders(){
        return orders;
    }
    */
}
