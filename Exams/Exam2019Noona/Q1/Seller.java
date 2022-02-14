package Q1;

import java.util.HashMap;

public class Seller {
    private PricingPolicy pricesDict;
    public Seller(PricingPolicy pricesDict){
        this.pricesDict = pricesDict;
    }
    double priceOfProduct(Product prod){
        return pricesDict.price(prod);
    }
}
