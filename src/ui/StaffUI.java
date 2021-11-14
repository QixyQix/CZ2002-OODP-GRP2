package ui;
import managers.StaffMgr;
import entities.Staff;
import exceptions.IDNotFoundException;

/***
 * Represents a Staff UI
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
public class StaffUI extends UserInterface {
    /**
     * The Instance of this StaffUI
     */
    private static StaffUI INSTANCE;
    /**
     * Constructor
     */
    private StaffUI(){}

    /**
     * Returns the StaffUI instance and creates an instance if it does not exist
     * 
     * @return StaffUI Instance
     */
    public static StaffUI getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new StaffUI();
        }

        return INSTANCE;
    }
    
    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions() {
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(0) End System");
        System.out.println("(1) Enter the system");
        System.out.println("(2) Register a Staff account");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }

    /**
     * Show the Selection Page of Staff UI for User to Select Options
     * @return return Staff, return none if End System
     */
    public Staff showSelection(){
        int option;
        do{
            displayOptions();
            option = super.getInputInt("Enter your selection: ", 0, 2);
            switch(option){
                case 1:
                    try{
                        return this.enterSystem();
                    }
                    catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    registerAccount();
                    break;
            }
            super.waitEnter();
        }while(option !=0);
        return null;
    }
    
    /**
     *  Get User Input to get the staff to enter the system
     * 
     * @throws IDNotFoundException
     * @return Staff entering the system
     */
    private Staff enterSystem() throws IDNotFoundException{
        int staffID;
        staffID = super.getInputInt("Please enter your StaffID: ");
        return StaffMgr.getInstance().checkexisitingStaff(staffID);
    }

    /**
     *  Get User Input to register a Staff Account  
     * 
     */
    private void registerAccount(){
        String jobTitle;
        String name;
        String gender;
        String contact;
        int option;
        do{
            jobTitle = super.getInputString("Please enter your jobTitle : ");
            name = super.getInputString("Please enter your name :");
            gender = super.getGender("Please enter your gender: "); 
            contact = super.getContact("Please enter your contact number: ");
            
            option = super.getInputInt("Do you confirm with the selection. (1) YES (2) NO (0) To Exit: " , 0, 2);
        }while(option ==2) ;

        if(option==0) return;

        Staff staff = StaffMgr.getInstance().createStaff(jobTitle, name, gender, contact);
        
        System.out.println("The Staff have been successfully registered with the following details");
        System.out.println("StaffId = " + staff.getId() );
        System.out.println("Please Remember your StaffId");

        
    }

    



}
