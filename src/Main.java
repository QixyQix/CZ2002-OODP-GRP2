
import ui.*;
import managers.*;
import entities.Staff;

class Main{ 

    private static void entersystem(Staff staff){
        System.out.println(" Hello Mr/Ms " + staff.getName() ); // TODO Mr/Ms based on the gender
        System.out.println(" Welcome to the Restaurant Reservation and Point of Sale System");

        
    }

    private static void endsystem(){
        // will it be better if we have a polymorphism, a Singleton Interface  and below when we get instance, we store all the Managers..
        
        CustomerMgr.getInstance().saveData();
        InvoiceMgr.getInstance().saveData();
        //MenuItemMgr.getInstance().saveData();
        OrderMgr.getInstance().saveData();
        SalesReportMgr.getInstance().saveData();
        StaffMgr.getInstance().saveData();
        
        // TableMgr
        // ReservationMgr

     
    }
    public static void main(String arg[]){
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
        Staff staff = StaffUI.getInstance().staffSelectionscreen();
        if(staff == null) endsystem();
        else entersystem(staff);
        
        System.out.println("GoodBye and Thank you for using our system !!");
    }  
}