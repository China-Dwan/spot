package springboottest.business.factorystrategy.strategytype;

import org.springframework.stereotype.Component;

@Component
public class BVIP implements DisCount {
    @Override
    public Double disCount(Double originPrice) {
        return 0.9*originPrice;
    }
}
