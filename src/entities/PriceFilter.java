package entities;

import enums.PriceFilterEnum;

interface PriceFilter{
    public void execute();
    public void getDescription();
    
}

Class TaxFilter implements PriceFilter{
    private double amount;
    private PriceFilterTypeEnum type;
    private TaxFilterNameEnum name;
    

    public TaxFilter(PriceFilterTypeEnum type, TaxFilterNameEnum name, double amount){
        this.amount = amount;
        this.type = type;
        this.name = name;
    }

    @Override
    public String getDescription(){
        String description;
        switch(name){

            case SERVICE_CHARGE: 
                description += " Service Charge ";
                break;
            case GST:
                description += " GST ";
                break;

        }

        switch(type){
            PERCENTAGE: description += " ( "+ amount + "% )"; break;
            ADSOLUTE:  description += " ( "+ amount + "sgd )"; break;
        }
        return description;
    }


    @Override 
    public double execute(double rawPrice){
        switch(type){
            case ABSOLUTE:
                return amount;
                break;
            case PERCENTAGE: 
                return rawPrice*amount/100;
                break;
        }
    }
}



Class DiscountFilter implements PriceFilter{
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
        String description;
        switch(name){

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

        switch(type){
            PERCENTAGE: description += " ( "+ amount + "% )"; break;
            ADSOLUTE:  description += " ( "+ amount + "sgd )"; break;
        }
        return description;
    }


    @Override 
    public double execute(double rawPrice){
        switch(type){
            case ABSOLUTE:
                return amount;
                break;
            case PERCENTAGE: 
                return rawPrice*amount/100;
                break;
        }
    }


}