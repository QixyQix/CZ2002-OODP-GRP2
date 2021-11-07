package managers;

import java.util.HashMap;

import entities.MenuItem;

public final class MenuItemMgr {
    private static MenuItemMgr INSTANCE;
    private HashMap<Integer, MenuItem> items;
    
    private MenuItemMgr(){}

    public static MenuItemMgr getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MenuItemMgr();
        }

        return INSTANCE;
    }
}
