package managers;

import java.util.HashMap;
import java.util.TreeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import entities.Entities;
import entities.Invoice;
import entities.Report;
import entities.MenuItem;

/***
 * Represents a sales report manager
 * 
 * @author Zong Yu Lee
 */
public final class SalesReportMgr extends DataMgr {
    private static SalesReportMgr instance;
    private HashMap<Integer, Report> reports;
    private HashMap<LocalDate, Integer> reports_day;
    private int nextId;

    private SalesReportMgr() {
        try {
            this.reports = new HashMap<Integer, Report>();
            this.reports_day = new HashMap<LocalDate, Integer>();

            downcast(super.loadSavedData("reports"));
            nextId = super.loadNextIdData("reportNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reports data");
        }
    };

    
    private void downcast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Report)
                this.reports.put(id,(Report) object.get(id));
            else throw new ClassCastException();
        }
    }

    private HashMap<Integer, Entities> upcast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: reports.keySet()){
           object.put(id,reports.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upcast(), nextId, "reports", "reportNextId");
    }

    

    /**
     * Returns the SalesReportMgr instance and creates an instance if it does not
     * exist
     * 
     * @return instance
     */
    public static SalesReportMgr getInstance() {
        if (instance == null) {
            instance = new SalesReportMgr();
        }
        return instance;
    }

    /**
     * Add Invoice object to report
     * 
     * @param report   Report object to add Invoice object to
     * @param invoices hashmap of invoiceId and Invoice object
     * @param date     date
     * 
     */
    private void addInvoiceToReport(Report report, HashMap<Integer, Invoice> invoices, LocalDate targetDay) {
        for (HashMap.Entry<Integer, Invoice> entry : invoices.entrySet()) {
            Invoice invoice = entry.getValue();
            if (invoice.getOrder().getDate().toLocalDate() == targetDay) {
                report.addInvoiceList(invoice);
            }
        }
    }

    /**
     * Create a new Report object
     * 
     * @param targetDay date
     * 
     */
    public void createReport(LocalDate targetDay) {
        // end of the day
        // error handling;
        if (reports_day.containsKey(targetDay))
            return;
        HashMap<Integer, Invoice> invoices = InvoiceMgr.getInstance().getInvoicesMap();
        Report report = new Report(targetDay);
        this.reports.put(nextId, report);
        this.reports_day.put(targetDay, nextId);

        this.addInvoiceToReport(report, invoices, targetDay);
        nextId++;
    }

    /**
     * Returns a report corresponding to the date
     * 
     * @param date date
     * @return Report object
     */
    private Report findReportByDay(LocalDate targetDay) {
        return this.reports.get(this.reports_day.get(targetDay));
    }

    private TreeMap<MenuItem, Double> addtomenuItemRevenue(TreeMap<MenuItem, Double> menuItemTotalRevenue,
            TreeMap<MenuItem, Double> menuItemRevenue) {
        for (MenuItem item : menuItemRevenue.keySet()) {
            double val = 0;
            if (menuItemTotalRevenue.containsKey(item))
                val = menuItemTotalRevenue.get(item);
            val += menuItemRevenue.get(item) * item.getPrice();

            // not sure will overwrite or not
            menuItemTotalRevenue.put(item, val);
        }
        return menuItemTotalRevenue;
    }

    /**
     * Print total revenue made
     * 
     * @param totalRevenue total revenue made
     *
     */
    private void printTotalRevenue(double totalRevenue) {
        System.out.println("Total Revenue : " + totalRevenue + "Sgd ");
        System.out.println("including discounts and taxes");
    }

    /**
     * Print total revenue by MenuItem
     * 
     * @param menuItemTotalRevenue total revenue of menu item
     * 
     */
    private void printMenuItemTotalRevenue(TreeMap<MenuItem, Double> menuItemTotalRevenue) {
        System.out.println("Details Revenue Report for each MenuItem");
        for (MenuItem item : menuItemTotalRevenue.keySet()) {
            // REpeat for invoice
            System.out.println(item.getName() + " " + menuItemTotalRevenue.get(item) + "sgd");
        }
    }

    /**
     * Print report by start and end date and update total revenue and total revenue
     * of that specific menu item
     * 
     * @param startDate start date
     * @param endDate   end date
     * @param total     total revenue
     * @param items     menu items
     * 
     */
    public void getReport(LocalDate startDate, LocalDate endDate, boolean total, boolean items) {
        // TODO for UI, tranlate month to startdate to enddate)
        if (!total & !items)
            return;
        double totalRevenue = 0;
      
        TreeMap<MenuItem, Double> menuItemTotalRevenue = new TreeMap<MenuItem, Double>();

        for (LocalDate date = startDate; !date.isEqual(endDate.plusDays(1)); date =date.plusDays(1)) {
            try{
                Report report = this.findReportByDay(startDate);
                if (total)
                    totalRevenue += report.getTotalRevenue();
                if (items)
                    menuItemTotalRevenue = addtomenuItemRevenue(menuItemTotalRevenue, report.getMenuItemRevenue());
            }catch(Exception ex){

            }
        }

        System.out.println("Restaurant Name ...");// Some formatting with date
        System.out.println("Sale Revenue Report from " + startDate.toString() + " to " + endDate.toString());
        if (total)
            printTotalRevenue(totalRevenue);
        if (items)
            printMenuItemTotalRevenue(menuItemTotalRevenue);// TO DO for menuitems may need * the filter.
    }

}
