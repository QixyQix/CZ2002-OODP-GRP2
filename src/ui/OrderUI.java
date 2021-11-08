package ui;

import entities.Order;
import entities.MenuItem;
import managers.OrderMgr;
import managers.MenuItemMgr;

import java.util.Scanner;

public final class OrderUI extends UserInterface {
    private static OrderUI INSTANCE;
    private Scanner sc;

    private OrderUI() {
        super();
        this.sc = new Scanner(System.in);
    }

    public static OrderUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderUI();
        }

        return INSTANCE;
    }

    public void displayOptions(){
        System.out.println("====Order Manager====");
        System.out.println("(1) Show current order");
        System.out.println("(2) Create a order");
        System.out.println("(3) Add order items");
        System.out.println("(4) Delete order items from order");
        System.out.println("(-1) Exit");
        System.out.println("Enter your selection: ");
    }

    private void viewOrder() {
        
    }

    private void createOrder() {

    }

    private void addOrderItem() {

    }

    private void deleteOrderItem() {

    }
}
