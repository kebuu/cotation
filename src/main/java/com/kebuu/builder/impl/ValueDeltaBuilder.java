package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.ValueDeltaMode;
import com.kebuu.utils.FunctionUtils;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Function;

public class ValueDeltaBuilder extends AbstractSingleAttributeBuilder<Double> {

    private static final String PREFIX_NAME = "delta_";
    private static final int DEFAULT_PERIOD = -1;

    @Getter private final int period;
    @Getter private final RealCotationAttribute attribute;
    @Getter private final Function<CotationBuilderInfo, Optional<Double>> baseAttributeTransformer;

    private ValueDeltaMode mode = ValueDeltaMode.PERCENT;

    public ValueDeltaBuilder(CotationAttribute<Double> baseAttribute, int period, ValueDeltaMode mode) {
        Preconditions.checkArgument(period < 0, "Period should be lower than 0");

        this.period = period;
        this.baseAttributeTransformer = FunctionUtils.transformerFromAttribute(baseAttribute);
        this.attribute = new RealCotationAttribute(PREFIX_NAME + baseAttribute.getName() + "_" + period);
        this.mode = mode;
    }

    public ValueDeltaBuilder(CotationAttribute<Double> baseAttribute) {
        this(baseAttribute, DEFAULT_PERIOD, ValueDeltaMode.PERCENT);
    }

    @Override
    public CotationAttribute<Double> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<Double> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> deltaValue = new SimpleCotationValue<>(attribute);

        Optional<Cotation> cotationAtPeriod = cotationBuilderInfo.getCotations().getByIndex(cotationBuilderInfo.position() + period);

        if (cotationAtPeriod.isPresent()) {
            Optional<Double> currentValue = baseAttributeTransformer.apply(cotationBuilderInfo);
            Optional<Double> previousValue = baseAttributeTransformer.apply(cotationBuilderInfo.withCotation(cotationAtPeriod.get()));

            if(currentValue.isPresent() && previousValue.isPresent()) {
                deltaValue = deltaValue.withValue(mode.getDelta(previousValue.get(), currentValue.get()));
            }
        }

        return deltaValue;
    }
}
