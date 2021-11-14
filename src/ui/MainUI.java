package ui;
import java.io.IOException;

import entities.Staff;
import global.AvailPriceFilter;
import managers.*;

/***
 * Represents a Main UI
 * 
 * @author Lee Zong Yu 
 * @version 1.0
 * @since 2021-11-14
 */
public class MainUI extends UserInterface{
    /**
     * The Instance of this MainUI
     */
    private static MainUI INSTANCE;

    /**
     * Constructor
     */
    private MainUI(){}

    /**
     * Returns the MainUI instance and creates an instance if it does not exist
     * 
     * @return MainUI instance
     */
    public static MainUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainUI();
        }

        return INSTANCE;
    }
    
    /** 
     *   return true if Data succesfully Saved
     * @return boolean
     */  
    private boolean endsystem() {
       
        try{
            
            CustomerMgr.getInstance().saveData();
            InvoiceMgr.getInstance().saveData();
            MenuItemMgr.getInstance().saveData();
            OrderMgr.getInstance().saveData();
            ReservationMgr.getInstance().saveData();
            SalesReportMgr.getInstance().saveData();
            StaffMgr.getInstance().saveData();
            TableMgr.getInstance().saveData();
            MembershipMgr.getInstance().saveData();
            return true;
             
        }catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("Data are not save properly !"); 
            super.waitEnter();
            return false;
        }
        
    }
    /**
     * Load the data by getting the first Instance of all Managers
     */
    private void loadData(){
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
    /** 
     *   Initial Booting of System
     * 
     */  
    public void systemBoot(){
        this.loadData();
        Staff staff;
        do{
            staff = StaffUI.getInstance().showSelection();
        
            if(staff == null) {
        
                if(!endsystem()) continue;
                // Make sure data is save properly
                break;
            }
            else {
                entersystem(staff);
                System.out.println("Succesfully Log Out");
                super.waitEnter();
            }
          
        
        }while(true);
        System.out.println("GoodBye and Thank you for using our system !!");


    }
    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions(){
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(0) Log Out");
        System.out.println("(1) Create/Modify MenuItem/Promotion");
        System.out.println("(2) Create/Modify Order");
        System.out.println("(3) Create/Modify Reservation");
        System.out.println("(4) Create/Check Table Availability");
        System.out.println("(5) Make Payment");
        System.out.println("(6) Show Sale Revenue Report");
        System.out.println("(7) Generate today's report ");
        System.out.println("(8) Update Membership");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }
    
    /**
     *  Show the Selection Page of Customer UI for User to Select Options
     * @param staff Staff that uses the system
     */
    private void entersystem(Staff staff){
        System.out.println(" Hello Mr/Ms " + staff.getName() + " Staff id : " + staff.getId()); 
        int option; 
        super.setStaff(staff);
        do{
            displayOptions();
            option = super.getInputInt("Enter your selection: ", 0, 8);
            switch(option){
                case 1:
                    MenuItemUI.getInstance().showSelection();
                    break;
                case 2:
                    OrderUI.getInstance().showSelection();
                    break;
                case 3: 
                    ReservationUI.getInstance().showSelection();
                    break;
                case 4:
                    TableUI.getInstance().showSelection();
                    break;
                case 5:
                    InvoiceUI.getInstance().showSelection();;
                    break;
                case 6:
                    SalesReportUI.getInstance().showSelection();
                    break;
                case 7:
                    SalesReportUI.getInstance().createreport();
                    break;
                case 8:
                    CustomerUI.getInstance().showSelection();
                    break;
            }
            

        }while(option != 0);

    }

    
    
    
}
