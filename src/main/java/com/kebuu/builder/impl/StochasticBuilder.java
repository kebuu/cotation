package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Calcul du stochastic : %K = 100 * (end - plusBas) / (plusHaut - plusBas)
 */
public class StochasticBuilder extends AbstractBuilder {

    public static final String STOCHASTIC_PREFIX_NAME = "stochastic_";
    public static final int DEFAULT_PERIOD = 14;
    public static final int DEFAULT_SIGNAL_PERIOD = 2;

    @Getter private final int period;
    @Getter private final int signalPeriod;
    @Getter private final RealCotationAttribute stochasticValueAttribute;

    private final SimpleMobileMeanBuilder signalBuilder;

    public StochasticBuilder(int period, int signalPeriod) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;
        this.signalPeriod = signalPeriod;

        this.stochasticValueAttribute = new RealCotationAttribute(STOCHASTIC_PREFIX_NAME + period);
        this.signalBuilder = new SimpleMobileMeanBuilder(signalPeriod, stochasticValueAttribute);
    }

    public StochasticBuilder(int period) {
        this(period, DEFAULT_SIGNAL_PERIOD);
    }
    public StochasticBuilder() {
        this(DEFAULT_PERIOD, DEFAULT_SIGNAL_PERIOD);
    }

    @Override
    public CotationAttributes attributes() {
        return new CotationAttributes(stochasticValueAttribute);
    }

    @Override
    public BuiltCotation build(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> stochasticValue = new SimpleCotationValue<>(stochasticValueAttribute);

        Cotation cotation = cotationBuilderInfo.getCotation();
        Cotations cotations = cotationBuilderInfo.getCotations();
        BuiltCotation builtCotation = new BuiltCotation(cotation);

        Optional<Cotation> stochasticStartCotation = cotations.getByIndex(cotation.getPosition() - period + 1);

        if (stochasticStartCotation.isPresent()) {
            DoubleSummaryStatistics doubleSummaryStatistics = IntStream.range(0, period)
               .mapToObj(index -> cotations.getByIndex(cotation.getPosition() - index).get())
               .flatMapToDouble(x -> DoubleStream.of(x.getMax(), x.getMin()))
               .summaryStatistics();

            double max = doubleSummaryStatistics.getMax();
            double min = doubleSummaryStatistics.getMin();

            if (max != min) {
                double value = calculateStochasticValue(cotation.getEnd(), max, min);
                stochasticValue = stochasticValue.withValue(value);
            }

            builtCotation = builtCotation.withAdditionalValues(stochasticValue);
            builtCotation = builtCotation.withAdditionalValues(signalBuilder.calculateSingleValue(cotationBuilderInfo.withBuiltCotation(builtCotation)));
        }

        return builtCotation;
    }

    private double calculateStochasticValue(double current, double max, double min) {
        return 100.0 * (current - min) / (max - min);
    }
}
