package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.decorator.TechnicalBuilder;
import com.kebuu.builder.impl.mobilemean.SimplifiedExponentialMobileMeanBuilder;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
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
    private static final int DEFAULT_SIGNAL_PERIOD = 9;

    @Getter private final int shortPeriod;
    @Getter private final int longPeriod;
    @Getter private final int signalPeriod;
    @Getter private final RealCotationAttribute attribute;

    private final AbstractSingleAttributeBuilder<Double> shortSemmBuilder;
    private final AbstractSingleAttributeBuilder<Double> longSemmBuilder;

    public MacdBuilder(int shortPeriod, int longPeriod, int signalPeriod) {
        Preconditions.checkArgument(longPeriod >= shortPeriod, "Long period should be greater or equals than short period");

        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        this.attribute = new RealCotationAttribute(MACD_PREFIX_NAME + shortPeriod + "_" + longPeriod);
        this.shortSemmBuilder = TechnicalBuilder.of(new SimplifiedExponentialMobileMeanBuilder(shortPeriod));
        this.longSemmBuilder = TechnicalBuilder.of(new SimplifiedExponentialMobileMeanBuilder(longPeriod));
    }

    public MacdBuilder() {
        this(DEFAULT_SHORT_PERIOD, DEFAULT_LONG_PERIOD, DEFAULT_SIGNAL_PERIOD);
    }

    @Override
    public CotationAttributes attributes() {
        return new CotationAttributes(attribute);
    }

    @Override
    public BuiltCotation build(CotationBuilderInfo cotationBuilderInfo) {
        BuiltCotation builtCotation = new BuiltCotation(cotationBuilderInfo.getCotation());

        CotationValue<Double> shortValue  = shortSemmBuilder.calculateSingleValue(cotationBuilderInfo);
        CotationValue<Double> longValue = longSemmBuilder.calculateSingleValue(cotationBuilderInfo);

        if (longValue.getValue().isPresent()) {
            SimpleCotationValue<Double> macdCotationValue = new SimpleCotationValue<>(attribute, shortValue.forceGetValue() - longValue.forceGetValue());
            builtCotation = builtCotation.withAdditionalValues(shortValue, longValue, macdCotationValue);
        }

        return builtCotation;
    }
}
