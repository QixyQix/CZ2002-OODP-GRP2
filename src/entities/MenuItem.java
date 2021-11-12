package entities;

import java.io.Serializable;
import enums.MenuItemTypeEnum;

public class MenuItem implements Serializable, Comparable<MenuItem>, Cloneable, Entities{
    private MenuItemTypeEnum type;
    private String name;
    private String description;
    private double price;
    private int id;

    public MenuItem(){}

    public MenuItem(MenuItemTypeEnum type, String name, String description, double price, int id) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }


    public MenuItemTypeEnum getType() {
        return this.type;
    }

    public void setType(MenuItemTypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return this.name.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(MenuItem o){
        return ( this.name.compareTo(o.name) );
        
    }

    @Override
    // Haven,t test yet TODO
	public Object clone() {//throws CloneNotSupportedException {
        try{
            MenuItem menuItem = (MenuItem) super.clone();
            return menuItem;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        
    }

}
    
