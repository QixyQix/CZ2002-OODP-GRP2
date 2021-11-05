package managers;

import managers.Manager;

Class InvoiceMgr implements Manager{

    private static InvoiceMgr instance;
    private HashMap<int,Invoice> invoices;
    private int invoiceid;

    private InvoiceMgr(){};

    private void importing(){
        this.invoices = ... //import from Json;
    }
    public void exporting(){
        // export to Json;
    }
    public static InvoiceMgr getInstance(){
        if(this.instance == null){
            importing();
            this.instance = new InvoiceMgr()
            this.invoiceid = this.invoices.size() +1;
        }
        return this.instance;
    }

    public Invoice createInvoice(Order order){
        Invoice invoice = new Invoice(order, this.invoiceid);
        invoices.put(this.invoiceid, invoice);
        this.invoiceid +=1;
        return invoice;
    }

    public void choosePriceFilter(int invoiceid){
        Invoice invoice = this.invoices.get(invoiceid);
        // Need depends on KT & Ben
        Membership membership = invoice.getOrder().getCustomer().getMembership();
        PriceFilter discount = membership.getDiscount();
        // we noted this for membership class
        
        invoice.addPriceFilters();


        

        
    }
    

    

}