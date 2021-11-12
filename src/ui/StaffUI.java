package ui;
import managers.StaffMgr;
import entities.Staff;

public class StaffUI extends UserInterface {
    private static StaffUI INSTANCE;

    private StaffUI(){}

    public static StaffUI getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new StaffUI();
        }

        return INSTANCE;
    }
    
    private void displayOptions() {
        System.out.println("====Welcome to Restaurant Reservation and Point of Sale System (RRPSS) ====");
        System.out.println("(0) End System");
        System.out.println("(1) Enter the system");
        System.out.println("(2) Register a Staff account");
        System.out.println("===========================================================================");
        System.out.println(" ");
    }

    public Staff staffSelectionscreen(){
        int option;
        do{
            displayOptions();
            option = super.getInputInt("Enter your selection: ", 1, 3);
            switch(option){
                case 1:
                    return enterSystem();
                case 2:
                    registerAccount();
                    break;
                case 3: 
                    return null;
            }
            super.waitEnter();
            

        }while(true);

    }

    private Staff enterSystem(){
        int staffID;
        Staff staff = null;
        staffID = super.getInputInt("Please enter your StaffID: ");
        try{
            staff = StaffMgr.getInstance().checkexisitingStaff(staffID);
        }
        catch(Exception ex){
            System.out.println("Invalid StaffID / StaffID not Found");
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
