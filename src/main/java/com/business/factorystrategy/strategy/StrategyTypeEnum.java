package com.business.factorystrategy.strategy;

public enum StrategyTypeEnum {
    /**
     * AVIP
     */
    AVIP("AVIP"),

    /**
     * BVIP
     */
    BVIP("BVIP"),

    /**
     * NPVIP
     */
    NOVIP("NOVIP");

    private String type;

    private StrategyTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

}
