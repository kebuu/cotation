package com.kebuu.builder.impl.mobilemean;

import lombok.Getter;

public class ExponentialMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String EMM_PREFIX_NAME = "emm";
    @Getter private final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        super(period, EMM_PREFIX_NAME);
        this.exponentialFactor = 2.0 / (Integer.valueOf(period).doubleValue() + 1.0);
    }

    @Override
    protected double getWeight(int i) {
        return Math.pow(exponentialFactor, Integer.valueOf(i + 1).doubleValue());
    }
}
