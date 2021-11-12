package managers;


import java.io.IOException;
import java.util.HashMap;

import entities.Entities;
import entities.Invoice;
import entities.PriceFilter;
import entities.TaxFilter;
import entities.Order;
import entities.Membership;
import enums.PriceFilterTypeEnum;
import enums.TaxFilterNameEnum;

/***
 * Represents an invoice manager
 * 
 * @author Zong Yu Lee
 */
public final class InvoiceMgr extends DataMgr {

    private static InvoiceMgr instance;
    private HashMap<Integer, Invoice> invoices;
    private int nextId;

    private InvoiceMgr() {
        try {
            this.invoices = new HashMap<Integer, Invoice>();
            downcast(super.loadSavedData("invoices"));
            nextId = super.loadNextIdData("invoiceNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load invoices data");
        }
    }
    
    private void downcast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Invoice)
                this.invoices.put(id,(Invoice) object.get(id));
            else throw new ClassCastException();
        }
    }

    private HashMap<Integer, Entities> upcast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: invoices.keySet()){
           object.put(id,invoices.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upcast(), nextId, "invoices", "invoiceNextId");
    }

    /**
     * Returns the InvoiceMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static InvoiceMgr getInstance() {
        if (instance == null) {
            instance = new InvoiceMgr();
        }
        return instance;
    }

    /**
     * Returns invoice object from order made
     * 
     * @param order customer order
     * @return created invoice
     */
    public Invoice createInvoice(Order order) {
        if(order.getPendingItems().size() !=0){
            // ask see should I print this at UI?
            System.out.println("The order contains Pending Items. You are not allowed to create invoice.");
            return null;
            
        }
        if(order.getStatus()=="Close"){
            System.out.println("The order have been paid");
        }
        Invoice invoice = new Invoice(order, this.nextId);
        invoices.put(this.nextId, invoice);
        this.choosePriceFilter(this.nextId);
        order.closeStatus();
        this.nextId += 1;
        return invoice;
    }

    /**
     * Add price filter to the invoice
     * 
     * @param invoiceid id of invoice
     * 
     */
    private void choosePriceFilter(int invoiceid) {
        Invoice invoice = this.invoices.get(invoiceid);
        // Need depends on KT & Ben
        Membership membership = invoice.getOrder().getCustomer().getMembership();
        PriceFilter membershipDiscountFilter = membership.getDiscount();
        // System.out.println(membership.getDiscount());
        // we noted this for membership class
        invoice.addPriceFilters(membershipDiscountFilter);
        // System.out.println(invoice.getPriceFilters());

        PriceFilter gstFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.GST, 7);
        PriceFilter serviceChargeFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE,
                TaxFilterNameEnum.SERVICE_CHARGE, 10);
        invoice.addPriceFilters(gstFilter);
        invoice.addPriceFilters(serviceChargeFilter);
    }

    /**
     * Returns true or false depending on whether the invoice corresponding to the
     * invoice id exists
     * 
     * @param invoiceid id of invoice
     * @return true if invoice exists, false if invoice does not exist
     */
    public boolean checkInvoice(int invoiceid) {
        return this.invoices.containsKey(invoiceid);
    }

    /**
     * Returns Invoice object corresponding to the invoice id
     * 
     * @param invoiceid id of invoice
     * @return Invoice object
     */
    public Invoice getOrder(int invoiceid) {
        return this.invoices.get(invoiceid);
    }

    /**
     * Returns Invoices hashmap
     * 
     * @return invoices hashmap
     */
    public HashMap<Integer, Invoice> getInvoicesMap() {
        return this.invoices;
    }
}