package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entities;
import entities.MenuItem;
import entities.MenuPackage;
import exceptions.DuplicateIDException;
import exceptions.IDNotFoundException;
import factories.MenuItemFactory;

/***
 * Represents a menu item manager
 * 
 * @author Cho Qi Xiang
 * 
 */
public final class MenuItemMgr extends DataMgr{
    private static MenuItemMgr instance;
    private HashMap<Integer, MenuItem> items;
    private int nextId;
    private MenuItemMgr() {
        this.items = new HashMap<Integer, MenuItem>();
        try {
            downcast(super.loadSavedData("menuitems"));
            this.nextId = super.loadNextIdData("menuItemNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load menu data");
        }
    }

    private void downcast(HashMap<Integer, Entities> object) throws ClassNotFoundException{
        for(int id: object.keySet()){
            MenuItem item = MenuItemFactory.getInstance().createMenuItemFromSerializedData( object.get(id));
            this.items.put(id, item);
        }
    }

    private HashMap<Integer, Entities> upcast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: items.keySet()){
           object.put(id,items.get(id)); 
        }
        return object;
    }

    public void saveData() throws IOException {
        saveDataSerialize(upcast(), nextId, "menuitems", "menuItemNextId");
    }
    
    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static MenuItemMgr getInstance() {
        if (instance == null) {
            instance = new MenuItemMgr();
        }

        return instance;
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
     * @throws DuplicateIDException if menu item with the same id exists
     * 
     */
    public void createMenuItem(String type, String name, String description, double price, int id,
            ArrayList<MenuItem> packageItems) throws DuplicateIDException {
        if (this.items.containsKey(id)) {
            throw new DuplicateIDException();
        }

        try {
            MenuItem newItem = MenuItemFactory.getInstance().createMenuItem(type, name, description, price, id,
                    packageItems);

            this.items.put(newItem.getId(), newItem);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Returns true or false depending on whether id is in use
     * 
     * @param id menu item id
     * @return true if id can be assigned to new object, false if id already in use
     */
    public boolean checkIDAvailable(int id) {
        return !this.items.containsKey(id);
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
