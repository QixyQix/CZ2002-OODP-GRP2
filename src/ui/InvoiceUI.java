package ui;

import java.util.Scanner;
import entities.Order;
import managers.InvoiceMgr;
import managers.OrderMgr;


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
                printOrderInvoice();
                break;
            case 2:
                createInvoice();
                break;
            }
        } while (option != -1);
    }

    private void displayOptions() {
        System.out.println("====Payment Manager====");
        System.out.println("(1) Close order and create invoice");
        System.out.println("(2) Print order invoice");
        System.out.println("(-1) Exit");
    }


    private void createInvoice() {
        
        int orderid;
        Order order;

        while(true) {
            orderid = super.getInputInt("Enter your order id");
            if (!OrderMgr.getInstance().checkAvailableOrder(orderid)) {
                System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
                continue;
            } else {
                order = OrderMgr.getInstance().getOrder(orderid);
                InvoiceMgr.getInstance().createInvoice(order);
                break;
            }
        }

        System.out.print("Invoice has been created");
    }

    private void printOrderInvoice() {
        int invoiceid;

        while(true) {
            invoiceid = super.getInputInt("Enter your invoice id");
            if (InvoiceMgr.getInstance().checkInvoice(invoiceid)) {
                InvoiceMgr.getInstance().getOrder(invoiceid).printInvoice();
                break;
            }
            System.out.println("Please enter a valid invoice id, invoice id: " + invoiceid + " is not valid");
        }
    }
    
}
