package com.business.factorystrategy.strategytype;

import org.springframework.stereotype.Component;

@Component
public class NOVIP implements DisCount {
    @Override
    public Double disCount(Double originPrice) {
        return originPrice;
    }
}
