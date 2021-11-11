package global;

import java.util.HashMap;

import entities.DiscountFilter;
import entities.TaxFilter;
import enums.DiscountFilterNameEnum;
import enums.PriceFilterTypeEnum;
import enums.TaxFilterNameEnum;


public class AvailPriceFilter {
    public static HashMap<DiscountFilterNameEnum, DiscountFilter> discountFilter = new HashMap<DiscountFilterNameEnum, DiscountFilter>();
    public static HashMap<TaxFilterNameEnum, TaxFilter> taxFilter = new HashMap<TaxFilterNameEnum, TaxFilter>();

    public static void createDiscountFilters() {
        DiscountFilter goldMembershipDiscount = new DiscountFilter(PriceFilterTypeEnum.PERCENTAGE, DiscountFilterNameEnum.GOLD_MEMBERSHIP, 30);
        DiscountFilter silverMembershipDiscount = new DiscountFilter(PriceFilterTypeEnum.PERCENTAGE, DiscountFilterNameEnum.SILVER_MEMBERSHIP, 20);
        DiscountFilter bronzeMembershipDiscount = new DiscountFilter(PriceFilterTypeEnum.PERCENTAGE, DiscountFilterNameEnum.BRONZE_MEMBERSHIP, 10);
        DiscountFilter noMembershipDiscount = new DiscountFilter(PriceFilterTypeEnum.PERCENTAGE, DiscountFilterNameEnum.NO_MEMBERSHIP, 0);

        discountFilter.put(DiscountFilterNameEnum.GOLD_MEMBERSHIP, goldMembershipDiscount);
        discountFilter.put(DiscountFilterNameEnum.SILVER_MEMBERSHIP, silverMembershipDiscount);
        discountFilter.put(DiscountFilterNameEnum.BRONZE_MEMBERSHIP, bronzeMembershipDiscount);
        discountFilter.put(DiscountFilterNameEnum.NO_MEMBERSHIP, noMembershipDiscount);
    }

    public static void createTaxFilters() {
        TaxFilter gstTax = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.GST, 7);
        TaxFilter serviceTax = new TaxFilter(PriceFilterTypeEnum.PERCENTAGE, TaxFilterNameEnum.GST, 10);

        taxFilter.put(TaxFilterNameEnum.GST, gstTax);
        taxFilter.put(TaxFilterNameEnum.SERVICE_CHARGE, serviceTax);
    }

    public static void createPriceFilters() {
        AvailPriceFilter.createDiscountFilters();
        AvailPriceFilter.createTaxFilters();
    }
}
