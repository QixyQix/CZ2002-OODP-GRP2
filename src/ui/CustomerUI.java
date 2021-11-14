package ui;

import entities.Customer;
import entities.DiscountFilter;
import entities.Membership;
import enums.DiscountFilterNameEnum;
import global.AvailPriceFilter;
import managers.CustomerMgr;
import managers.MembershipMgr;

/***
 * Represents a Customer UI
 * 
  * @author Lee Zong Yu 
 *  @author Lim Yan Kai
 *  @version 1.0
 *  @since 2021-11-14
 */
public final class CustomerUI extends UserInterface {
    /**
     * The Instance of this CustomerUI
     */
    private static CustomerUI INSTANCE;
    
    /**
     * Constructor
     */
    private CustomerUI() {
        super();
    }
    
    /**
     * Returns the CustomerUI instance and creates an instance if it does not exist
     * 
     * @return CustomerUI instance
     */
    public static CustomerUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerUI();
        }

        return INSTANCE;
    }

    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions(){
        System.out.println("==========Customer Manager==========");
        System.out.println("(0) Go Back to Main Page Exit");
        System.out.println("(1) Update Membership");
        System.out.println("=================================");
    }

    /**
     * Show the Selection Page of Customer UI for User to Select Options
     * 
     */
    public void showSelection(){
        int option = 0;
        do {
            displayOptions();
            
            option = super.getInputInt("Enter your option", 0, 1);

            switch (option) {
                case 1:
                    this.updateMembership();
                    break;
            }
            super.waitEnter();
        } while (option != 0);
    }
    /**
     * 
     *  Get User Input to update Membership
     */
    private void updateMembership(){
        try{
            String contact = super.getContact("Please enter customer contact: ");
            CustomerMgr.getInstance().getExistingCustomer(contact);
            Membership membership = this.getMembershipInput();
            if(!super.getYNOption("Are you sure you want to update your Membership?")) return;
            CustomerMgr.getInstance().updateMembership(contact ,membership);
        } catch(Exception ex){
            System.out.println("Customer Not Exists");
        }
    }
    
    /**
     * 
     *   Get User Input to get the Customer Object
     * @return Customer Object
     */  
    public Customer getCustomer() {
        String contact = super.getContact("Please enter customer contact: ");
        try {
            return CustomerMgr.getInstance().getExistingCustomer(contact);
        } catch(Exception ex) {
            if(super.getYNOption("Do you want to register as our Customer?"))
                return createCustomer(contact);
            else 
                return null;
        }
    }
    /**
     *   Get User Input to create Customer Object
     * @return Customer Object
     */  
    private Customer createCustomer(String contact) {
        String name = super.getInputString("Customer name: ");
        String gender = super.getGender("Customer gender: ");
        Membership membership = this.getMembershipInput();

        return CustomerMgr.getInstance().createCustomer(membership, name, gender, contact);
    }
    /** 
     *  Get User Input to get Membership Object
     * @return Membership Object
     */  
    private Membership getMembershipInput() {

        int validMembershipOption;
        validMembershipOption = super.getInputInt("Please enter your desired membership.\n 1. Gold Membership\n 2. Silver Membership\n 3. Bronze Membership\n 4. No membership", 1 , 4);
        Membership membership = createMembership(validMembershipOption);
        return membership;
    }

    /** 
     *   Get User Input to create Membership Object
     * @return Membership Object
     */  
    private Membership createMembership(int validMembershipOption) {
        DiscountFilter discountFilter;
        Membership membership;
        
        switch(validMembershipOption) {
            case 1:
                discountFilter = AvailPriceFilter.discountFilter.get(DiscountFilterNameEnum.GOLD_MEMBERSHIP);
                membership = MembershipMgr.getInstance().createMembership(discountFilter);
                System.out.println("Gold Membership created");
                break;
            case 2:
                discountFilter = AvailPriceFilter.discountFilter.get(DiscountFilterNameEnum.SILVER_MEMBERSHIP);
                membership = MembershipMgr.getInstance().createMembership(discountFilter);
                System.out.println("Silver Membership created");
                break;
            case 3:
                discountFilter = AvailPriceFilter.discountFilter.get(DiscountFilterNameEnum.BRONZE_MEMBERSHIP);
                membership = MembershipMgr.getInstance().createMembership(discountFilter);
                System.out.println("Bronze Membership created");
                break;
            default:
                discountFilter = AvailPriceFilter.discountFilter.get(DiscountFilterNameEnum.NO_MEMBERSHIP);
                membership = MembershipMgr.getInstance().createMembership(discountFilter);
                System.out.println("No membership needed.");
                break;
        }

        return membership;
    }

    
}
