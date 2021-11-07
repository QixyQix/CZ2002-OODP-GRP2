package factories;

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

    public MenuItem createMenuItem(String itemType) throws ClassNotFoundException {
        switch (itemType.toUpperCase()) {
        case "ALACARTE":
            return new MenuItem();
        case "PACKAGE":
            return new MenuPackage();
        default:
            throw new ClassNotFoundException();
        }
    }
}
