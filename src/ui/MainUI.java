package ui;
import java.io.IOException;

import entities.Staff;
import global.CurrentTime;
import managers.*;
public class MainUI extends UserInterface{
    private static MainUI INSTANCE;

    public static MainUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainUI();
        }

        return INSTANCE;
    }
    
    private void endsystem() throws IOException{
        // will it be better if we have a polymorphism, a Singleton Interface  and below when we get instance, we store all the Managers..
        
        CustomerMgr.getInstance().saveData();
        InvoiceMgr.getInstance().saveData();
        //MenuItemMgr.getInstance().saveData();
        OrderMgr.getInstance().saveData();
        SalesReportMgr.getInstance().saveData();
        StaffMgr.getInstance().saveData();
        CurrentTime.saveData();
        // TableMgr
        // ReservationMgr 
    }


    private void displayOptions(){
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(1) Alter MenuItem/Promotion");
        System.out.println("(2) Open Order");
        System.out.println("(3) Open Reservation");
        System.out.println("(4) Check Table Availability");
        System.out.println("(5) Print Order invoice");
        System.out.println("(6) Print Sale Revenue Report");
        System.out.println("(7) End of the day (report)");
        System.out.println("(8) Log Out");
        System.out.println("(9) ChangeCurrentTime");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }
    
    private void entersystem(Staff staff){
        System.out.println(" Hello Mr/Ms " + staff.getName() + " Staff id : " + staff.getId()); // TODO Mr/Ms based on the gender
        int option;
        
        do{
            displayOptions();
            option = super.getInputInt("Enter your selection: ", 1, 9);
            switch(option){
                case 1:
                    MenuItemUI.getInstance().showMenu();
                    break;
                case 2:
                    
                    //OrderUI.getInstance().
                    break;
                case 3: 
                    ReservationUI.getInstance().selectOption();
                    break;
                case 4:
                    // TableUI.getInstance().
                    break;
                case 5:
                    // InvoiceUI.getInstance();
                    break;
                case 6:
                    // SaleReportUI.getInstance();
                    break;
                case 7:
                    // SaleReportUI.getInstance();
                    break;
                case 9:
                    CurrentTime.setCurrentTime();
                    break;
            }
            

        }while(option != 8);

    }

    public void systemBoot() throws IOException{
        do{
            Staff staff = StaffUI.getInstance().staffSelectionscreen();
            if(staff == null) {
                endsystem();
                break;
            }
            else {
                entersystem(staff);
                System.out.println("Succesfully Log Out");
                super.getInputString("Enter to continue ... ");
            }
        
        }while(true);
        System.out.println("GoodBye and Thank you for using our system !!");


    }
    
    
}
