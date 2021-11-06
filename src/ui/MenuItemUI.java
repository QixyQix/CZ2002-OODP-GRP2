package ui;

import java.util.ArrayList;
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
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
            case 1:
                showCurrentMenuItems();
                break;
            case 2:
                createMenuItem();
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

    private void createMenuItem() {
        boolean invalid = false;
        boolean createPackage = false;

        int id = 0;
        String type;
        String name;
        String description;
        double price =0.0;
        ArrayList<MenuItem> packageItems = null;

        System.out.println("Is this a package? Enter P for package.");
        if (sc.nextLine().equals("P")) {
            createPackage = true;
            packageItems = new ArrayList<MenuItem>();
        }

        do {
            try {
                System.out.println("Enter an ID for the Menu Item");
                id = sc.nextInt();
                sc.nextLine();
                if (!MenuItemMgr.getInstance().checkIDAvailable(id)) {
                    System.out.println("ID " + id + " has already been taken. Please enter another ID.");
                    continue;
                }
            } catch (Exception ex) {
                System.out.println("Enter a valid ID");
            }
        } while (invalid);

        System.out.println("Enter the item type: ");
        type = sc.nextLine();

        System.out.println("Enter a name: ");
        name = sc.nextLine();

        System.out.println("Enter a description: ");
        description = sc.nextLine();

        do {
            invalid = true;
            System.out.println("Enter a price: ");
            try {
                price = sc.nextDouble();
                sc.nextLine();

                if (price < 0.01) {
                    System.out.println("Please enter a price greater than 0.01");
                    continue;
                }
                invalid = false;
            } catch (Exception ex) {
                System.out.println("Please enter a valid double");
            }
        } while (invalid);

        if(createPackage){
            System.out.println("Enter the IDs of items to be included in package (-1) to end: ");
            int idToAdd = 0;
            do{
                try{
                    idToAdd = sc.nextInt();
                    sc.nextLine();
                    packageItems.add(MenuItemMgr.getInstance().getMenuItemByID(idToAdd));
                }catch(Exception ex){
                    System.out.println("Please enter a valid ID");
                }
            }while(idToAdd > 0);
        }

        try{
            MenuItemMgr.getInstance().createMenuItem(type, name, description, price, id, packageItems);
        }catch(Exception ex){
            System.out.println("An error occured while creating menu item");
        }
    }

}
