package managers;

import java.util.HashMap;
import java.time.LocalDateTime;
import entities.Order;
import entities.MenuItem;
import entities.Customer;
import entities.Table;
import entities.Staff;
import managers.InvoiceMgr;
public final class OrderMgr {
    private static OrderMgr INSTANCE;
    private HashMap<Integer, Order> orders;
    private int orderid;
    
    private void importing(){

    }

    public void exporting(){

    }
    private OrderMgr(){
        importing();
    }


    public static OrderMgr getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrderMgr();
        }

        return INSTANCE;
    }
    public Table allocateTable(){
        // TO BE DONE
        Table table = new Table();
        return table;
    }

    public Order createOrder(Staff staff, Customer customer, LocalDateTime date, int noofpax){
        Table table = this.allocateTable();

        Order order = new Order(staff,  customer, table, date, orderid);
        orders.put(orderid,order);

        orderid++;
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
    /*
    public HashMap<Integer, Order> getorders(){
        return orders;
    }
    */
}
