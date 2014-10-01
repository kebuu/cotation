package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.mobilemean.SimplifiedExponentialMobileMeanBuilder;
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
 * Calcul du MACD : il s'agit d'une difference entre deux moyennes mobiles exponentielles
 */
public class MacdBuilder extends AbstractBuilder {

    private static final String MACD_PREFIX_NAME = "macd_";
    private static final int DEFAULT_SHORT_PERIOD = 12;
    private static final int DEFAULT_LONG_PERIOD = 26;

    @Getter private final int shortPeriod;
    @Getter private final int longPeriod;
    @Getter private final RealCotationAttribute macdValueAttribute;

    private final SimplifiedExponentialMobileMeanBuilder shortSemmBuilder;
    private final SimplifiedExponentialMobileMeanBuilder longSemmBuilder;

    public MacdBuilder(int shortPeriod, int longPeriod) {
        Preconditions.checkArgument(longPeriod >= shortPeriod, "Long period should be greater or equals than short period");

        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.macdValueAttribute = new RealCotationAttribute(MACD_PREFIX_NAME + shortPeriod + "_" + longPeriod);
        this.shortSemmBuilder = new SimplifiedExponentialMobileMeanBuilder(shortPeriod);
        this.longSemmBuilder = new SimplifiedExponentialMobileMeanBuilder(longPeriod);
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

        CotationValue<Double> shortValue  = shortSemmBuilder.calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations);
        CotationValue<Double> longValue = longSemmBuilder.calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations);

        if (longValue.getValue().isPresent()) {
            macdCotationValue = macdCotationValue.withValue(shortValue.forceGetValue() - longValue.forceGetValue());
        }

        return new BuiltCotation(cotation).withAdditionalValues(macdCotationValue, shortValue, longValue);
    }
}
