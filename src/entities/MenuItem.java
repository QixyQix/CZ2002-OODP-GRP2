package entities;

import enums.MenuItemState;

public class MenuItem {
    private String type;
    private String name;
    private String description;
    private double price;
    private int stocks;
    private MenuItemState state;
    private String id;

    public MenuItem(){}

    public MenuItem(String type, String name, String description, double price, int stocks, MenuItemState state, String id) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stocks = stocks;
        this.state = state;
        this.id = id;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
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

    public int getStocks() {
        return this.stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public MenuItemState getState() {
        return this.state;
    }

    public void setState(MenuItemState state) {
        this.state = state;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}