package entities;

import enums.TaxFilterNameEnum;

import java.io.Serializable;

import enums.PriceFilterTypeEnum;

public class TaxFilter implements PriceFilter, Serializable{
    private double amount;
    private PriceFilterTypeEnum type;
    private TaxFilterNameEnum name;
    
    public TaxFilter(){}
    
    public TaxFilter(PriceFilterTypeEnum type, TaxFilterNameEnum name, double amount){
        this.amount = amount;
        this.type = type;
        this.name = name;
    }

    @Override
    public String getDescription(){
        String description = "";
        switch(this.name){
            case SERVICE_CHARGE: 
                description += " Service Charge ";
                break;
            case GST:
                description += " GST ";
                break;

        }

        switch(type){
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
        switch(this.type){
            case ABSOLUTE:
                return rawPrice - this.amount;
            case PERCENTAGE: 
                return rawPrice*amount/100;
            default:
                return 0;
        }
    }
}