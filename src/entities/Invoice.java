package entities;

import java.util.ArrayList;


public class Invoice {
    private InvoiceId;
    private Order order;
    private ArrayList<PriceFilters> pricefilters;
    private double finalPrice

    private void calculateFinalPrice( boolean print){
        this.finalPrice = order.getTotalPrice();
        double rawPrice = finalPrice;
        double addPrice;
        PriceFilter filter;
        for(int i =0; i<pricefilters.size();i++){
            filter = pricefilters.get(i);
            addPrice = filter.execute(rawPrice);
            if(print) System.out.println(filter.getDescription() + ": " + addPrice);
            finalPrice += addPrice
        }
    }

    public Invoice(Order order) {
        this.order = order;
        this.finalPrice = order.getTotalPrice();
    }
    /*
    //setter for order, DO WE ALLOW THIS???, i dun think we should
    public void setOrder(Order order){

    }*/

    public Order getOrder(){
        return order;
    }

    public ArrayList<PriceFilters> getPriceFilters(){
        return priceFilters;
    }

    public void addPriceFilters(PriceFilter filter){
        pricefilters.add(filter);
    }


    public void getFinalPrice(){
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
        System.out.println("Server: "+);
        System.out.println("Table: "+ order.getTableID() );
        System.out.println("Date: "+ order.getDate().toLocalDate().toString() );
        System.out.println("Time: "+ order.getDate().toLocalTime().toString() );

        System.out.println("................................");
        System.out.println();

        TreeMap<OrderItem, int> items = order.getorderedItems();
        for (OrderItem item : items.keySet()){
            int quantity = items.get(item);
            // need somethings to know is it a package order
            System.out.println(quantity + " " + item.getName() + " " + item.getPrice()*quantity);
        }

        System.out.println("................................");
        System.out.println("         SUB-TOTAL:   " +  order.getTotalPrice());
        /*
        for(int i =0 ; i<pricefilters.size();i++)
            System.out.println("         " +pricefilters.getDescription()+ " :  "+ price )*/
        // TODO Pricefilter.getName

        calculateFinalPrice(true);
        System.out.println("--------------------------------");
        System.out.println("        TOTAL : "  + finalPrice);
        System.out.println("================================");

        System.out.println(" PROMOTIONAL STUFS")

        System.out.println(" InvoiceNumber : " + InvoiceId);
        
    }
    
}
