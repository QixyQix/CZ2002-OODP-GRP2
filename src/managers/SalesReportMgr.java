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
import entities.Invoice;
import entities.Report;
import entities.MenuItem;

public final class SalesReportMgr {
    private static SalesReportMgr instance;
    private HashMap<Integer, Report> reports;
    private HashMap<LocalDate, Integer> reports_day;
    private int reportId;

    private SalesReportMgr() {
        try {
            this.reports = new HashMap<Integer, Report>();
            this.reports_day = new HashMap<LocalDate, Integer>();

            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reports data");
        }
    };

    /***
     * Serializes and saves the Customers objects into the data/customers folder
     * Creates the data/customers folder if it does not exist
     * 
     * @author Zong Yu Lee
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/reports");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int reportID : this.reports.keySet()) {
            Report report = this.reports.get(reportID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/reports/" + reportID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(report);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/reportId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(reportId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @author Zong Yu Lee
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/reports");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Report report = (Report) objectInputStream.readObject();
            this.reports.put(report.getId(), report);
            this.reports_day.put(report.getDate(), report.getId());
            objectInputStream.close();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("reportId");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.reportId = (int) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            this.reportId = 1;
        }
    }

    /**
     * Returns the SalesReportMgr instance and creates an instance if it does not
     * exist
     * 
     * @author Zong Yu Lee
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
     * @author Zong Yu Lee
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
     * @author Zong Yu Lee
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
        this.reports.put(reportId, report);
        this.reports_day.put(targetDay, reportId);

        this.addInvoiceToReport(report, invoices, targetDay);
        reportId++;
    }

    /**
     * Returns a report corresponding to the date
     * 
     * @author Zong Yu Lee
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
     * @author Zong Yu Lee
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
     * @author Zong Yu Lee
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
     * @author Zong Yu Lee
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
        for (LocalDate date = startDate; date != endDate; date.plusDays(1)) {
            Report report = this.findReportByDay(startDate);
            if (total)
                totalRevenue += report.getTotalRevenue();
            if (items)
                menuItemTotalRevenue = addtomenuItemRevenue(menuItemTotalRevenue, report.getMenuItemRevenue());
        }

        System.out.println("Restaurant Name ...");// Some formatting with date
        System.out.println("Sale Revenue Report from " + startDate.toString() + " to " + endDate.toString());
        if (total)
            printTotalRevenue(totalRevenue);
        if (items)
            printMenuItemTotalRevenue(menuItemTotalRevenue);// TO DO for menuitems may need * the filter.
    }

}
