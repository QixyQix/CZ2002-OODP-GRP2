package ui;
import java.io.IOException;

import entities.Staff;
import managers.*;
public class MainUI extends UserInterface{
    private static MainUI INSTANCE;

    private MainUI(){}

    public static MainUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainUI();
        }

        return INSTANCE;
    }
    
    private boolean endsystem() {
        // will it be better if we have a polymorphism, a Singleton Interface  and below when we get instance, we store all the Managers..
        
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

    
    public void systemBoot(){
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

    private void displayOptions(){
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(0) Log Out");
        System.out.println("(1) Alter MenuItem/Promotion");
        System.out.println("(2) Open Order");
        System.out.println("(3) Open Reservation");
        System.out.println("(4) Create/Check Table Availability");
        System.out.println("(5) Print Order invoice");
        System.out.println("(6) Print Sale Revenue Report");
        System.out.println("(7) End of the day (report)");
        System.out.println("(8) Update Membership");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }
    
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
