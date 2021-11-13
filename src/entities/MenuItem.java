package entities;

import java.io.Serializable;
import enums.MenuItemTypeEnum;

/***
 * Represents a menu item entity
 * 
 * @author Cho Qi Xiang
 */
public class MenuItem implements Serializable, Comparable<MenuItem>, Cloneable, Entities {
    private MenuItemTypeEnum type;
    private String name;
    private String description;
    private double price;
    private int id;

    public MenuItem() {
    }

    /**
     * Constructor
     */
    public MenuItem(MenuItemTypeEnum type, String name, String description, double price, int id) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    /**
     * Returns menu item type
     * 
     * @return menu item type
     */
    public MenuItemTypeEnum getType() {
        return this.type;
    }

    /**
     * Sets menu item type
     * 
     * @param type menu item type
     */
    public void setType(MenuItemTypeEnum type) {
        this.type = type;
    }

    /**
     * Returns name
     * 
     * @return name
     */
    public String getName() {
        return this.name.toString();
    }

    /**
     * Sets name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns description
     * 
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns price
     * 
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets price
     * 
     * @return price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Compares menu items
     * 
     * @param o menu item
     * @return comparison
     */
    @Override
    public int compareTo(MenuItem o) {
        return (this.name.compareTo(o.name));

    }

    /**
     * Returns cloned menu item
     * 
     * @return cloned menu item
     */
    @Override
    public Object clone() {// throws CloneNotSupportedException {
        try {
            MenuItem menuItem = (MenuItem) super.clone();
            return menuItem;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

}
