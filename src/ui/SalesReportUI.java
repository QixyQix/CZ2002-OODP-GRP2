package ui;

import managers.SalesReportMgr;

import java.time.LocalDate;


/***
 * Represents a Sales Report UI
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
public final class SalesReportUI extends UserInterface{
    /**
     * The Instance of this SalesReportUI
     */
    private static SalesReportUI INSTANCE;

    /**
     * Constructor
     */
    private SalesReportUI() {
        super();
    }

    /**
     * Returns the SalesReportUI instance and creates an instance if it does not exist
     * 
     * @return SalesReportUI Instance
     */
    public static SalesReportUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SalesReportUI();
        }

        return INSTANCE;
    }

    /**
     * Display the options of Selection Page
     * 
     */
    private void displayOptions(){
        System.out.println("=========SalesReport Manager==========");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Total Revenue only");
        System.out.println("(2) Revenue For each MenuItem only");
        System.out.println("(3) Both");
        System.out.println("======================================");
    }

    /**
     * Show the Selection Page of SaleReport UI for User to Select Options
     * 
     */
    public void showSelection(){
        int option;
        LocalDate startDate;
        LocalDate endDate;
        do{
            displayOptions();
            option = super.getInputInt("Please enter your choice: ",0,3);
            if(option == 0) break;

            startDate = super.getInputDate("Enter start date (YYYY-MM-DD)");
            endDate = super.getInputDate("Enter end date (YYYY-MM-DD)");
            switch(option){
                case 1:
                    SalesReportMgr.getInstance().getReport(startDate, endDate, true, false);
                    break;
                case 2:
                    SalesReportMgr.getInstance().getReport(startDate, endDate, false, true);
                    break;
                case 3: 
                    SalesReportMgr.getInstance().getReport(startDate, endDate, true, true);
                    break;
            }
            
            waitEnter();
        }while(option!=0); 
        
    }

    
    /**
     *  Get User Input to confirm creation of report
     * 
     */
    public void createreport(){
        if(super.getYNOption("Are you sure it is end of the day? "))
            SalesReportMgr.getInstance().createReport(LocalDate.now() );

        super.waitEnter();
    }



}
