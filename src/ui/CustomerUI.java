package ui;

import entities.Customer;
import entities.DiscountFilter;
import entities.Membership;
import enums.DiscountFilterNameEnum;
import global.AvailPriceFilter;
import managers.CustomerMgr;
import managers.MembershipMgr;

public final class CustomerUI extends UserInterface {
    private static CustomerUI INSTANCE;

    private CustomerUI() {
        super();
    }

    public static CustomerUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerUI();
        }

        return INSTANCE;
    }
    
    public Customer getCustomer() {
        String contact = super.getContact("Customer contact: ");
        try {
            return CustomerMgr.getInstance().getExistingCustomer(contact);
        } catch(Exception ex) {
            return createCustomer(contact);
        }
    }

    private Customer createCustomer(String contact) {
        String name = super.getInputString("Customer name: ");
        String gender = super.getGender("Customer gender: ");
        Membership membership = this.getMembershipInput();

        return CustomerMgr.getInstance().createCustomer(membership, name, gender, contact);
    }

    private Membership getMembershipInput() {

        int validMembershipOption;
        validMembershipOption = super.getInputInt("Please enter your desired membership.\n 1. Gold Membership\n 2. Silver Membership\n 3. Bronze Membership\n 4. No membership");
        Membership membership = createMembership(validMembershipOption);
        return membership;
    }

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
