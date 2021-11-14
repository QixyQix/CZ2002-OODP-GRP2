package entities;

import enums.PriceFilterTypeEnum;
import enums.DiscountFilterNameEnum;

import java.io.Serializable;
/***
 * Represents a Discount Filter entity
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class DiscountFilter implements PriceFilter, Serializable{
    /**
     * (Absolute/Percentage) Amount of this Discount Filter
     */
    private double amount;
    /**
     * Applied Type (by absolute/by Percentage) of this Discount Filter
     */
    private PriceFilterTypeEnum type;
    /**
     * Name of this Discount Filter
     */
    private DiscountFilterNameEnum name;
    
    /**
     * Constructor
     * @param type      Applying Type (by absolute/by Percentage) of the Discount Filter
     * @param name      Name of the Discount Filter
     * @param amount    Amount of the Discount (absolute/Percentage)
     */
    public DiscountFilter(PriceFilterTypeEnum type, DiscountFilterNameEnum name, double amount){
        this.amount = amount;
        this.type = type;
        this.name = name;
    }

    /**
     * Returns description
     * 
     * @return description
     */
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


    /**
     * Returns discounted price
     * 
     * @return discounted price
     */
    @Override 
    public double execute(double rawPrice){
        switch(type){
            case ABSOLUTE:
                return -amount;
            case PERCENTAGE: 
                return -rawPrice*amount/100;
            default:
                return 0;
        }
    }


}