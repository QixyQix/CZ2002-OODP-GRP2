package entities;

import java.util.ArrayList;
import enums.MenuItemTypeEnum;

/***
 * Represents a menu package entity
 * 
 * @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public class MenuPackage extends MenuItem {
    /**
     * the List of Items contained in this MenuPackage
     */
    private ArrayList<MenuItem> items;

    /**
     * Constructor
     */
    public MenuPackage() {
        super();
    }

    /**
     * Constructor
     * @param type          The type of the MenuPackage
     * @param name          The name of the MenuPackage
     * @param description   The description of the MenuPackage
     * @param price         The price of the MenuPackage
     * @param id            The id of the MenuPackage
     */
    public MenuPackage(MenuItemTypeEnum type, String name, String description, double price, int id,
            ArrayList<MenuItem> items) {
        super(type, name, description, price, id);
        this.items = items;
    }

    /**
     * Returns items
     * 
     * @return Hashmap of items
     */
    public ArrayList<MenuItem> getItems() {
        return this.items;
    }

    /**
     * Sets menu items
     * 
     * @param items menu items
     */
    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    /**
     * Returns description
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        String mainDesc = super.getDescription() + " Package consists of ";
        for (MenuItem item : this.items) {
            mainDesc += item.getName() + "  ";
        }

        return mainDesc;
    }
    //TODO
    /*
     * @Override public String getName(){ String mainName = super.getName();
     * for(MenuItem item: this.items){ mainName+= "\n          " + item.getName(); }
     * return mainName; }
     */
}
