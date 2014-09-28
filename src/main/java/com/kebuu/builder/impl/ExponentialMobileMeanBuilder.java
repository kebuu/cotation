package com.kebuu.builder.impl;

public class ExponentialMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String EMM_PREFIX_NAME = "emm_";
    private final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        super(period, EMM_PREFIX_NAME);
        this.exponentialFactor = 2.0 / (Integer.valueOf(period).doubleValue() + 1.0);
    }

    @Override
    protected double getWeight(int i) {
        return Math.exp(Integer.valueOf(i + 1).doubleValue() * Math.log(exponentialFactor));
    }
}
