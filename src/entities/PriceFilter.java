package entities;

import enums.PriceFilterTypeEnum;
import enums.DiscountFilterNameEnum;

public interface PriceFilter{
    public double execute(double rawPrice);
    public String getDescription();
    
}