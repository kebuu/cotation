package com.kebuu.builder.impl.mobilemean;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.value.SimpleCotationValue;

import java.util.Optional;

public class SimplifiedExponentialMobileMeanBuilder extends ExponentialMobileMeanBuilder {

    private static final String SIMPLE_EMM_PREFIX_NAME = "simple_emm";

    public SimplifiedExponentialMobileMeanBuilder(int period) {
        super(period, SIMPLE_EMM_PREFIX_NAME);
    }

    @Override
    public SimpleCotationValue<Double> calculateSingleValue(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> value = new SimpleCotationValue<>(attribute);

        Optional<Double> previousValue = builtCotations.getValue(cotation.getPosition() - 1, attribute);

        if (previousValue.isPresent()) {
            value = value.withValue(calculateSimplifiedValue(getValueToAverage(cotation, cotations, builtCotations, alreadyBuiltCotations).get(), previousValue.get(), exponentialFactor));
        } else {
            SimpleCotationValue<Double> cotationValue = super.calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations);

            if (cotationValue.hasValue()) {
                value = value.withValue(cotationValue.unwrapValue());
            }
        }

        return value;
    }

    private double calculateSimplifiedValue(Double underlyingAttribute, Double previousValue, double exponentialFactor) {
        return underlyingAttribute * exponentialFactor + previousValue * (1.0 - exponentialFactor);
    }
}
