package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

/***
 * Represents a report entity
 * 
 * @author Lim Yan Kai
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
public class Report implements Serializable, Entities{
    /**
     * List of invoices that contained in this report of the day
     */
    private ArrayList<Invoice> invoiceList;
    /**
     * the totalrevenue of this report
     */
    private double totalRevenue;
    /**
     * The date of this report
     */
    private LocalDate date;
    /**
     * The List MenuItem and its total Revenum of this report
     */
    private TreeMap<MenuItem, Double> menuItemRevenue;
    /**
     * The id of this report
     */
    private int id;

    /**
     * Constructor  
     */
    public Report(){}

    /**
     * Constructor
     * @param date The date of the report
     */
    public Report(LocalDate date){
        this.date = date;
        this.menuItemRevenue = new TreeMap<MenuItem, Double> ();
        this.invoiceList = new ArrayList<Invoice> ();
    }

    /**
     * Add to menu item revenue
     * 
     * @param orderedItems ordered items
     */
    private void addtomenuItemRevenue(TreeMap<MenuItem,Integer> orderedItems){
        for(MenuItem item : orderedItems.keySet() ){
            
            double val = 0;
            if( this.menuItemRevenue.containsKey(item)) {
                val = menuItemRevenue.get(item);
            }
                
            val += orderedItems.get(item) * item.getPrice();

            
            
            this.menuItemRevenue.put(item,val);
        }
    }

    /**
     * Calculates total revenue
     * 
     */
    public void calculateTotalRevenue(){
        if(!validator()){
            System.out.println("There are Invoice that not belong to the date, probably insert wrongly. Please Check");
            return;
        }
        this.totalRevenue =0;
        this.menuItemRevenue.clear();
        for (int i =0; i< this.invoiceList.size();i++){
            this.totalRevenue += this.invoiceList.get(i).getFinalPrice();
            this.addtomenuItemRevenue(this.invoiceList.get(i).getOrder().getOrderedItems());
        }
    }

    /**
     * Validator that returns true or false
     * 
     * @return true or false
     */
    private boolean validator(){

        for(int i =0; i<this.invoiceList.size();i++){
            if (! this.invoiceList.get(i).getOrder().getDate().toLocalDate().isEqual(date))
                return false;            
        }
        return true;
    }

    /**
     * Returns invoice list
     * 
     * @return invoice ArrayList
     */
    public ArrayList<Invoice> getInvoiceList(){
        return this.invoiceList;
    }

    /**
     * Adds invoice list
     * 
     * @param invoice invoice object
     */
    public void addInvoiceList(Invoice invoice){
        this.invoiceList.add(invoice);
    }

    /**
     * Returns total revenue
     * 
     * @return total revenue
     */
    public double getTotalRevenue(){
        return this.totalRevenue;
    }

    /**
     * Returns date
     * 
     * @return date
     */
    public LocalDate getDate(){
        return this.date;
    }

    /**
     * Returns menu item revenue
     * 
     * @return Treemap of menu item revenue
     */
    public TreeMap<MenuItem, Double> getMenuItemRevenue() {
        return this.menuItemRevenue;
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId(){
        return this.id;
    }

    

}

    

