package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.mobilemean.ExponentialMobileMeanBuilder;
import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
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

    @Getter private final int mmPeriod;
    @Getter private final int emmPeriod;
    @Getter private final RealCotationAttribute macdValueAttribute;

    private final SimpleMobileMeanBuilder mmBuilder;
    private final ExponentialMobileMeanBuilder emmBuilder;

    public MacdBuilder(int mmPeriod, int emmPeriod) {
        Preconditions.checkArgument(emmPeriod >= mmPeriod, "Long period should be greater or equals than short period");

        this.mmPeriod = mmPeriod;
        this.emmPeriod = emmPeriod;
        this.macdValueAttribute = new RealCotationAttribute(MACD_PREFIX_NAME + mmPeriod + "_" + emmPeriod);
        this.mmBuilder = new SimpleMobileMeanBuilder(mmPeriod);
        this.emmBuilder = new ExponentialMobileMeanBuilder(emmPeriod);
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

        BuiltCotation mmBuiltValue = mmBuilder.build(cotation, cotations, builtCotations, alreadyBuiltCotations);
        BuiltCotation emmBuiltValue = emmBuilder.build(cotation, cotations, builtCotations, alreadyBuiltCotations);

        CotationValue<Double> mmValue = mmBuiltValue.getCotationValueByAttribute(mmBuilder.getAttribute()).get();
        CotationValue<Double> emmValue = emmBuiltValue.getCotationValueByAttribute(emmBuilder.getAttribute()).get();

        if (emmValue.getValue().isPresent()) {
            macdCotationValue = macdCotationValue.withValue(mmValue.forceGetValue() - emmValue.forceGetValue());
        }

        return new BuiltCotation(cotation).withAdditionalValues(macdCotationValue);
    }
}
