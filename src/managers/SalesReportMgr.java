package managers;

import java.util.HashMap;
import java.time.LocalDate;
import entities.Report;

public class SalesReportMgr {
    private static SalesReportMgr instance;
    private HashMap<Integer,Report> reports;
    private int invoiceid;

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
            importing();
            instance = new SalesReportMgr();
        }
        return instance;
    }

    public void createReport(LocalDate targetDay) {
        
    }

    public void getReport(LocalDate startDate, LocalDate endDate) {

    }

    private Report findReportByDay(LocalDate targetDay) {
        
    }
}
