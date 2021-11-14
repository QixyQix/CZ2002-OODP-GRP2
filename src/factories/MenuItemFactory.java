package factories;

import java.util.ArrayList;

import entities.MenuItem;
import entities.MenuPackage;
import enums.MenuItemTypeEnum;

/***
 * Represents a id not found exception
 * 
 * @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public final class MenuItemFactory {
    /**
     * The Instance of this MenuItemFactory
     */
    private static MenuItemFactory INSTANCE;

    /**
     * Constructor
     */
    private MenuItemFactory() {
    }

    /**
     * Returns the MenuItemFactory instance and creates an instance if it does not
     * exist
     * 
     * @return MenuItemFactory instance
     */
    public static MenuItemFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemFactory();
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
     * @param packageItems menu items in a package
     * 
     * @return menu item object
     * 
     * @throws ClassNotFoundException if object is not of menu item class
     */
    public MenuItem createMenuItem(MenuItemTypeEnum type, String name, String description, double price, int id,
            ArrayList<MenuItem> packageItems) throws ClassNotFoundException {
        if (packageItems != null) {
            return new MenuPackage(type, name, description, price, id, packageItems);
        } else {
            return new MenuItem(type, name, description, price, id);
        }
    }

    /**
     * Create a menu item
     * 
     * @param o serialized data
     * 
     * @return menu item object
     * 
     * @throws ClassNotFoundException if object is not of menu item class
     */
    public MenuItem createMenuItemFromSerializedData(Object o) throws ClassNotFoundException {
        if (o instanceof MenuPackage) {
            return (MenuPackage) o;
        } else if (o instanceof MenuItem) {
            return (MenuItem) o;
        } else {
            throw new ClassNotFoundException();
        }
    }
}
