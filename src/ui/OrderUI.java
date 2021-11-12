package ui;

import entities.Order;
import entities.Staff;
import entities.Customer;
import entities.MenuItem;
import managers.OrderMgr;
import managers.MenuItemMgr;

import java.time.LocalDateTime;

public final class OrderUI extends UserInterface {
    private static OrderUI INSTANCE;
 
    private OrderUI() {
        super();
    }

    public static OrderUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderUI();
        }
        return INSTANCE;
    }


    public void showMenu(Staff staff) {
        int option = 0;
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
                case 1:
                    createOrder(staff);
                    break;
                case 2:
                    modifyOrderMenu();
                    break;
            }
            super.waitEnter();
        } while (option != 0);

    }

    private Order getOrder(){
        Order order;
        int orderid;
        orderid = super.getInputInt("Enter your order id");
        try {
            order = OrderMgr.getInstance().getOrder(orderid);
            // TODO ask yan kai seee or others seeee.
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
                    //TODO depends see what or not
                    break;
                case 5:
                    confirmOrder(order);
            }
            super.waitEnter();
        } while (option != 0);
    }

    private void displayOptions(){
        System.out.println("===========Order Manager============");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Create Order");
        System.out.println("(2) Modify Order");
        System.out.println("====================================");
    }

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

    private void printOrder(Order order) {     
        // TODO This want call manager?     
        order.printOrder();        
    }

    public void createOrder(Staff staff) {
        LocalDateTime date = LocalDateTime.now();

        Integer noofpax = super.getInputInt("No of pax");
        Customer customer = CustomerUI.getInstance().getCustomer();
        Order order = OrderMgr.getInstance().createOrder(staff, customer, date, noofpax);
        if(order == null){
            System.out.println("Order is not created, Table are full.");
            return;
        }
        System.out.println("Order has been created. Your order id is: " + order.getId());
    }

    private void addOrderItem(Order order) {
        int menuItemid;
        MenuItem menuItem;
        try {
            menuItemid = super.getInputInt("Please enter the Menu item id");
            menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
            OrderMgr.getInstance().addItem(menuItem, order);
            System.out.println("Order item added");
        } catch (Exception ex) {
            System.out.println("Invalid menu item id. ");
        }   
    }

    private void deleteOrderItem(Order order) {
        int menuItemid;
        int qty;
        MenuItem menuItem;
        try {
            menuItemid = super.getInputInt("Menu item id");
            menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
            qty = super.getInputInt("Quantity to be deleted");
            OrderMgr.getInstance().deleteOrderItem(menuItem, qty, order);
        } catch (Exception ex) {
            System.out.println("Invalid menu item id");
        }
       
    }

    private void confirmOrder(Order order){
        if(super.getYNOption("Are you sure you want to make order?")){
            OrderMgr.getInstance().makeOrder(order);
        }
    }
}
