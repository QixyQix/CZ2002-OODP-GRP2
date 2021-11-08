package ui;

import java.util.Scanner;

import entities.Invoice;
import managers.InvoiceMgr;


public class InvoiceUI extends UserInterface{
    private static InvoiceUI INSTANCE;
    private Scanner sc;

    private InvoiceUI() {
        super();
        this.sc = new Scanner(System.in);
    }

    public static InvoiceUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceUI();
        }

        return INSTANCE;
    }

    private void displayOptions() {
        System.out.println("====Payment Manager====");
        System.out.println("(1) Close order and create invoice");
        System.out.println("(2) Print order invoice");
    }


    private void createInvoice() {
        
        int orderid;

        while(true) {
            orderid = super.getInputInt("Enter your order id");    
        }
    }
    
}
