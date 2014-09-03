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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calcul de l'EMM : EMM(p) = TODO
 */
public class ExponentialMobileMeanBuilder extends AbstractBuilder {

    private static final String EMM_PREFIX_NAME = "emm_";

    @Getter private final int period;
    @Getter private final RealCotationAttribute attribute;
    private final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;
        this.exponentialFactor = 2.0 / ((double)period + 1.0);

        this.attribute = new RealCotationAttribute(EMM_PREFIX_NAME + period);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(attribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> emmCotationValue = new SimpleCotationValue<>(attribute);

        if (cotations.getByIndex(cotation.getPosition() - period).isPresent()) {
            List<Cotation> usedCotations = IntStream.range(0, period)
                .mapToObj(i -> cotations.getByIndex(cotation.getPosition() - i).get())
                .sorted(Comparator.comparingInt(Cotation::getPosition).reversed())
                .collect(Collectors.toList());

            emmCotationValue = emmCotationValue.withValue(calculateEmmValue(usedCotations, exponentialFactor));
        }

        return new BuiltCotation(cotation).withAdditionalValues(emmCotationValue);
    }

    private double calculateEmmValue(List<Cotation> usedCotations, double exponentialFactor) {
        double emmNumeratorValue = 0.0;
        double factorSum = 0.0;

        for (int i = 0; i < usedCotations.size(); i++) {
            double factor = Math.pow(exponentialFactor, (double)i);
            emmNumeratorValue += usedCotations.get(i).getEnd() * factor;
            factorSum += factor;
        }

        return emmNumeratorValue / factorSum;
    }
}
