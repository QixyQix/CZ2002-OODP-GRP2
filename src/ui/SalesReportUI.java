package ui;

import entities.Invoice;
import entities.Report;
import managers.InvoiceMgr;
import managers.SalesReportMgr;
import java.util.Scanner;

public final class SalesReportUI extends UserInterface{
    private static SalesReportUI INSTANCE;
    private Scanner sc;

    private SalesReportUI() {
        super();
        this.sc = new Scanner(System.in);
    }

    public static SalesReportUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SalesReportUI();
        }

        return INSTANCE;
    }

    public void displayOptions(){

    }
}
