package entities;

import java.util.ArrayList;
import enums.MenuItemTypeEnum;

/***
 * Represents a menu package entity
 * 
 * @author Cho Qi Xiang
 */
public class MenuPackage extends MenuItem {
    private ArrayList<MenuItem> items;

    public MenuPackage() {
        super();
    }

    /**
     * Constructor
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
        String mainDesc = super.getDescription() + "\nThis package consists of the following items:\n";
        for (MenuItem item : this.items) {
            mainDesc += item.getName() + ", ";
        }
        return mainDesc;
    }
    /*
     * @Override public String getName(){ String mainName = super.getName();
     * for(MenuItem item: this.items){ mainName+= "\n          " + item.getName(); }
     * return mainName; }
     */
}
