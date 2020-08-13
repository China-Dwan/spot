package com.business.factorystrategy.strategy;

import com.business.factorystrategy.strategytype.DisCount;

public class Context {
    private DisCount strategy;

    public Double getPayPrice(Double originPrice, String VIPType) {
        strategy = StrategyFactory.getInstance().creator(VIPType);
        return strategy.disCount(originPrice);
    }

    public DisCount getStrategy() {
        return strategy;
    }

    public void setStrategy(DisCount strategy) {
        this.strategy = strategy;
    }
}
