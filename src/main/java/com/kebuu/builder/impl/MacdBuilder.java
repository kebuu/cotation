package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

/**
 * Calcul de l'EMM : EMM(p) = TODO
 */
public class MacdBuilder extends AbstractBuilder {

    private static final String MACD_PREFIX_NAME = "macd_";
    private static final int DEFAULT_SHORT_PERIOD = 12;
    private static final int DEFAULT_LONG_PERIOD = 26;

    @Getter private final int shortPeriod;
    @Getter private final int longPeriod;
    @Getter private final RealCotationAttribute macdValueAttribute;

    private final ExponentialMobileMeanBuilder shortPeriodBuilder;
    private final ExponentialMobileMeanBuilder longPeriodBuilder;

    public MacdBuilder(int shortPeriod, int longPeriod) {
        Preconditions.checkArgument(shortPeriod > 0, "Short period should be greater than 0");
        Preconditions.checkArgument(longPeriod > shortPeriod, "Long period should be greater than short period");

        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.macdValueAttribute = new RealCotationAttribute(MACD_PREFIX_NAME + shortPeriod + "_" + longPeriod);
        this.shortPeriodBuilder = new ExponentialMobileMeanBuilder(shortPeriod);
        this.longPeriodBuilder = new ExponentialMobileMeanBuilder(longPeriod);
    }

    public MacdBuilder() {
        this(DEFAULT_SHORT_PERIOD, DEFAULT_LONG_PERIOD);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(macdValueAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> macdCotationValue = new SimpleCotationValue<>(macdValueAttribute);

        BuiltCotation shortPeriodBuiltValue = shortPeriodBuilder.build(cotation, cotations, builtCotations, alreadyBuiltCotations);
        BuiltCotation longPeriodBuiltValue = longPeriodBuilder.build(cotation, cotations, builtCotations, alreadyBuiltCotations);

        CotationValue<Double> shortPeriodValue = shortPeriodBuiltValue.getValueByAttribute(shortPeriodBuilder.getAttribute()).get();
        CotationValue<Double> longPeriodValue = longPeriodBuiltValue.getValueByAttribute(longPeriodBuilder.getAttribute()).get();

        if (longPeriodValue.getValue().isPresent()) {
            macdCotationValue = macdCotationValue.withValue(longPeriodValue.forceGetValue() - shortPeriodValue.forceGetValue());
        }

        return new BuiltCotation(cotation).withAdditionalValues(macdCotationValue);
    }
}
