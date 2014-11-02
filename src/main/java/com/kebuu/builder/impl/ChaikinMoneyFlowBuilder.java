package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calcul du Chaikin money flow : (volume / sum(volume)) * ((end - lowest) – (highest - end)) / (highest - lowest)
 */
public class ChaikinMoneyFlowBuilder extends AbstractSingleAttributeBuilder<Double> {

    public static final String PREFIX_NAME = "chainkin_";
    public static final int DEFAULT_PERIOD = 21;

    @Getter private final int period;
    @Getter private final RealCotationAttribute attribute;

    public ChaikinMoneyFlowBuilder(int period) {
        Preconditions.checkArgument(period >= 0, "Period should be greater or equals to 0");

        this.period = period;
        this.attribute = new RealCotationAttribute(PREFIX_NAME + period);
    }

    public ChaikinMoneyFlowBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public CotationAttribute<Double> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<Double> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> cmfValue = new SimpleCotationValue<>(attribute);

        Optional<Cotation> startPeriodCotation = cotationBuilderInfo.getCotation(-period);

        if (startPeriodCotation.isPresent() && startPeriodCotation.get().getVolume() != null) {
            cmfValue = cmfValue.withValue(calculateValue(cotationBuilderInfo, period));
        }

        return cmfValue;
    }

    private double calculateValue(CotationBuilderInfo cotationBuilderInfo, int period) {
        double volume = cotationBuilderInfo.getCotation().getVolume();
        double endValue = cotationBuilderInfo.getCotation().getEnd();

        List<Cotation> usedCotations = IntStream.rangeClosed(0, period)
            .mapToObj(index -> cotationBuilderInfo.getCotation(-index).get())
            .collect(Collectors.toList());

        double volumeSum = usedCotations.stream().mapToDouble(Cotation::getVolume).sum();
        double lowestValue = usedCotations.stream().mapToDouble(Cotation::getMin).min().getAsDouble();
        double highestValue = usedCotations.stream().mapToDouble(Cotation::getMax).max().getAsDouble();

        double value;
        if (lowestValue == highestValue) {
            value = volume / volumeSum;
        } else {
            value = volume * (2.0 * endValue - lowestValue - highestValue) / ((highestValue - lowestValue) * volumeSum);
        }

        return value;
    }
}
