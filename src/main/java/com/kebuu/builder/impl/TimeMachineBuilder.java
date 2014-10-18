package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.utils.FunctionUtils;

import java.util.Optional;
import java.util.function.Function;

/**
 * Not Finished Yet
 */
public class TimeMachineBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    private final int timeStep;
    private final Function<CotationBuilderInfo, Optional<T>> transfomer;
    //private final CotationAttribute<T> attribute;

    public TimeMachineBuilder(int timeStep, CotationAttribute<T> baseAttribute) {
        this(timeStep, FunctionUtils.transformerFromAttribute(baseAttribute));
    }

    public TimeMachineBuilder(int timeStep, Function<CotationBuilderInfo, Optional<T>> transfomer) {
        Preconditions.checkArgument(timeStep >= 0, "You cannot choose a negative time step");

        this.timeStep = timeStep;
        this.transfomer = transfomer;
    }

    @Override
    public CotationAttribute<T> attribute() {
        return null;
    }

    @Override
    public CotationValue<T> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        return null;
    }
}
