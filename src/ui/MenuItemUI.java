package ui;

import java.util.ArrayList;

import entities.MenuItem;
import entities.MenuPackage;
import enums.MenuItemTypeEnum;
import managers.MenuItemMgr;

public final class MenuItemUI extends UserInterface {
    private static MenuItemUI INSTANCE;
    

    private MenuItemUI() {
        super();
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

    private void displayOptions() {
        System.out.println("======Menu Item Manager======");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Show current MenuItems");
        System.out.println("(2) Create a Menu Item");
        System.out.println("(3) Edit a Menu Item");
        System.out.println("(4) Delete a Menu Item");
        System.out.println("=============================");
    }

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

    
    private void displayType (){
        System.out.println("==Choice of Item Type==");
        System.out.println("1. MAIN_DISHES");
        System.out.println("2. SIDES");
        System.out.println("3. DRINKS");
        System.out.println("4. DESSERTS");
        System.out.println("5. SET_LUNCH");
        System.out.println("=======================");
    }
    
    private MenuItemTypeEnum getInputType(String prompt){
        displayType();
        switch(this.getInputInt(prompt,1,5)){
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

    public void showCurrentMenuItems() {
        try {
            ArrayList<MenuItem> items = MenuItemMgr.getInstance().getAllMenuItems();
            
            MenuItemTypeEnum type = this.getInputType("Enter the type of MenuItem you want to show.");
            int i =0;

            for (MenuItem item : items) {
                if(item.getType() != type) continue;
                if(i==0){
                    System.out.println("Menu Item : ");
                    i=1;
                }
                System.out.println("ID: " + item.getId() + " | Type: " + item.getType() + " | Name: " + item.getName()
                        + " $" + item.getPrice());
                System.out.println(item.getDescription());
            }
            if(i==0){
                System.out.println("There is no MenuItems of this Type Yet");
            }
        } catch (Exception ex) {
            System.out.println("An error occured while getting all Menu Items:");
            System.out.println(ex.getMessage());
        }
    }



    private void createMenuItem() {
        boolean createPackage = false;

        
        MenuItemTypeEnum type;
        String name;
        String description;
        double price = 0.0;
        ArrayList<MenuItem> packageItems = null;
       
        if (super.getYNOption("Is this a package?")) {
            createPackage = true;
        }
    
        type = this.getInputType("Enter the item type: ");
        name = super.getInputString("Enter the item name: ");
        description = super.getInputString("Enter the item description: ");
        price = super.getInputDouble("Enter the item price: ", 0.0, Double.MAX_VALUE);

        if( !super.getYNOption("Do you confirm with the selection. " )) return;
        
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
                System.out.println("(5) Change package items");
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
            case 0:
                loop = false;
                break;
            }
        } while (loop);
    }
    
    private void deleteMenuItem() {   
        int idToRemove = super.getInputInt("Enter ID of item to delete: ", 0, Integer.MAX_VALUE);
        try {
            MenuItemMgr.getInstance().deleteMenuItemByID(idToRemove);
            System.out.println("Item removed");
        } catch (Exception ex) {
            System.out.println("Invalid ID");
            
        }
        
    }


    private ArrayList<MenuItem> buildPackageItems() {
        
        
        System.out.println("Enter the IDs of items to be included in package (0 to end): ");
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
                    System.out.println("Added item " + itemToAdd.getName() + " to package");
                }
            } catch (Exception ex) {
                if(idToAdd!=0)
                    System.out.println("Please enter a valid ID");
            }
        } while (idToAdd > 0);

        return packageItems;
    }

}
