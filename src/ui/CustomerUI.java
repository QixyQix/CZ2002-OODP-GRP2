package ui;

import java.util.Scanner;

import managers.CustomerMgr;

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

    private void createMembership() {
                
    }

    private void createCustomer() {
        CustomerMgr.getInstance().createCustomer(membership, name, gender, contact)
    }
}
