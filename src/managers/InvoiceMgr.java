package managers;

import java.util.HashMap;
import managers.Manager;
import entities.Invoice;
import entities.PriceFilter;
import entities.DiscountFilter;
import entities.TaxFilter;
import enums.PriceFilterTypeEnum;
import enums.TaxFilterNameEnum;

class InvoiceMgr{

    private static InvoiceMgr instance;
    private HashMap<Integer,Invoice> invoices;
    private int invoiceid;

    private InvoiceMgr(){
        importing();
        // this.invoiceid = this.invoices.size() +1;
    };

    private static void importing(){
        // this.invoices = ... //import from Json;
        // this.invoiceid import from json
    }
    public static void exporting(){
        // export to Json;
    }
    public static InvoiceMgr getInstance(){
        if(instance == null){
            importing();
            instance = new InvoiceMgr();
        }
        return instance;
    }

    private Invoice createInvoice(Order order){
        Invoice invoice = new Invoice(order, this.invoiceid);
        invoices.put(this.invoiceid, invoice);
        this.invoiceid +=1;
        this.choosePriceFilter(this.invoiceid);
        return invoice;
    }

    private void choosePriceFilter(int invoiceid){
        Invoice invoice = this.invoices.get(invoiceid);
        // Need depends on KT & Ben
        Membership membership = invoice.getOrder().getCustomer().getMembership();
        PriceFilter membershipDiscountFilter = membership.getDiscount();
        // we noted this for membership class
        invoice.addPriceFilters(membershipDiscountFilter);
        
        PriceFilter gstFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.GST, 7);
        PriceFilter serviceChargeFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.SERVICE_CHARGE, 10));
        invoice.addPriceFilters(gstFilter);
        invoice.addPriceFilters(serviceChargeFilter);
    }
    
    public HashMap<Integer,Invoice> getInvoicesMap() {
        return this.invoices;
    }
}