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

/***
 * Represents an invoice manager
 * 
 * @author Zong Yu Lee
 */
public final class InvoiceMgr {

    private static InvoiceMgr instance;
    private HashMap<Integer, Invoice> invoices;
    private int invoiceId;

    private InvoiceMgr() {
        try {
            this.invoices = new HashMap<Integer, Invoice>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load invoices data");
        }
    }

    /***
     * Serializes and saves the invoice objects into the data/invoice folder Creates
     * the data/invoice folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
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
     * Reads Serialized invoice data in the data/invoice folder and stores it into
     * the invoices HashMap
     * 
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/invoices");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try {
            File file = new File("./data/invoiceId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.invoiceId = (int) objectInputStream.readInt();
            objectInputStream.close();
        } catch (Exception e) {
            // System.out.println(e.getMessage());
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
        Invoice invoice = new Invoice(order, this.invoiceId);
        invoices.put(this.invoiceId, invoice);
        this.choosePriceFilter(this.invoiceId);
        order.closeStatus();
        this.invoiceId += 1;
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