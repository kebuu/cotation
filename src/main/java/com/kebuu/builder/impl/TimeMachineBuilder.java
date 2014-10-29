package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.decorator.RenamedAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.utils.FunctionUtils;

import java.util.Optional;
import java.util.function.Function;

public class TimeMachineBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    private final int timeStep;
    private final Function<CotationBuilderInfo, Optional<T>> transfomer;
    private final CotationAttribute<T> attribute;
    private final CotationAttribute<T> baseAttribute;

    public TimeMachineBuilder(int timeStep, CotationAttribute<T> baseAttribute) {
        Preconditions.checkArgument(timeStep > 0, "Time step should be greater than 0 (but note it represents a step back in past)");

        this.timeStep = timeStep;
        this.transfomer = FunctionUtils.transformerFromAttribute(baseAttribute);
        this.attribute = new RenamedAttribute<>(baseAttribute.getName() + "_timed_" + timeStep, baseAttribute);
        this.baseAttribute = baseAttribute;
    }

    @Override
    public CotationAttribute<T> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<T> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue value = new SimpleCotationValue(attribute);

        Optional<? extends CotationValue<T>> cotationValue = cotationBuilderInfo.getAlreadyBuiltCotations().getCotationValue(cotationBuilderInfo.position() - timeStep, baseAttribute);

        if (cotationValue.isPresent()) {
            value = value.withValue(cotationValue.get());
        }

        return value;
    }
}
