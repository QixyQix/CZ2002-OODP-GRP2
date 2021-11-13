package managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entities;
import entities.MenuItem;
import entities.MenuPackage;
import enums.MenuItemTypeEnum;
import exceptions.IDNotFoundException;
import factories.MenuItemFactory;

/***
 * Represents a menu item manager
 * 
 * @author Cho Qi Xiang
 * 
 */
public final class MenuItemMgr extends DataMgr {
    private static MenuItemMgr INSTANCE;
    private HashMap<Integer, MenuItem> items;
    private int nextId;

    /**
     * Constructor
     */
    private MenuItemMgr() {
        this.items = new HashMap<Integer, MenuItem>();
        try {
            downCast(super.loadSavedData("menuitems"));
            this.nextId = super.loadNextIdData("menuItemNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load menu data");
        }
    }

    /**
     * Downcast from entities to menuItem
     * 
     * @param object
     */
    public void downCast(HashMap<Integer, Entities> object) {
        for (int id : object.keySet()) {
            try {
                MenuItem item = MenuItemFactory.getInstance().createMenuItemFromSerializedData(object.get(id));
                this.items.put(id, item);
            } catch (Exception ex) {
                System.out.println("Failed to downCast Data");
            }
        }
    }

    /**
     * Upcast menuItem to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast() {
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for (int id : items.keySet()) {
            object.put(id, items.get(id));
        }
        return object;
    }

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "menuitems", "menuItemNextId");
    }

    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static MenuItemMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemMgr();
        }

        return INSTANCE;
    }

    /**
     * Create a menu item
     * 
     * @param type         menu item type
     * @param name         menu item name
     * @param description  menu item description
     * @param price        menu item price
     * @param id           menu item id
     * @param packageItems menu items in a package
     * 
     */
    public void createMenuItem(MenuItemTypeEnum type, String name, String description, double price,
            ArrayList<MenuItem> packageItems) {

        try {
            MenuItem newItem = MenuItemFactory.getInstance().createMenuItem(type, name, description, price, nextId,
                    packageItems);
            this.items.put(newItem.getId(), newItem);
            nextId++;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


    /**
     * Returns a MenuItem object corresponding to the menu item id
     * 
     * @param id menu item id
     * @return MenuItem object that matches the menu item id
     * @throws IDNotFoundException if no such menu item corresponding to the id
     *                             exists
     */
    public MenuItem getMenuItemByID(int id) throws IDNotFoundException {
        if (!this.items.containsKey(id)) {
            throw new IDNotFoundException();
        }
        return this.items.get(id);
    }

    /***
     * Returns an ArrayList of MenuItem objects that are stored in the MenuItemMgr
     * 
     * @return ArrayList of MenuItem objects
     * @throws IDNotFoundException if no such menu item corresponding to the id
     *                             exists
     */
    public ArrayList<MenuItem> getAllMenuItems() throws IDNotFoundException {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        for (int id : this.items.keySet()) {
            items.add(getMenuItemByID(id));
        }
        return items;
    }

    /***
     * Deletes menu item corresponding to menu item id
     * 
     * @param id menu item id
     * @throws IDNotFoundException if no such menu item corresponding to the id
     *                             exists
     */
    public void deleteMenuItemByID(int id) throws IDNotFoundException {
        if (!this.items.containsKey(id)) {
            throw new IDNotFoundException();
        }
        this.items.remove(id);
        for (int key : this.items.keySet()) {
            MenuItem item = this.items.get(key);
            if (item instanceof MenuPackage) {
                MenuPackage menuPackage = (MenuPackage) item;
                ArrayList<MenuItem> packageItems = menuPackage.getItems();
                for (int i = 0; i < packageItems.size(); i++) {
                    if (packageItems.get(i).getId() == id) {
                        packageItems.remove(i);
                        System.out.println("Removed from package " + menuPackage.getName() + " items");
                        i--;
                    }
                }
            }
        }
        // TODO Remove alacarte items from packages
    }

}
