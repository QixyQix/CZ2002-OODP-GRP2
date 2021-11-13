package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

/***
 * Represents a report entity
 * 
 * @author Lim Yan Kai
 * @author Zong Yu Lee
 */
public class Report implements Serializable, Entities{

    private ArrayList<Invoice> invoiceList;
    private double totalRevenue;
    private LocalDate date;
    private TreeMap<MenuItem, Double> menuItemRevenue;
    private int id;
    public Report(){}

    /**
     * Constructor
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
            if( this.menuItemRevenue.containsKey(item)) val = menuItemRevenue.get(item);
            val += orderedItems.get(item) * item.getPrice();
            
            // not sure will overwrite or not
            this.menuItemRevenue.put(item,val);
        }
    }

    /**
     * Calculates total revenue
     * 
     */
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

    /**
     * Validator that returns true or false
     * 
     * @return true or false
     */
    private boolean validator(){

        // TODO, to be able to fine out which invoicenumber placed wrongly ..
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
        calculateTotalRevenue();
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
        return menuItemRevenue;
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

    

