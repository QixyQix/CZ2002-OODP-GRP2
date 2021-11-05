package entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class Report{

    private ArrayList<Invoice> invoiceList;
    private double totalRevenue;
    private LocalDate date;

    public Report(LocalDate date){
        this.date = date;
    }

    private void calculateTotalRevenue(){
        if(!validator()){
            System.out.println("There are Invoice that not belong to the date, probably insert wrongly. Please Check");
            return;
        }
        this.totalRevenue =0;
        for (int i =0; i< this.invoiceList.size();i++){
            this.totalRevenue += this.invoiceList.get(i).getFinalPrice();
        }
    }

    private boolean validator(){

        // TODO, to be able to fine out which invoicenumber placed wrongly ..
        for(int i =0; i<this.invoiceList.size();i++){
            if ( this.invoiceList.get(i).getDate().getOrder().getDate().toLocalDate()  != date)
                return false;            
        }
        return true;
    }

    public ArrayList<Invoice> getInvoiceList(){
        return this.invoiceList;
    }

    public void addInvoiceList(Invoice invoice){
        this.invoiceList.add(invoice);
    }

    public double getTotalRevenue(){
        calculateTotalRevenue();
        return this.totalRevenue;
    }

    public LocalDate getDate(){
        return this.date;
    }
}

    

