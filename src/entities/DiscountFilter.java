package entities;

import enums.PriceFilterTypeEnum;
import enums.DiscountFilterNameEnum;


public class DiscountFilter implements PriceFilter{
    private double amount;
    private PriceFilterTypeEnum type;
    private DiscountFilterNameEnum name;

    public DiscountFilter(PriceFilterTypeEnum type, DiscountFilterNameEnum name, double amount){
        this.amount = amount;
        this.type = type;
        this.name = name;
    }

    @Override
    public String getDescription(){
        String description = "";
        switch(this.name){

            case GOLD_MEMBERSHIP: 
                description += " Gold Membership ";
                break;
            case SILVER_MEMBERSHIP:
                description += " Silver Membership ";
                break;
            case BRONZE_MEMBERSHIP:
                description += " Bronze Membership ";
                break;
            case NO_MEMBERSHIP:
                description += " No Membership ";
                break;

        }

        switch(this.type){
            case PERCENTAGE: 
                description += " ( "+ amount + "% )"; 
                break;
            case ABSOLUTE:  
                description += " ( "+ amount + "sgd )"; 
                break;
        }
        return description;
    }


    @Override 
    public double execute(double rawPrice){
        switch(type){
            case ABSOLUTE:
                return amount;
            case PERCENTAGE: 
                return rawPrice*amount/100;
            default:
                return 0;
        }
    }


}