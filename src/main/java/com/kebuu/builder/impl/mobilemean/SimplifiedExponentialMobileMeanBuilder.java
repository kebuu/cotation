package com.kebuu.builder.impl.mobilemean;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;

import java.util.Optional;

public class SimplifiedExponentialMobileMeanBuilder extends AbstractSingleAttributeBuilder<Double> {

    private static final String EMM_PREFIX_NAME = "simple_emm";
    private final ExponentialMobileMeanBuilder exponentialMobileMeanBuilder;
    private final RealCotationAttribute attribute;

    public SimplifiedExponentialMobileMeanBuilder(int period) {
        exponentialMobileMeanBuilder = new ExponentialMobileMeanBuilder(period);
        attribute = new RealCotationAttribute(EMM_PREFIX_NAME + "_" + period);
    }

    @Override
    public CotationAttribute<Double> getSingleAttribute() {
        return attribute;
    }

    @Override
    public CotationValue calculateSingleValue(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> value = new SimpleCotationValue<>(attribute);

        Optional<Double> previousValue = builtCotations.getValue(cotation.getPosition() - 1, attribute);

        if (previousValue.isPresent()) {
            double exponentialFactor = exponentialMobileMeanBuilder.getExponentialFactor();
            value = value.withValue(calculateSimplifiedValue(cotation, previousValue, exponentialFactor));
        } else {
            SimpleCotationValue<Double> simpleCotationValue = exponentialMobileMeanBuilder.calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations);

            if (simpleCotationValue.hasValue()) {
                value = value.withValue(simpleCotationValue.unwrapValue());
            }
        }

        return value;
    }

    private double calculateSimplifiedValue(Cotation cotation, Optional<Double> previousValue, double exponentialFactor) {
        return cotation.getEnd() * exponentialFactor + previousValue.get() * (1.0 - exponentialFactor);
    }
}
