package ui;

import java.util.Scanner;

import entities.Invoice;
import managers.InvoiceMgr;


public class InvoiceUI extends UserInterface{
    private static InvoiceUI INSTANCE;
    private Scanner sc;

    private InvoiceUI() {
        super();
        this.sc = new Scanner(System.in);
    }

    public static InvoiceUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceUI();
        }

        return INSTANCE;
    }

    public void displayOptions(){

    }
}
