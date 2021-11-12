package ui;

import entities.Invoice;
import entities.Order;
import managers.InvoiceMgr;
import managers.OrderMgr;


public class InvoiceUI extends UserInterface{
    private static InvoiceUI INSTANCE;


    private InvoiceUI() {
        super();
    }

    public static InvoiceUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceUI();
        }

        return INSTANCE;
    }

    private void displayOptions() {
        System.out.println("====Payment Manager================");
        System.out.println("(0) Exit");
        System.out.println("(1) Close order and create invoice");
        System.out.println("(2) Print order invoice");
        System.out.println("===================================");
        System.out.println();
    }

    public void showMenu() {
        int option = 0;
        do {
            displayOptions();
            
            option = super.getInputInt("Enter your option", 0, 3);

            switch (option) {
                case 1:
                    createInvoice ();
                    break;
                case 2:
                    printOrderInvoice();
                    break;
            }
            super.waitEnter();
        } while (option != 0);
    }

    private void createInvoice() {
        
        int orderid;
        Order order;
        Invoice invoice;
       
        orderid = super.getInputInt("Enter your order id");
        try{
            order = OrderMgr.getInstance().getOrder(orderid);
        }
        catch(Exception ex){
            System.out.println("Please enter a valid order id, order id: " + orderid + " is not valid");
            return;
        }

        invoice = InvoiceMgr.getInstance().createInvoice(order);       
        
        try{
            System.out.println("Invoice has been created with Invoice id = " + invoice.getId());
        }catch(Exception ex){
            return;
        }
    }

    private void printOrderInvoice() {
        int invoiceid;
        invoiceid = super.getInputInt("Enter your invoice id");
        try{
            InvoiceMgr.getInstance().getOrder(invoiceid).printInvoice();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("Please enter a valid invoice id, invoice id: " + invoiceid + " is not valid");
        }      
    }
    
}
