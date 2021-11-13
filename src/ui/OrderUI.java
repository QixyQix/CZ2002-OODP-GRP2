package ui;

import entities.Order;
import entities.Table;
import entities.Customer;
import entities.MenuItem;
import managers.OrderMgr;
import managers.TableMgr;
import managers.MenuItemMgr;

import java.time.LocalDateTime;

/***
 * Represents a Order UI
 * 
 * @author Lee Zong Yu
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public final class OrderUI extends UserInterface {
    private static OrderUI INSTANCE;
    
    /**
     * Constructor
     */
    private OrderUI() {
        super();
    }

    /**
     * Returns the OrderUI instance and creates an instance if it does not exist
     * 
     * @return OrderUI Instance
     */
    public static OrderUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderUI();
        }
        return INSTANCE;
    }
    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions(){
        System.out.println("===========Order Manager============");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Create Order");
        System.out.println("(2) Modify Order");
        System.out.println("====================================");
    }

    /**
     * Show the Selection Page of Order UI for User to Select Options
     * 
     */
    public void showSelection() {
        int option = 0;
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    modifyOrderMenu();
                    break;
            }
            super.waitEnter();
        } while (option != 0);

    }
    /** 
     *   Get User Input to create Order Object
     * 
     */  
    private void createOrder() {
        LocalDateTime date = LocalDateTime.now();

        Integer noofpax = super.getInputInt("No of pax");
        Table table = TableMgr.getInstance().findAvailTable(date, noofpax);
        
        if(table == null){
            System.out.println("Order is not created, Table are full.");
            return;
        }
        
        Customer customer = CustomerUI.getInstance().getCustomer();
        
        if( !super.getYNOption("Do you confirm with the selection. " )) return;
        
        Order order = OrderMgr.getInstance().createOrder(super.getStaff(), customer, date, noofpax,table);
        System.out.println("Order has been created. Your order id is: " + order.getId());    
    }


    /**
     *  Get User Input to get Order Object
     * @return order
     */
    private Order getOrder(){
        Order order;
        int orderid;
        orderid = super.getInputInt("Enter your order id");
        try {
            order = OrderMgr.getInstance().getOrder(orderid);
            
            if(order.getStatus()=="Close"){
                System.out.println("You should not modify this order anymore");
                return null;
            }
            return order;
        } catch(Exception ex) {
            System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid.");
            return null;
        }
    }

    /**
     * Display the option of Selection Page
     * 
     */
    private void displayOrderOptions(int orderid){
        System.out.println("===========Orderid " + orderid + "============");
        System.out.println("(0) Go Back to Order Page");
        System.out.println("(1) Show current order");
        System.out.println("(2) Add order items");
        System.out.println("(3) Delete order items from order");
        System.out.println("(4) Show Menu");
        System.out.println("(5) Confirm Order");
        System.out.println("====================================");
    }

    /**
     * Show the Selection Page of Order for User to Select Options
     * 
     */
    private void modifyOrderMenu(){
        int option;
        Order order = getOrder();
        if (order == null)
            return;
        do {
            displayOrderOptions(order.getId());
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
                case 0: 
                    return;
                case 1:
                    printOrder(order);
                    break;
                case 2:
                    addOrderItem(order);
                    break;
                case 3:
                    deleteOrderItem(order);
                    break;
                case 4:
                    MenuItemUI.getInstance().showCurrentMenuItems();
                    break;
                case 5:
                    confirmOrder(order);
                    break;
            }
            super.waitEnter();
        } while (option != 0);
    }

    /**
     *  print Order 
     *
     */
    private void printOrder(Order order) {     
        order.printOrder();        
    }

    
    /**
     *  Get User Input to add OrderItem to Order 
     *
     */
    private void addOrderItem(Order order) {
        int menuItemid;
        MenuItem menuItem;
        try {
            menuItemid = super.getInputInt("Please enter the Menu item id To add (0 to Exit)");
            if(menuItemid == 0) return;
            menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
            OrderMgr.getInstance().addItem( (MenuItem) menuItem.clone(), order);
            System.out.println("Order item added");
        } catch (Exception ex) {
            System.out.println("Invalid menu item id. ");
        }   
    }

    /**
     *  Get User Input to add OrderItem to Order 
     *
     */
    private void deleteOrderItem(Order order) {
        int menuItemid;
        int qty;
        MenuItem menuItem;
        try {
            menuItemid = super.getInputInt("Menu item id (0 to Exit)");

            if(menuItemid ==0) return;

            menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
            qty = super.getInputInt("Quantity to be deleted");
            OrderMgr.getInstance().deleteOrderItem(menuItem, qty, order);
        } catch (Exception ex) {
            System.out.println("Invalid menu item id");
        }
       
    }

    /**
     *  Get User Input to confirm adding of OrderItem to Order 
     *
     */
    private void confirmOrder(Order order){
        if(super.getYNOption("Are you sure you want to make order?")){
            OrderMgr.getInstance().makeOrder(order);
        }
    }
}
