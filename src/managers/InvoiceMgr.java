package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import entities.Invoice;
import entities.PriceFilter;
import entities.TaxFilter;
import entities.Order;
import entities.Membership;
import enums.PriceFilterTypeEnum;
import enums.TaxFilterNameEnum;

public final class InvoiceMgr{

    private static InvoiceMgr instance;
    private HashMap<Integer,Invoice> invoices;
    private int invoiceId;

    
    /**
     * Constructor
     */
    private InvoiceMgr(){
        try {
            this.invoices = new HashMap<Integer,Invoice>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load invoices data");
        }
    }

     /***
     * Serializes and saves the Staffs objects into the data/staffs folder
     * Creates the data/staffs folder if it does not exist
     * 
     * @throws IOException
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/invoices");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int invoiceID : this.invoices.keySet()) {
            Invoice invoice = this.invoices.get(invoiceID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/invoices/" + invoiceID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            
            objectOutputStream.writeObject(invoice);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        
        FileOutputStream fileOutputStream = new FileOutputStream("./data/invoiceId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(this.invoiceId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }
    
    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/orders");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try{
            File file = new File("./data/orderId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.invoiceId = (int) objectInputStream.readInt();
            objectInputStream.close();
        }catch(Exception e){
            //System.out.println(e.getMessage());
            this.invoiceId = 1;
        }

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
           
            Invoice invoice = (Invoice) objectInputStream.readObject();
            this.invoices.put(invoice.getId(), invoice);
            objectInputStream.close();
        }
        
    }

    /**
     * Returns the InvoiceMgr instance and creates an instance if it does not exist
     * 
     * @return
     */
    public static InvoiceMgr getInstance(){
        if(instance == null){
            instance = new InvoiceMgr();
        }
        return instance;
    }

    /**
     * Creates invoice object from order made
     * 
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order){
        Invoice invoice = new Invoice(order, this.invoiceId);
        invoices.put(this.invoiceId, invoice);
        this.invoiceId +=1;
        this.choosePriceFilter(this.invoiceId);
        return invoice;
    }

    /**
     * add price filter to the invoice
     * 
     * @param invoice id
     * 
     */
    private void choosePriceFilter(int invoiceid){
        Invoice invoice = this.invoices.get(invoiceid);
        // Need depends on KT & Ben
        Membership membership = invoice.getOrder().getCustomer().getMembership();
        PriceFilter membershipDiscountFilter = membership.getDiscount();
        // we noted this for membership class
        invoice.addPriceFilters(membershipDiscountFilter);
        
        PriceFilter gstFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.GST, 7);
        PriceFilter serviceChargeFilter = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.SERVICE_CHARGE, 10);
        invoice.addPriceFilters(gstFilter);
        invoice.addPriceFilters(serviceChargeFilter);
    }
    
    /**
     * Check if invoice exists by invoice id
     * 
     * @param invoice id
     * @return true if invoice exists else false
     */
    public boolean checkInvoice(int invoiceid) {
        return this.invoices.containsKey(invoiceid);
    }

    /**
     * Get invoice by invoice id
     * 
     * @param invoice id
     * @return Invoice
     */
    public Invoice getOrder(int invoiceid) {
        return this.invoices.get(invoiceid);
    }

    /**
     * Get all invoices
     * 
     * @return invoices
     */
    public HashMap<Integer,Invoice> getInvoicesMap() {
        return this.invoices;
    }
}