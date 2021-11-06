package factories;

import entities.MenuItem;
import entities.MenuPackage;
import enums.MenuItemState;

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

    public MenuItem createMenuItem(String itemType, String type, String name, String description, double price, int stocks, MenuItemState state, int id, MenuItem[] packageItems) throws ClassNotFoundException {
        switch (itemType.toUpperCase()) {
        case "ALACARTE":
            MenuItem alaCarteItem = new MenuItem(type, name, description, price, stocks, state, id);
            return alaCarteItem;
        case "PACKAGE":
            MenuItem packageItem = new MenuPackage(type, name, description, price, stocks, state, id);
            return packageItem;
        default:
            throw new ClassNotFoundException();
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
