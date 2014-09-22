package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.Optional;

/**
 * Calcul de l'EMM : EMM(n) = end * C + (1 - C) * EMM(n - 1)
 * Avec C = 2 / (period + 1)
 */
public class ExponentialMobileMeanBuilder extends AbstractBuilder {

    private static final String EMM_PREFIX_NAME = "emm_";

    @Getter private final int period;
    @Getter private final RealCotationAttribute attribute;
    private final double exponentialFactor;
    private final MobileMeanBuilder simpleMobileMeanBuilder;

    public ExponentialMobileMeanBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;
        this.exponentialFactor = 2.0 / ((double)period + 1.0);

        this.attribute = new RealCotationAttribute(EMM_PREFIX_NAME + period);
        this.simpleMobileMeanBuilder = new MobileMeanBuilder(period);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(attribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> emmCotationValue = new SimpleCotationValue<>(attribute);

        Optional<Double> previousEmmValue = builtCotations.getValue(cotation.getPosition() - 1, attribute);
        if (previousEmmValue.isPresent()) {
            emmCotationValue = emmCotationValue.withValue(calculateEmmValue(cotation, previousEmmValue.get(), exponentialFactor));
        } else {
            BuiltCotation simpleMobileMeanBuiltCotation = simpleMobileMeanBuilder.build(cotation, cotations, builtCotations, alreadyBuiltCotations);
            Optional<Double> simpleMobileMeanValue = simpleMobileMeanBuiltCotation.getValueByAttribute(simpleMobileMeanBuilder.getMobileMeanValueAttribute());

            if (simpleMobileMeanValue.isPresent()) {
                emmCotationValue = emmCotationValue.withValue(simpleMobileMeanValue.get());
            }
        }

        return new BuiltCotation(cotation).withAdditionalValues(emmCotationValue);
    }

    private Double calculateEmmValue(Cotation cotation, Double previousEmmValue, double exponentialFactor) {
        return cotation.getEnd() * exponentialFactor + (1.0 - exponentialFactor) * previousEmmValue;

    }
}
