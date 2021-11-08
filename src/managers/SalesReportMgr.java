package managers;

import java.util.HashMap;
import java.util.TreeMap;
import java.time.LocalDate;
import entities.Invoice;
import entities.Report;
import entities.MenuItem;

public final class SalesReportMgr {
    private static SalesReportMgr instance;
    private HashMap<Integer,Report> reports;
    private HashMap<LocalDate,Integer> reports_day;
    private int reportid;

    private SalesReportMgr(){
        importing();
        // this.invoiceid = this.invoices.size() +1;
    };

    private void importing(){
        // this.invoices = ... //import from serialization;
        // this.invoiceid import from serialization
    }

    public  void exporting(){
        // export to serialization;
    }

    public static SalesReportMgr getInstance(){
        if(instance == null){
            instance = new SalesReportMgr();
        }
        return instance;
    }

    private void addInvoiceToReport(Report report, HashMap<Integer,Invoice> invoices, LocalDate targetDay){
        for(HashMap.Entry<Integer,Invoice> entry : invoices.entrySet()){
            Invoice invoice = entry.getValue();
            if( invoice.getOrder().getDate().toLocalDate() == targetDay){
                report.addInvoiceList(invoice);
            }
        }
    }

    public void createReport(LocalDate targetDay, InvoiceMgr invoiceMgr) {
        // end of the day
        HashMap<Integer,Invoice> invoices = invoiceMgr.getInvoicesMap();
        Report report = new Report(targetDay);
        this.reports.put(reportid,report);
        this.reports_day.put(targetDay,reportid);
        
       
        this.addInvoiceToReport( report, invoices, targetDay);
        reportid++;
    }

    private Report findReportByDay(LocalDate targetDay) {
        return this.reports.get(this.reports_day.get(targetDay));
    }

    private TreeMap<MenuItem,Double>  addtomenuItemRevenue(TreeMap<MenuItem,Double> menuItemTotalRevenue, TreeMap<MenuItem,Double>  menuItemRevenue){
        for(MenuItem item : menuItemRevenue.keySet() ){
            double val = 0;
            if(menuItemTotalRevenue.containsKey(item)) val = menuItemTotalRevenue.get(item);
            val += menuItemRevenue.get(item) * item.getPrice();
            
            // not sure will overwrite or not
            menuItemTotalRevenue.put(item,val);
        }
        return menuItemTotalRevenue;
    }

    private void printTotalRevenue(double totalrevenue){
        System.out.println("Total Revenue : " + totalrevenue + "Sgd ");
        System.out.println("including discounts and taxes");
    }

    private void printMenuItemTotalRevenue(TreeMap<MenuItem,Double> menuItemTotalRevenue){
        System.out.println("Details Revenue Report for each MenuItem");
        for( MenuItem item : menuItemTotalRevenue.keySet()){
            // REpeat for invoice 
            System.out.println( item.getName() + " " + menuItemTotalRevenue.get(item) + "sgd");
        }
    }

    public void getReport(LocalDate startDate, LocalDate endDate, boolean total, boolean items) {
        // TODO for UI, tranlate month to startdate to enddate)
        if(!total & !items) return;
        double totalRevenue = 0;
        TreeMap<MenuItem,Double> menuItemTotalRevenue = new TreeMap<MenuItem,Double> ();
        for(LocalDate date= startDate; date!=endDate; date.plusDays(1)){
            Report report = this.findReportByDay(startDate);
            if(total) totalRevenue += report.getTotalRevenue();
            if(items) menuItemTotalRevenue = addtomenuItemRevenue(menuItemTotalRevenue, report.getMenuItemRevenue() );
        }
        
        System.out.println("Restaurant Name ...");// Some formatting with date
        System.out.println("Sale Revenue Report from " + startDate.toString() + " to " + endDate.toString());
        if(total) printTotalRevenue(totalRevenue);
        if(items) printMenuItemTotalRevenue(menuItemTotalRevenue);// TO DO for menuitems may need * the filter.
    }

    
}
