import ui.MainUI;

import java.io.IOException;
import global.CurrentTime;

class Main{

    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        // two way 
        // Actually we maybe do not need to getInstance first cuz in future when we use the mgr it will also be using getInstance..
        /*
        CustomerMgr customerMgr = CustomerMgr.getInstance();
        InvoiceMgr invoiceMgr = InvoiceMgr.getInstance();
        MenuItemMgr menuItemMgr = MenuItemMgr.getInstance();
        OrderMgr orderMgr = OrderMgr.getInstance();
        SalesReportMgr salesReportMgr = SalesReportMgr.getInstance();
        StaffMgr staffMgr = StaffMgr.getInstance();
        // TableMgr
        // ReservationMgr

        InvoiceUI invoiceUI = InvoiceUI.getInstance();
        MenuItemUI menuItemUI = MenuItemUI.getInstance();
        OrderUI orderUI = OrderUI.getInstance();
        SalesReportUI salesReportUI = SalesReportUI.getInstance();
        StaffUI staffUI = StaffUI.getInstance();
        // Reservation UI
        */

        // is it better if we use getInstance? or use above code..
        
        CurrentTime.loadSavedData();
        
        MainUI.getInstance().systemBoot();  
    }  
}