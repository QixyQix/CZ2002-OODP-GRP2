import ui.MainUI;

import java.io.IOException;

import entities.Membership;
import global.CurrentTime;
import entities.*;
import managers.*;
import enums.*;
import factories.*;
import exceptions.*;
import ui.*;

class Main{

    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        // CurrentTime.loadSavedData();
         MainUI.getInstance().systemBoot();  
        //Membership m = new Membership();
        StaffUI.getInstance();
        CustomerUI.getInstance();
        InvoiceUI.getInstance();
        MenuItemUI.getInstance();
        OrderUI.getInstance();
        ReservationUI.getInstance();
        SalesReportUI.getInstance();
        TableUI.getInstance();
        CustomerMgr.getInstance();
        InvoiceMgr.getInstance();
        MenuItemMgr.getInstance();
        OrderMgr.getInstance();
        ReservationMgr.getInstance();
        SalesReportMgr.getInstance();
        StaffMgr.getInstance();
        TableMgr.getInstance();

        
        CurrentTime.saveData();
    }  
}