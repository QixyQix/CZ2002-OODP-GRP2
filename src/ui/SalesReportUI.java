package ui;

import entities.Invoice;
import entities.Report;
import global.CurrentTime;
import managers.InvoiceMgr;
import managers.SalesReportMgr;

import java.time.LocalDate;
import java.util.Scanner;

public final class SalesReportUI extends UserInterface{
    private static SalesReportUI INSTANCE;


    private SalesReportUI() {
        super();
    }

    public static SalesReportUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SalesReportUI();
        }

        return INSTANCE;
    }

    private void displayOptions(){
        System.out.println("=========SalesReport Manager==========");
        System.out.println("(0) Go Back to Main Page");
        System.out.println("(1) Total Revenue only");
        System.out.println("(2) Revenue For each MenuItem only");
        System.out.println("(3) Both");
        System.out.println("======================================");
    }

    

    public void printrevenue(){
        int option;
        LocalDate startDate;
        LocalDate endDate;
        do{
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");
            startDate = super.getInputDate("Enter start date (YYYY:MM:DD)");
            endDate = super.getInputDate("Enter end date (YYYY:MM:DD)");
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
            

        }while(option!=0); 
        
    }
    
    public void createreport(){
        if(super.getYNOption("Are you sure it is end of the day? (The time will be automatically skipped to tomorrow 0.00 AM) [Y],[N]"))
            SalesReportMgr.getInstance().createReport(CurrentTime.currentTime.toLocalDate() );
    }

}
