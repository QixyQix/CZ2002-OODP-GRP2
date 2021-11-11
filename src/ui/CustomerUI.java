package ui;

import java.util.Scanner;

import entities.DiscountFilter;
import entities.Membership;
import enums.DiscountFilterNameEnum;
import global.AvailPriceFilter;
import managers.CustomerMgr;
import managers.MembershipMgr;

public final class CustomerUI extends UserInterface {
    private static CustomerUI INSTANCE;
    private Scanner sc;

    private CustomerUI() {
        super();
        this.sc = new Scanner(System.in);
    }

    public static CustomerUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerUI();
        }

        return INSTANCE;
    }

    public void showMenu() {
        int option = 0;
        do {
            displayOptions();
            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
                case 1:
                    this.createCustomer();
                    break;
            }
        } while (option != -1);
    }

    private void displayOptions() {
        System.out.println("====Customer Manager====");
        System.out.println("(1) Create customer");
        System.out.println("(-1) Exit");
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

    private void createCustomer() {
        String name = super.getInputString("Customer name: ");
        String contact = super.getContact("Customer contact: ");
        String gender = super.getGender("Customer gender: ");
        Membership membership = this.getMembershipInput();

        CustomerMgr.getInstance().createCustomer(membership, name, gender, contact);
        System.out.println("Customer have been created");
    }
}
