import ui.MainUI;

import java.io.IOException;
import java.time.LocalDateTime;

import entities.Membership;
import global.CurrentTime;
import entities.*;
import managers.*;
import enums.*;
import factories.*;
import exceptions.*;
import ui.*;

class Main{
    
    public static void loadData(){

        Table table;
        Customer customer;
        DiscountFilter filter;
        Invoice i;
        Membership m;
        MenuItem menuIte;
        MenuPackage menp;
        Order o;
        Person p;
        PriceFilter pf;
        Report re;
        Reservation rese;
        Staff s;
        TaxFilter tfs;

        StaffUI.getInstance();
        CustomerUI.getInstance();
        InvoiceUI.getInstance();
        MenuItemUI.getInstance();
        OrderUI.getInstance();
        ReservationUI.getInstance();
        SalesReportUI.getInstance();
        TableUI.getInstance();


        OrderMgr.getInstance();
        CustomerMgr.getInstance();
        StaffMgr.getInstance();
        MenuItemMgr.getInstance();
        TableMgr.getInstance();

        ReservationMgr.getInstance();
        
    
        
        SalesReportMgr.getInstance();
        //InvoiceMgr.getInstance();
        try{
        CurrentTime.loadSavedData();
        } catch(Exception ex){
            System.out.println("Fail to load");
        }
    }
    /*
    private static boolean saveData(){
        try{
            CustomerMgr.getInstance().saveData();
            TableMgr.getInstance().saveData();
            StaffMgr.getInstance().saveData();
            MenuItemMgr.getInstance().saveData();
            
            ReservationMgr.getInstance().saveData();
           
            
            OrderMgr.getInstance().saveData();
            InvoiceMgr.getInstance().saveData();

            SalesReportMgr.getInstance().saveData();
            CurrentTime.saveData();
            return true;
            
        }catch(IOException e){
            // CHECK WITH QI XIANG WHETHER THIS CORRECT
            //System.out.println(e.getMessage());
            //System.out.println((e.getCause()));
            System.out.println("Data are not save properly !"); 
            return false;
        }
    }
    */
    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        loadData();
            /*
        UserInterface user = new UserInterface();
            LocalDateTime date = user.getInputDateTime("Enter time");
            Staff staff = StaffMgr.getInstance().checkexisitingStaff(1);
            Customer customer = CustomerMgr.getInstance().createCustomer(null, "Lee Zong Yu", "Male",  "88499185");
            Order order = OrderMgr.getInstance().createOrder( staff,  customer,  date, 2);
            //Report report = SalesReportMgr.getInstance().getReport(startDate, endDate, total, items);
            
            Invoice invoice = InvoiceMgr.getInstance().createInvoice(order);
            SalesReportMgr.getInstance().createReport(date.toLocalDate());
            */
          
       // Invoice invoice = InvoiceMgr.getInstance().getInvoicesMap().get(1);
        //Invoice invoice = InvoiceMgr.getInstance().getInvoicesMap().get(1);
        /*
        System.out.println(invoice.getOrder().getDate());
        System.out.println(invoice.getOrder().getServeby().getId());
        System.out.println(invoice.getOrder().getCustomer().getCustomerid());
        System.out.println(invoice.getOrder().getTable().getId());
    
        */
        
        //saveData();
        MainUI.getInstance().systemBoot();  
        //Membership m = new Membership();
        
        

        
        CurrentTime.saveData();
    }  
}