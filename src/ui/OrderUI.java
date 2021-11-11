package ui;

import entities.Order;
import entities.Staff;
import entities.Customer;
import entities.MenuItem;
import managers.OrderMgr;
import managers.CustomerMgr;
import managers.MenuItemMgr;

import java.time.LocalDateTime;
import java.util.Scanner;

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
                    showOrder();
                    break;
                case 2:
                    createOrder(staff);
                    break;
            }
        } while (option != 0);

    }
    private Order askId(){
        Order order;
        int orderid;
        while(true) {
            orderid = super.getInputInt("Enter your order id");
            if (!OrderMgr.getInstance().checkAvailableOrder(orderid)) {
                System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
                continue;
            } else {
                order = OrderMgr.getInstance().getOrder(orderid);
                break;
            }
        }
        return order;
    }
    private void showOrder(){
        int option;
        Order order = askId();
        do {
            displayOrderOptions(order.getId());
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
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
                    //depends see what or not
                    break;
                case 5:
                    confirmOrder(order);
            }
        } while (option != 0);
    }

    private void displayOptions(){
        System.out.println("===========Order Manager============");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Enter Order Selection Page");
        System.out.println("(2) Create a order");
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
        order.printOrder();        
        super.waitEnter();
    }

    private void createOrder(Staff staff) {
        LocalDateTime date = LocalDateTime.now();

        Integer noofpax = super.getInputInt("No of pax");
        String customerPhoneNumber = super.getInputString("Customer phone number");
        if (!CustomerMgr.getInstance().checkExistingCustomer(customerPhoneNumber)) {
            CustomerUI.getInstance().showMenu();
        } else {
            Customer customer = CustomerMgr.getInstance().getExistingCustomer(customerPhoneNumber);
            Order order = OrderMgr.getInstance().createOrder(staff, customer, date, noofpax);
            System.out.println("Order has been created. Your order id is: " + order.getId());
        }
        super.waitEnter();
    }

    private void addOrderItem(Order order) {
        int menuItemid;
        MenuItem menuItem;
      
        while(true) {
            try {
                menuItemid = super.getInputInt("Please enter the Menu item id");
                menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
                OrderMgr.getInstance().addItem(menuItem, order);
                break;
            } catch (Exception ex) {
                System.out.println("Invalid menu item id");
            }   
        }
    }

    private void deleteOrderItem(Order order) {
        int menuItemid;
        int qty;
        MenuItem menuItem;
        while(true) {
            try {
                menuItemid = super.getInputInt("Menu item id");
                menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
                qty = super.getInputInt("Quantity to be deleted");
                OrderMgr.getInstance().deleteOrderItem(menuItem, qty, order);
                break;
            } catch (Exception ex) {
                System.out.println("Invalid menu item id");
            }
        }
    }

    private void confirmOrder(Order order){
        if(super.getYNOption("Are you sure you want to make order?")){
            OrderMgr.getInstance().makeOrder(order);
        }
    }
}
