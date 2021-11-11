package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;


public class Invoice implements Serializable {
    private int invoiceId;
    private Order order;
    private ArrayList<PriceFilter> priceFilters;
    private double finalPrice;

    private void calculateFinalPrice( boolean print){
        this.finalPrice = order.getTotalPrice();
        double rawPrice = finalPrice;
        double addPrice;
        PriceFilter filter;
        for(int i =0; i< this.priceFilters.size();i++){
            filter = this.priceFilters.get(i);
            addPrice = filter.execute(rawPrice);
            if(print) System.out.println(filter.getDescription() + ": " + addPrice);
            finalPrice += addPrice;
        }
    }
    public Invoice(){}

    public Invoice(Order order, int invoiceId) {
        this.order = order;
        this.finalPrice = order.getTotalPrice();
        this.invoiceId = invoiceId;
        this.priceFilters = new ArrayList<PriceFilter>();
    }
    

    public int getId(){
        return this.invoiceId;
    }

    public Order getOrder(){
        return order;
    }

    public ArrayList<PriceFilter> getPriceFilters(){
        return this.priceFilters;
    }

    public void addPriceFilters(PriceFilter filter){
        priceFilters.add(filter);
    }


    public double getFinalPrice(){
        this.calculateFinalPrice(false);
        return finalPrice;
    }

    public void printInvoice(){
        // TODO: Formatting, Restaurant Name, Address, TEl, Server, 
        System.out.println(" Our Restaurant Name ");
        System.out.println(" Our Address Line 1 ");
        System.out.println(" Our Address Line 2 ");
        System.out.println("Tel: XXX-XXX-XXXX ");
        System.out.println();
        System.out.println("Server      : Staffid "+ order.getServeby().getId());
        System.out.println("Table No    : "+ order.getTable().getId() ); // TO ADD Table
        System.out.println("Date        : "+ order.getDate().toLocalDate().toString() );
        System.out.println("Time        : "+ order.getDate().toLocalTime().toString() );

        System.out.println(".........................................................");
        System.out.println();

        TreeMap<MenuItem, Integer> items = order.getOrderedItems();
        for (MenuItem item : items.keySet()){
            int quantity = items.get(item);
            // need somethings to know is it a package order
            System.out.printf("-3%d  %20 %.2f\n",quantity, item.getName(), item.getPrice()*quantity);
        }

        System.out.println(".......................................................");
        System.out.println("                         SUB-TOTAL:   " +  order.getTotalPrice());
        

        calculateFinalPrice(true);
        System.out.println("------------------------------------------------------");
        System.out.println("                                  TOTAL : "  + finalPrice);
        System.out.println("=====================================================");

        System.out.println(" PROMOTIONAL STUFS");

        System.out.println(" InvoiceNumber : " + this.invoiceId);
        
    }
    
}
