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

    }
}
