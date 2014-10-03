package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.kebuu.builder.impl.mobilemean.SimplifiedExponentialMobileMeanBuilder;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.List;

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
    //@Getter private final int signalPeriod;
    @Getter private final RealCotationAttribute macdValueAttribute;

    private final SimplifiedExponentialMobileMeanBuilder shortSemmBuilder;
    private final SimplifiedExponentialMobileMeanBuilder longSemmBuilder;
    //private final SimplifiedExponentialMobileMeanBuilder signalSemmBuilder;

    public MacdBuilder(int shortPeriod, int longPeriod/*, int signalPeriod*/) {
        Preconditions.checkArgument(longPeriod >= shortPeriod, "Long period should be greater or equals than short period");

        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        //this.signalPeriod = signalPeriod;
        this.macdValueAttribute = new RealCotationAttribute(MACD_PREFIX_NAME + shortPeriod + "_" + longPeriod);
        this.shortSemmBuilder = new SimplifiedExponentialMobileMeanBuilder(shortPeriod);
        this.longSemmBuilder = new SimplifiedExponentialMobileMeanBuilder(longPeriod);
        //this.signalSemmBuilder = new SimplifiedExponentialMobileMeanBuilder(signalPeriod);
    }

    public MacdBuilder() {
        this(DEFAULT_SHORT_PERIOD, DEFAULT_LONG_PERIOD/*, DEFAULT_SIGNAL_PERIOD*/);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(macdValueAttribute);
    }

    @Override
    public BuiltCotation build(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> macdCotationValue = new SimpleCotationValue<>(macdValueAttribute);

        CotationValue<Double> shortValue  = shortSemmBuilder.calculateSingleValue(cotationBuilderInfo);
        CotationValue<Double> longValue = longSemmBuilder.calculateSingleValue(cotationBuilderInfo);
        //CotationValue<Double> signalValue = signalSemmBuilder.calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations);

        List<CotationValue> cotationValues = Lists.newArrayList();
        if (longValue.getValue().isPresent()) {
            macdCotationValue = macdCotationValue.withValue(shortValue.forceGetValue() - longValue.forceGetValue());

            cotationValues.addAll(Lists.newArrayList(macdCotationValue, shortValue, longValue));
        }

//        if (signalValue.hasValue()) {
//            cotationValues.add(signalValue);
//        }

        return new BuiltCotation(cotationBuilderInfo.getCotation()).withAdditionalValues(cotationValues);
    }
}
