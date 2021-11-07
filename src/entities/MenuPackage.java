package entities;

import java.util.ArrayList;

public class MenuPackage extends MenuItem {
    private ArrayList<MenuItem> items;

    public MenuPackage(){
        super();
    }

    public MenuPackage(String type, String name, String description, double price, int id, ArrayList<MenuItem> items) {
        super(type, name, description, price, id);
        this.items = items;
    }

    public ArrayList<MenuItem> getItems(){
        return this.items;
    }

    public void setItems(ArrayList<MenuItem> items){
        this.items = items;
    }

    @Override
    public String getDescription() {
        String mainDesc = super.getDescription() + "\n This package consists of the following items:\n";
        for (MenuItem item : this.items) {
            mainDesc+= item.getName() + "\n";
        }
        return mainDesc;
    }
}
