package ui;

import java.util.ArrayList;
import java.util.Scanner;

import entities.MenuItem;
import entities.MenuPackage;
import managers.MenuItemMgr;

public final class MenuItemUI extends UserInterface {
    private static MenuItemUI INSTANCE;
    private Scanner sc;

    private MenuItemUI() {
        super();
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
            case 3:
                editMenuItem();
                break;
            case 4:
                deleteMenuItem();
                break;
            }
        } while (option != -1);
    }

    private void displayOptions() {
        System.out.println("====Menu Item Manager====");
        System.out.println("(1) Show current MenuItems");
        System.out.println("(2) Create a Menu Item");
        System.out.println("(3) Edit a Menu Item");
        System.out.println("(4) Delete a Menu Item");
        System.out.println("(-1) Exit");
        System.out.println("Enter your selection: ");
    }

    private void showCurrentMenuItems() {
        System.out.println("MENU ITEMS:");
        try {
            ArrayList<MenuItem> items = MenuItemMgr.getInstance().getAllMenuItems();

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
        boolean createPackage = false;

        int id = 0;
        String type;
        String name;
        String description;
        double price = 0.0;
        ArrayList<MenuItem> packageItems = null;

        if (super.getInputString("Is this a package? Enter P for package.").equals("P")) {
            createPackage = true;
            packageItems = new ArrayList<MenuItem>();
        }

        while (true) {
            id = super.getInputInt("Enter an ID for the Menu Item", 0, Integer.MAX_VALUE);
            if (!MenuItemMgr.getInstance().checkIDAvailable(id)) {
                System.out.println("ID " + id + " has already been taken. Please enter another ID.");
                continue;
            } else {
                break;
            }
        }

        type = super.getInputString("Enter the item type: ");
        name = super.getInputString("Enter the item name: ");
        description = super.getInputString("Enter the item description: ");
        price = super.getInputDouble("Enter the item price: ", 0.0, Double.MAX_VALUE);

        if (createPackage) {
            packageItems = buildPackageItems();
        }

        try {
            MenuItemMgr.getInstance().createMenuItem(type, name, description, price, id, packageItems);
            System.out.println("Menu Item Created");
        } catch (Exception ex) {
            System.out.println("An error occured while creating menu item");
        }
    }

    private void deleteMenuItem() {
        while (true) {
            int idToRemove = super.getInputInt("Enter ID of item to delete: ", 0, Integer.MAX_VALUE);
            try {
                MenuItemMgr.getInstance().deleteMenuItemByID(idToRemove);
                System.out.println("Item removed");
                break;
            } catch (Exception ex) {
                System.out.println("Invalid ID");
            }
        }
    }

    private void editMenuItem() {
        MenuItem itemToEdit = null;
        boolean loop = true;

        do {
            int id = super.getInputInt("Enter ID of item to edit: ");
            try {
                itemToEdit = MenuItemMgr.getInstance().getMenuItemByID(id);
                loop = false;
            } catch (Exception ex) {
                System.out.println("Invalid item ID");
            }
        } while (loop);

        loop = true;
        do {
            System.out.println("== EDIT ITEM ID: " + itemToEdit.getId() + " ==");
            System.out.println("(1) type: " + itemToEdit.getType());
            System.out.println("(2) name: " + itemToEdit.getName());
            System.out.println("(3) description: " + itemToEdit.getDescription());
            System.out.println("(4) price: " + itemToEdit.getPrice());
            if (itemToEdit instanceof MenuPackage) {
                System.out.println("(5) Change package items");
            }
            System.out.println("(-1) Exit");
            int choice = super.getInputInt("Enter number of corresponding property to edit", -1, 5);
            switch (choice) {
            case 1:
                String newType = super.getInputString("Enter new value for type:");
                itemToEdit.setType(newType);
                System.out.println("New type saved.");
                break;
            case 2:
                String newName = super.getInputString("Enter new value for name:");
                itemToEdit.setName(newName);
                System.out.println("New name saved.");
                break;
            case 3:
                String newDesc = super.getInputString("Enter new value for description:");
                itemToEdit.setDescription(newDesc);
                System.out.println("New description saved.");
                break;
            case 4:
                double newPrice = super.getInputDouble("Enter new valuue for price: ", 0.1, Double.MAX_VALUE);
                itemToEdit.setPrice(newPrice);
                System.out.println("New price saved.");
                break;
            case 5:
                if (itemToEdit instanceof MenuPackage) {
                    MenuPackage packageItem = (MenuPackage) itemToEdit;
                    ArrayList<MenuItem> items = new ArrayList<MenuItem>();
                    items = buildPackageItems();
                    packageItem.setItems(items);
                    System.out.println("Items updated.");
                }else{
                    System.out.println("This is not a package");
                }
            case -1:
                loop = false;
                break;
            default:
                System.out.println("Invalid choice");
                break;
            }
        } while (loop);
    }

    private ArrayList<MenuItem> buildPackageItems() {
        System.out.println("Enter the IDs of items to be included in package (-1) to end: ");
        int idToAdd = 0;
        ArrayList<MenuItem> packageItems = new ArrayList<MenuItem>();
        do {
            idToAdd = super.getInputInt("Enter ID: ", -1, Integer.MAX_VALUE);
            try {
                MenuItem itemToAdd = MenuItemMgr.getInstance().getMenuItemByID(idToAdd);
                if (itemToAdd instanceof MenuPackage) {
                    System.out.println("Not allowed to add a package to a package");
                } else {
                    packageItems.add(itemToAdd);
                    System.out.println("Added item " + itemToAdd.getName() + " to package");
                }
            } catch (Exception ex) {
                System.out.println("Please enter a valid ID");
            }
        } while (idToAdd > 0);

        return packageItems;
    }

}
