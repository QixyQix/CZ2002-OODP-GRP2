package managers;

import java.util.HashMap;
import java.util.TreeMap;
import java.io.IOException;
import java.time.LocalDate;

import entities.Entities;
import entities.Invoice;
import entities.Report;
import entities.MenuItem;
import entities.MenuPackage;

/***
 * Represents a sales report manager
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
public final class SalesReportMgr extends DataMgr {
    /**
     * The Instance of this SalesReportMgr
     */
    private static SalesReportMgr INSTANCE;
    /**
     * The mapping of Report ID to its respective object
     */
    private HashMap<Integer, Report> reports;
    /**
     * The mapping of report date to its respective report id
     */
    private HashMap<LocalDate, Integer> reports_day;
    /**
     * The next Id to be use in creating sales report
     */
    private int nextId;

    /**
     * Constructor
     */
    private SalesReportMgr() {
        try {
            this.reports = new HashMap<Integer, Report>();
            this.reports_day = new HashMap<LocalDate, Integer>();

            downCast(super.loadSavedData("reports"));

            this.convertToDate();
            this.nextId = super.loadNextIdData("reportNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reports data");
        }
    };
    

    /**
     * Convert reports to a HashMap of Key Date and Value Id
     * 
     */
    private void convertToDate(){
        for(int i : reports.keySet()){
            Report report = reports.get(i);
            reports_day.put(report.getDate(),report.getId());
        }
    }
    
    /**
     * Downcast from entities to SalesReport
     * 
     * @param object the entities to downcast
     */
    public void downCast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Report)
                this.reports.put(id,(Report) object.get(id));
            else throw new ClassCastException();
        }
    }

    /**
     * Upcast SalesReport to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: reports.keySet()){
           object.put(id,reports.get(id)); 
        }
        return object;
    }
    
    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "reports", "reportNextId");
    }

    

    /**
     * Returns the SalesReportMgr instance and creates an instance if it does not
     * exist
     * 
     * @return instance
     */
    public static SalesReportMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SalesReportMgr();
        }
        return INSTANCE;
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
            if (invoice.getOrder().getDate().toLocalDate().isEqual(targetDay) ){
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
        if (reports_day.containsKey(targetDay)){
            System.out.println("Duplicated Exists");
            return;
        }
        HashMap<Integer, Invoice> invoices = InvoiceMgr.getInstance().getInvoicesMap();
        Report report = new Report(targetDay);
        this.reports.put(nextId, report);
        this.reports_day.put(targetDay, nextId);

        this.addInvoiceToReport(report, invoices, targetDay);
        report.calculateTotalRevenue();
        nextId++;
        System.out.println("Report for date " + targetDay.toString() + " was Succesfully Created ");
    }

    /**
     * Returns a report corresponding to the date
     * 
     * @param date date
     * @return Report object
     */
    private Report findReportByDay(LocalDate targetDay) {
         
        return this.reports.get( this.reports_day.get(targetDay));
    }

    /**
     * Add menuitemrevenue to existing menu item revenue
     * 
     * @param menuItemTotalRevenue  total revenue made for each item
     * @param menuItemRevenue       total revenue to be added   
     * @return treemap of menu item revenue
     *
     */
    private TreeMap<MenuItem, Double> addtomenuItemRevenue(TreeMap<MenuItem, Double> menuItemTotalRevenue,
            TreeMap<MenuItem, Double> menuItemRevenue) {

        for (MenuItem item : menuItemRevenue.keySet()) {
            double val = 0;
            if (menuItemTotalRevenue.containsKey(item)){
                val = menuItemTotalRevenue.get(item);
            }
            val += menuItemRevenue.get(item);

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
        System.out.println("==========================================");
        System.out.printf("%30s : %.2f Sgd\n" ,"Total Revenue ", totalRevenue);
        System.out.printf("(including discounts and taxes)\n");
    }

    /**
     * Print total revenue by MenuItem
     * 
     * @param menuItemTotalRevenue total revenue of menu item
     * 
     */
    private void printMenuItemTotalRevenue(TreeMap<MenuItem, Double> menuItemTotalRevenue) {
        System.out.println("==========================================");
        System.out.println("Details Revenue Report for each MenuItem");

        for (MenuItem item : menuItemTotalRevenue.keySet()) {
            // REpeat for invoice
            if(item instanceof MenuPackage){
                System.out.printf("%30s : %.2f\n", item.getName(), menuItemTotalRevenue.get(item) );
                for (MenuItem packageItem : ((MenuPackage)item).getItems() ){
                    System.out.printf("      %20s\n", packageItem.getName());
                } 
            }
            else {
                if(item.getId()==1)
                System.out.printf("%30s : %.2f\n",item.getName() ,  menuItemTotalRevenue.get(item)) ;
                else 
                System.out.printf("%30s : %.2f\n",item.getName() ,  menuItemTotalRevenue.get(item)) ;
            }
        }
            
    }

    /**
     * Print report by start and end date and update total revenue and total revenue
     * of that specific menu item
     * 
     * @param startDate start date
     * @param endDate   end date
     * @param total     boolean to print total revenue
     * @param items     boolean to print menu items
     * 
     */
    public void getReport(LocalDate startDate, LocalDate endDate, boolean total, boolean items) {
        if (!total & !items)
            return;
        double totalRevenue = 0;
      
        TreeMap<MenuItem, Double> menuItemTotalRevenue = new TreeMap<MenuItem, Double>();

        for (LocalDate date = startDate; !date.isEqual(endDate.plusDays(1)); date = date.plusDays(1)) {
            try{
                Report report = this.findReportByDay(startDate);
                
                if (total)
                    totalRevenue += report.getTotalRevenue();
                if (items)
                    menuItemTotalRevenue = addtomenuItemRevenue(menuItemTotalRevenue, report.getMenuItemRevenue());
            }catch(Exception ex){
                
            }
        }

        System.out.println(" Group 2 Restaurant ");
        System.out.println();
        System.out.println("Sale Revenue Report ");
        System.out.println("from " + startDate.toString() + " to " + endDate.toString());
        
        if (total)
            printTotalRevenue(totalRevenue);
        if (items)
            printMenuItemTotalRevenue(menuItemTotalRevenue);
        
        System.out.println("==========================================");
    }

}
