package Q1;

public interface PricingPolicy {
    double price(Valuable c);
    static PricingPolicy competitivePolicy(PricingPolicy[] policies){
        return (product)->{
            double min_value = -1;
            for (PricingPolicy policy : policies){
                if (min_value < policy.price(product)){
                    min_value = policy.price(product);
                }
            }
            return min_value-1;
        };
    }
}
