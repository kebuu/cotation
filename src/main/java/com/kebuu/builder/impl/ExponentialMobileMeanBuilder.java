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

import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calcul de l'EMM : EMM(p) = (Dernier cours - (MME(p) de la veille))*K + (MME(p) de la veille) et K = 2/(p+1)
 */
public class ExponentialMobileMeanBuilder extends AbstractBuilder {

    public static final String EMM_PREFIX_NAME = "emm_";

    @Getter private final int period;
    @Getter private final RealCotationAttribute emmValueAttribute;
    private final double exponentialFactor;

    public ExponentialMobileMeanBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;
        this.exponentialFactor = 2.0 / ((double)period + 1.0);

        this.emmValueAttribute = new RealCotationAttribute(EMM_PREFIX_NAME + period);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(emmValueAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> emmValue = new SimpleCotationValue<>(emmValueAttribute);

        Optional<Cotation> stochasticStartCotation = cotations.getByIndex(cotation.getPosition() - period);

        if (stochasticStartCotation.isPresent()) {
            DoubleSummaryStatistics doubleSummaryStatistics = IntStream.range(0, period)
               .mapToObj(index -> cotations.getByIndex(cotation.getPosition() - index).get())
               .collect(Collectors.summarizingDouble(Cotation::getEnd));

            double max = doubleSummaryStatistics.getMax();
            double min = doubleSummaryStatistics.getMin();

            if (max != min) {
                double value = calculateEmmValue(cotation.getEnd(), max, min);
                emmValue = emmValue.withValue(value);
            }
        }

        return new BuiltCotation(cotation).withAdditionalValues(emmValue);
    }

    private double calculateEmmValue(double current, double max, double min) {
        return 100.0 * (current - min) / (max - min);
    }
}
