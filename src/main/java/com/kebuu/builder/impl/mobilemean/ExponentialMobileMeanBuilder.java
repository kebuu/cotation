package com.kebuu.builder.impl.mobilemean;

import lombok.Getter;

public class ExponentialMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String EMM_PREFIX_NAME = "emm";
    @Getter protected final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        this(period, EMM_PREFIX_NAME);
    }

    public ExponentialMobileMeanBuilder(int period, String attributeBaseName) {
        super(period, attributeBaseName);
        this.exponentialFactor = 2.0 / (Integer.valueOf(period).doubleValue() + 1.0);
    }

    @Override
    protected double getWeight(int i) {
        return Math.pow(exponentialFactor, Integer.valueOf(i + 1).doubleValue());
    }
}
