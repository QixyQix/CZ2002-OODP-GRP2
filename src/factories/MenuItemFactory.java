package factories;

import java.util.ArrayList;

import entities.MenuItem;
import entities.MenuPackage;

public final class MenuItemFactory {
    private static MenuItemFactory INSTANCE;

    private MenuItemFactory() {
    }

    public static MenuItemFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemFactory();
        }

        return INSTANCE;
    }

    public MenuItem createMenuItem(String type, String name, String description, double price, int id, ArrayList<MenuItem> packageItems) throws ClassNotFoundException {
        if(packageItems != null){
            return new MenuPackage(type, name, description, price, id, packageItems);
        }else{
            return new MenuItem(type, name, description, price, id);
        }
    }

    public MenuItem createMenuItemFromSerializedData(Object o) throws ClassNotFoundException{
        if(o instanceof MenuPackage){
            return (MenuPackage) o;
        }else if(o instanceof MenuItem){
            return (MenuItem) o;
        }else{
            throw new ClassNotFoundException();
        }
    }
}
