package ui;

import java.util.ArrayList;

import entities.MenuItem;
import entities.MenuPackage;
import enums.MenuItemTypeEnum;
import managers.MenuItemMgr;

/***
 * Represents a MenuItem UI
 * 
 * @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public final class MenuItemUI extends UserInterface {
    private static MenuItemUI INSTANCE;

    /**
     * Constructor
     */
    private MenuItemUI() {
        super();
    }

    /**
     * Returns the MenuItemUI instance and creates an instance if it does not exist
     * 
     * @return MenuItemUI Instance
     */
    public static MenuItemUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemUI();
        }

        return INSTANCE;
    }

    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions() {
        System.out.println("======Menu Item Manager======");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Show current MenuItems");
        System.out.println("(2) Create a Menu Item");
        System.out.println("(3) Edit a Menu Item");
        System.out.println("(4) Delete a Menu Item");
        System.out.println("=============================");
    }

    /**
     * Show the Selection Page of MenuItem UI for User to Select Options
     * 
     */
    public void showSelection() {
        int option = 0;
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");
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
            super.waitEnter();
        } while (option != 0);
    }

    /**
     * Show the type of items
     * 
     */
    private void displayType() {
        System.out.println("==Choice of Item Type==");
        System.out.println("1. MAIN_DISHES");
        System.out.println("2. SIDES");
        System.out.println("3. DRINKS");
        System.out.println("4. DESSERTS");
        System.out.println("5. SET_LUNCH");
        System.out.println("=======================");
    }

    /**
     * Selects enum from input
     * 
     * @param prompt
     * @return MenuItemTypeEnum
     */
    private MenuItemTypeEnum getInputType(String prompt) {
        displayType();
        switch (this.getInputInt(prompt, 1, 5)) {
        case 1:
            return MenuItemTypeEnum.MAIN_DISHES;
        case 2:
            return MenuItemTypeEnum.SIDES;
        case 3:
            return MenuItemTypeEnum.DRINKS;
        case 4:
            return MenuItemTypeEnum.DESSERTS;
        case 5:
            return MenuItemTypeEnum.SET_LUNCH;
        }
        return null;
    }

    /**
     * Shows current menu items
     * 
     */
    public void showCurrentMenuItems() {
        try {
            ArrayList<MenuItem> items = MenuItemMgr.getInstance().getAllMenuItems();
            if (items.size() == 0) {
                System.out.println("Currently, there is no menuItems");
                return;
            }

            System.out.println("These are our available MenuItems");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------");
            for (MenuItemTypeEnum type : MenuItemTypeEnum.values()) {
                Boolean flag = true;
                System.out.println("Menu Item Type : " + type.toString());
                for (MenuItem item : items) {
                    if (item.getType() != type)
                        continue;
                    if (flag) {
                        System.out.printf(" %-3s  %-25s  %-8s  %s\n", "ID", "Name", "Price(Sgd)", "  Desription ");
                        flag = false;
                    }
                    System.out.printf(" %-3d  %-25s  %-3.2f         %s\n", item.getId(), item.getName(),
                            item.getPrice(), item.getDescription());
                }
                if (flag) {
                    System.out.println("There is no MenuItems of type " + type.toString());
                }
                System.out.println(
                        "----------------------------------------------------------------------------------------------------");
            }

        } catch (Exception ex) {
            System.out.println("An error occured while getting all Menu Items:");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Creates menu items
     * 
     */
    private void createMenuItem() {
        boolean createPackage = false;

        MenuItemTypeEnum type;
        String name;
        String description;
        double price = 0.0;
        ArrayList<MenuItem> packageItems = null;

        if (super.getYNOption("Is this a promotional package?")) {
            createPackage = true;
        }

        type = this.getInputType("Enter the item type: ");
        name = super.getInputString("Enter the item name: ");
        description = super.getInputString("Enter the item description: ");
        price = super.getInputDouble("Enter the item price: ", 0.0, Double.MAX_VALUE);

        if (!super.getYNOption("Do you confirm with the selection. "))
            return;

        if (createPackage) {
            packageItems = new ArrayList<MenuItem>();
            packageItems = this.buildPackageItems();
        }

        try {
            MenuItemMgr.getInstance().createMenuItem(type, name, description, price, packageItems);
            System.out.println("Menu Item Created");
        } catch (Exception ex) {
            System.out.println("An error occured while creating menu item");
        }
    }

    /**
     * Edits current menu items
     * 
     */
    private void editMenuItem() {
        MenuItem itemToEdit = null;
        boolean loop = true;

        int id = super.getInputInt("Enter ID of item to edit: ");
        try {
            itemToEdit = MenuItemMgr.getInstance().getMenuItemByID(id);
            loop = false;
        } catch (Exception ex) {
            System.out.println("Invalid item ID");
            return;
        }

        loop = true;
        do {
            System.out.println("== EDIT ITEM ID: " + itemToEdit.getId() + " ==");
            System.out.println("(1) type: " + itemToEdit.getType());
            System.out.println("(2) name: " + itemToEdit.getName());
            System.out.println("(3) description: " + itemToEdit.getDescription());
            System.out.println("(4) price: " + itemToEdit.getPrice());
            if (itemToEdit instanceof MenuPackage) {
                System.out.println("(5) Change Promotional Package Items");
            }
            System.out.println("(0) Exit");
            int choice = super.getInputInt("Enter number of corresponding property to edit", 0, 5);
            switch (choice) {
            case 1:
                MenuItemTypeEnum newType = this.getInputType("Enter new value for type:");
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
                double newPrice = super.getInputDouble("Enter new value for price: ", 0.1, Double.MAX_VALUE);
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
                } else {
                    System.out.println("This is not a promotional package");
                }
            case 0:
                loop = false;
                break;
            }
        } while (loop);
    }

    /**
     * Deletes a menu item
     * 
     */
    private void deleteMenuItem() {
        int idToRemove = super.getInputInt("Enter ID of item to delete: ", 0, Integer.MAX_VALUE);
        try {
            MenuItemMgr.getInstance().deleteMenuItemByID(idToRemove);
            System.out.println("Item removed");
        } catch (Exception ex) {
            System.out.println("Invalid ID");

        }

    }

    /**
     * Builds package menu items
     * 
     */
    private ArrayList<MenuItem> buildPackageItems() {

        System.out.println("Enter the IDs of items to be included in promotional package (0 to end): ");
        int idToAdd = 0;
        ArrayList<MenuItem> packageItems = new ArrayList<MenuItem>();
        do {
            idToAdd = super.getInputInt("Enter ID: ", 0, Integer.MAX_VALUE);
            try {
                MenuItem itemToAdd = MenuItemMgr.getInstance().getMenuItemByID(idToAdd);
                if (itemToAdd instanceof MenuPackage) {
                    System.out.println("Not allowed to add a package to a package");
                } else {
                    packageItems.add(itemToAdd);
                    System.out.println("Added item " + itemToAdd.getName() + " to the package");
                }
            } catch (Exception ex) {
                if (idToAdd != 0)
                    System.out.println("Please enter a valid ID");
            }
        } while (idToAdd > 0);

        return packageItems;
    }

}
