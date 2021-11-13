import ui.MainUI;

import java.io.IOException;
import global.AvailPriceFilter;
import managers.*;

/***
 * Represents a MainApplication
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
class Main{
    
    public static void loadData(){
        AvailPriceFilter.createPriceFilters();
        OrderMgr.getInstance();
        CustomerMgr.getInstance();
        StaffMgr.getInstance();
        MenuItemMgr.getInstance();
        TableMgr.getInstance();
        ReservationMgr.getInstance();
        
    
        SalesReportMgr.getInstance();
        InvoiceMgr.getInstance();
        MembershipMgr.getInstance();
    }
            
    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        loadData();
        
        MainUI.getInstance().systemBoot();  
    }  
}