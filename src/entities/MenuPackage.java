package entities;

import java.util.ArrayList;

import enums.MenuItemState;

public class MenuPackage extends MenuItem {
    private ArrayList<MenuItem> items;

    public MenuPackage(){
        super();
    }

    public MenuPackage(String type, String name, String description, double price, int stocks, MenuItemState state,
            String id) {
        super(type, name, description, price, stocks, state, id);
        this.items = new ArrayList<MenuItem>();
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        String mainDesc = super.getDescription() + "\n This package consists of the following items:\n";
        for (MenuItem item : this.items) {
            mainDesc+= item.getName() + "\n";
        }
        return mainDesc;
    }
}
