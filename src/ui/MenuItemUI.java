package ui;

import java.util.Scanner;

import entities.MenuItem;
import managers.MenuItemMgr;

public final class MenuItemUI {
    private static MenuItemUI INSTANCE;
    private Scanner sc;

    private MenuItemUI() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Returns the MenuItemUI instance and creates an instance if it does not exist
     * 
     * @return
     */
    public static MenuItemUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemUI();
        }

        return INSTANCE;
    }

    public void showMenu() {
        int option = 0;
        do {
            displayOptions();
            try {
                option = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
            case 1:
                showCurrentMenuItems();
                break;

            }
        } while (option != -1);
    }

    private void displayOptions() {
        System.out.println("====Menu Item Manager====");
        System.out.println("(1) Show current MenuItems");
        System.out.println("(2) Create a Menu Item");
        System.out.println("(3) Delete a Menu Item");
        System.out.println("(-1) Exit");
        System.out.println("Enter your selection: ");
    }

    private void showCurrentMenuItems() {
        System.out.println("MENU ITEMS:");
        try {
            MenuItem[] items = MenuItemMgr.getInstance().getAllMenuItems();
            System.out.println(items.length);

            for (MenuItem item : items) {
                System.out.println("ID: " + item.getId() + " | Type: " + item.getType() + " | Name: " + item.getName()
                        + " $" + item.getPrice());
                System.out.println(item.getDescription());
            }
        } catch (Exception ex) {
            System.out.println("An error occured while getting all Menu Items:");
            System.out.println(ex.getMessage());
        }
    }

}
