package managers;

import java.util.HashMap;
import entities.Order;
import entities.MenuItem;

public final class OrderMgr {
    private static OrderMgr INSTANCE;
    private HashMap<Integer, MenuItem> items;
    
    public static OrderMgr getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrderMgr();
        }

        return INSTANCE;
    }
    public void allocateTable(){}
    public void chooseItem(){}
    public void closeOrder(Order order){}
    
}
