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
 * Calcul du stochastic : %K = 100 * (end - plusBas) / (plusHaut - plusBas)
 */
public class StochasticBuilder extends AbstractBuilder {

    private static final String STOCHASTIC_PREFIX_NAME = "stochastic_";
    private static final int DEFAULT_PERIOD = 14;

    @Getter private final int period;
    @Getter private final RealCotationAttribute stochasticValueAttribute;

    public StochasticBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;

        this.stochasticValueAttribute = new RealCotationAttribute(STOCHASTIC_PREFIX_NAME + period);
    }

    public StochasticBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(stochasticValueAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> stochasticValue = new SimpleCotationValue<>(stochasticValueAttribute);

        Optional<Cotation> stochasticStartCotation = cotations.getByIndex(cotation.getPosition() - period);

        if (stochasticStartCotation.isPresent()) {
            DoubleSummaryStatistics doubleSummaryStatistics = IntStream.rangeClosed(0, period)
               .mapToObj(index -> cotations.getByIndex(cotation.getPosition() - index).get())
               .collect(Collectors.summarizingDouble(Cotation::getEnd));

            double max = doubleSummaryStatistics.getMax();
            double min = doubleSummaryStatistics.getMin();

            if (max != min) {
                double value = calculateStochasticValue(cotation.getEnd(), max, min);
                stochasticValue = stochasticValue.withValue(value);
            }
        }

        return new BuiltCotation(cotation).withAdditionalValues(stochasticValue);
    }

    private double calculateStochasticValue(double current, double max, double min) {
        return 100.0 * (current - min) / (max - min);
    }
}
