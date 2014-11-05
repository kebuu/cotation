package com.kebuu.builder.impl;

import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Calcul du Chaikin money flow
 * Step 1 ((Close – Low) – (High – Close)/ (High – Low)) * Volume
 * Step 2 21 Day Average of Step1 (Daily MF) / 21 Day Average of Volume
 */
public class ChaikinMoneyFlowBuilder extends SimpleMobileMeanBuilder {

    public static final String PREFIX_NAME = "chaikin";
    public static final int DEFAULT_PERIOD = 21;

    public ChaikinMoneyFlowBuilder(int period) {
        super(period, PREFIX_NAME, (CotationBuilderInfo cotationBuilderInfo) -> ChaikinMoneyFlowBuilder.extractValueToAverage(cotationBuilderInfo, period));
    }

    public ChaikinMoneyFlowBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public SimpleCotationValue<Double> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> mobileMeanValue = super.calculateSingleValue(cotationBuilderInfo);

        if (mobileMeanValue.hasValue() && mobileMeanValue.unwrapValue() != NumberUtils.DOUBLE_ZERO) {
            double volumeSum = IntStream.range(0, mobileMeanRange)
                .mapToDouble(index -> cotationBuilderInfo.getCotation(-index).get().getVolume())
                .sum();

            mobileMeanValue = mobileMeanValue.withValue(mobileMeanRange * mobileMeanValue.unwrapValue() / volumeSum);
        }

        return mobileMeanValue;
    }

    private static Optional<Double> extractValueToAverage(CotationBuilderInfo cotationBuilderInfo, int period) {
        Optional<Double> result = Optional.empty();

        if (cotationBuilderInfo.getCotation().hasVolume()) {
            double volume = cotationBuilderInfo.getCotation().getVolume();
            double endValue = cotationBuilderInfo.getCotation().getEnd();
            double lowestValue = cotationBuilderInfo.getCotation().getMin();
            double highestValue = cotationBuilderInfo.getCotation().getMax();

            double value;
            if (lowestValue == highestValue) {
                value = volume;
            } else {
                value = volume * (2.0 * endValue - lowestValue - highestValue) / (highestValue - lowestValue);
            }

            result = Optional.of(value);
        }

        return result;
    }
}
