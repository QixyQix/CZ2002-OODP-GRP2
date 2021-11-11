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
    private Scanner sc;
    private Staff staff;

    private OrderUI(Staff staff) {
        super();
        this.staff = staff;
        this.sc = new Scanner(System.in);
    }

    public static OrderUI getInstance(Staff staff) {
        if (INSTANCE == null) {
            INSTANCE = new OrderUI(staff);
        }

        return INSTANCE;
    }


    public void showMenu() {
        int option = 0;
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");
            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
                case 1:
                    printOrder();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    addOrderItem();
                    break;
                case 4:
                    deleteOrderItem();
                    break;
            }
        } while (option != 0);

    }

    public void displayOptions(){
        System.out.println("===========Order Manager============");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Show current order");
        System.out.println("(2) Create a order");
        System.out.println("(3) Add order items");
        System.out.println("(4) Delete order items from order");
        System.out.println("====================================");
    }

    private void printOrder() {
        
        int orderid;
        Order order;

        while(true) {
            orderid = super.getInputInt("Enter your order id");
            if (!OrderMgr.getInstance().checkAvailableOrder(orderid)) {
                System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
                continue;
            } else {
                order = OrderMgr.getInstance().getOrder(orderid);
                order.printOrder();
                break;
            }
        }
    }

    private void createOrder() {
        LocalDateTime date = LocalDateTime.now();

        Integer noofpax = super.getInputInt("No of pax");
        String customerPhoneNumber = super.getInputString("Customer phone number");
        if (!CustomerMgr.getInstance().checkExistingCustomer(customerPhoneNumber)) {
            CustomerUI.getInstance().showMenu();
        } else {
            Customer customer = CustomerMgr.getInstance().getExistingCustomer(customerPhoneNumber);
            Order order = OrderMgr.getInstance().createOrder(this.staff, customer, date, noofpax);
            System.out.println("Order has been created. Your order id is: " + order.getId());
        }
    }

    private void addOrderItem() {
        int orderid;
        int menuItemid;
        MenuItem menuItem;
        Order order;

        while(true) {
            orderid = super.getInputInt("Order id");
            if (OrderMgr.getInstance().checkAvailableOrder(orderid)) {
                order = OrderMgr.getInstance().getOrder(orderid);
                menuItemid = super.getInputInt("Menu item id");
                if (MenuItemMgr.getInstance().checkIDAvailable(menuItemid)) {
                    try {
                        menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
                        OrderMgr.getInstance().addItem(menuItem, order);
                    } catch (Exception ex) {
                        System.out.println("Invalid menu item id");
                    }
                }
            } else {
                System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
                continue;
            }
        }
    }

    private void deleteOrderItem() {
        int orderid;
        int menuItemid;
        int qty;
        MenuItem menuItem;
        Order order;

        while(true) {
            orderid = super.getInputInt("Order id");
            if (OrderMgr.getInstance().checkAvailableOrder(orderid)) {
                order = OrderMgr.getInstance().getOrder(orderid);
                menuItemid = super.getInputInt("Menu item id");
                if (MenuItemMgr.getInstance().checkIDAvailable(menuItemid)) {
                    try {
                        menuItem = MenuItemMgr.getInstance().getMenuItemByID(menuItemid);
                        qty = super.getInputInt("Quantity to be deleted");
                        OrderMgr.getInstance().deleteOrderItem(menuItem, qty, order);
                    } catch (Exception ex) {
                        System.out.println("Invalid menu item id");
                    }
                }
            } else {
                System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
                continue;
            }
        }
    }
}
