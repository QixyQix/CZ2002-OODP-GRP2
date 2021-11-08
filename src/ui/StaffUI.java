package ui;
import managers.StaffMgr;
import entities.Staff;
import java.util.Scanner;

import javax.print.DocPrintJob;

public class StaffUI extends UserInterface {
    private static StaffUI INSTANCE;
    private Scanner sc;

    private void importing(){

    }

    public void exporting(){

    }

    private StaffUI(){
        importing();
        this.sc = new Scanner(System.in);
    }

    public static StaffUI getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new StaffUI();
        }

        return INSTANCE;
    }

    private void displayOptions() {
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(1) Enter the system");
        System.out.println("(2) Register a Staff account");
        System.out.println("(3) End System");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }

    private Staff enterSystem(){
        int staffID;
        Staff staff;
        staffID = super.getInputInt("Please enter your StaffID: ");
        staff = StaffMgr.getInstance().checkexisitingStaff(staffID);
        while(staff == null){
            System.out.println("Invalid StaffID / StaffID not Found");
            staffID = super.getInputInt("Please enter your StaffID (Enter -1 to exit): ");
            if(staffID == -1) return null;
            staff = StaffMgr.getInstance().checkexisitingStaff(staffID);
        }

        return staff;
        

    }

    private void registerAccount(){
        String jobTitle;
        String name;
        String gender;
        String contact;
        int option;
        do{
            jobTitle = super.getInputString("Please enter your jobTitle : ");
            name = super.getInputString("Please enter your name :");
            gender = super.getInputString("Please enter your gender: "); // A bit not makes sense
            contact = super.getInputString("Please enter your contact number: ");
            
            option = super.getInputInt("Are you happy with the selection. (1) YES (2) NO : " , 1, 2);
        }while(option ==2 );


        Staff staff = StaffMgr.getInstance().createStaff(jobTitle, name, gender, contact);
        
        System.out.println("The Staff have been successfully registered with the following details");
        System.out.println("StaffId = " + staff.getId() );
        System.out.println("Please Remember your StaffId");

        super.getInputString("Please Enter to continue ... ");
    }

    public Staff staffSelectionscreen(){
        int option;
        Staff staff = null;
        do{
            displayOptions();
            option = super.getInputInt("Enter your selection: ", 1, 3);
            switch(option){
                case 1:
                    staff = enterSystem();
                    break;
                case 2:
                    registerAccount();
                    break;
                case 3: 
                    return null;
            }
            

        }while(staff == null);

        return staff;
    }



}
