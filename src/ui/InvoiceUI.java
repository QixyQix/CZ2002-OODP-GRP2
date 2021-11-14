package ui;

import entities.Invoice;
import entities.Order;
import managers.InvoiceMgr;
import managers.OrderMgr;
import managers.TableMgr;

/***
 * Represents a Invoice UI
 * 
  * @author Lee Zong Yu 
 *  @author Lim Yan Kai
 *  @version 1.0
 *  @since 2021-11-14
 */
public class InvoiceUI extends UserInterface{
    private static InvoiceUI INSTANCE;

    /**
     * Constructor
     */
    private InvoiceUI() {
        super();
    }

    /**
     * Returns the InvoiceUI instance and creates an instance if it does not exist
     * 
     * @return InvoiceUI instance
     */
    public static InvoiceUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceUI();
        }

        return INSTANCE;
    }
    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions() {
        System.out.println("====Payment Manager================");
        System.out.println("(0) Exit");
        System.out.println("(1) Close order and create invoice");
        System.out.println("(2) Print order invoice");
        System.out.println("===================================");
        System.out.println();
    }

    /**
     * Show the Selection Page of Invoice UI for User to Select Options
     * 
     */
    public void showSelection() {
        int option = 0;
        do {
            this.displayOptions();
            
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

    /** 
     *   Get User Input to create Invoice Object
     * 
     */  
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
        
        if(!getYNOption("Are you sure you want to make payment for the order?")) return;
        invoice = InvoiceMgr.getInstance().createInvoice(order);  
        
        try{
            System.out.println("Invoice has been created with Invoice id = " + invoice.getId());
            TableMgr.getInstance().deallocateTable(order.getTable(), order.getDate());
            System.out.println("Table ID= "+order.getTable().getId()+" has become available.");
        }catch(Exception ex){
            return;
        }
    }

    /** 
     *   Get User Input to print Invoice
     * 
     */  
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
