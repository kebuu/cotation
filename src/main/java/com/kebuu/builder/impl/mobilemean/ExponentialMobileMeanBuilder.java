package com.kebuu.builder.impl.mobilemean;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import lombok.Getter;

public class ExponentialMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String EMM_PREFIX_NAME = "emm";

    @Getter protected final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        this(period, EMM_PREFIX_NAME);
    }

    public ExponentialMobileMeanBuilder(int period, String attributeBaseName) {
        super(period, attributeBaseName);
        this.exponentialFactor = calculateExponentialFactor(period);
    }

    public ExponentialMobileMeanBuilder(int period, String attributeBaseName, CotationAttribute<Double> attributeToAverage) {
        super(period, attributeBaseName, attributeToAverage);
        this.exponentialFactor = calculateExponentialFactor(period);
    }

    @Override
    protected double getWeight(int i) {
        return Math.pow(exponentialFactor, Integer.valueOf(i + 1).doubleValue());
    }

    private double calculateExponentialFactor(int period) {
        return 2.0 / (Integer.valueOf(period).doubleValue() + 1.0);
    }
}
