package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;
public class Report implements Serializable, Entities{

    private ArrayList<Invoice> invoiceList;
    private double totalRevenue;
    private LocalDate date;
    private TreeMap<MenuItem, Double> menuItemRevenue;
    private int id;
    public Report(){}

    public Report(LocalDate date){
        this.date = date;
        this.menuItemRevenue = new TreeMap<MenuItem, Double> ();
        this.invoiceList = new ArrayList<Invoice> ();
    }

    private void addtomenuItemRevenue(TreeMap<MenuItem,Integer> orderedItems){
        for(MenuItem item : orderedItems.keySet() ){
            double val = 0;
            if( this.menuItemRevenue.containsKey(item)) val = menuItemRevenue.get(item);
            val += orderedItems.get(item) * item.getPrice();
            
            // not sure will overwrite or not
            this.menuItemRevenue.put(item,val);
        }
    }

    private void calculateTotalRevenue(){
        if(!validator()){
            System.out.println("There are Invoice that not belong to the date, probably insert wrongly. Please Check");
            return;
        }
        this.totalRevenue =0;
        for (int i =0; i< this.invoiceList.size();i++){
            this.totalRevenue += this.invoiceList.get(i).getFinalPrice();
            addtomenuItemRevenue(this.invoiceList.get(i).getOrder().getOrderedItems());
        }
    }

    private boolean validator(){

        // TODO, to be able to fine out which invoicenumber placed wrongly ..
        for(int i =0; i<this.invoiceList.size();i++){
            if ( this.invoiceList.get(i).getOrder().getDate().toLocalDate()  != date)
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

    public TreeMap<MenuItem, Double> getMenuItemRevenue() {
        return menuItemRevenue;
    }

    public int getId(){
        return this.id;
    }

    

}

    

