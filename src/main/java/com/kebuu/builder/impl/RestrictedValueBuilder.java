package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;

import java.util.Set;

public class RestrictedValueBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    private final AbstractSingleAttributeBuilder<T> delegate;
    private final Set<T> restrictedValues;

    public RestrictedValueBuilder(AbstractSingleAttributeBuilder<T> delegate, Iterable<T> restrictedValues) {
        this.delegate = delegate;
        this.restrictedValues = ImmutableSet.copyOf(restrictedValues);
    }

    protected void validateCotationValue(CotationValue<T> value) {
        if(value.getValue().isPresent()) {
            T unwrappedValue = value.getValue().get();

            Preconditions.checkArgument(restrictedValues.contains(unwrappedValue), "Current value '%s' is not in listed possible values %s", unwrappedValue, restrictedValues);
        }
    }

    @Override
    public CotationAttribute<T> attribute() {
        return delegate.attribute();
    }

    @Override
    public CotationValue<T> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        CotationValue<T> cotationValue = delegate.calculateSingleValue(cotationBuilderInfo);
        validateCotationValue(cotationValue);
        return cotationValue;
    }
}
