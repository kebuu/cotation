package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
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

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        BuiltCotation builtCotation = delegate.build(cotation, cotations, builtCotations, alreadyBuiltCotations);
        validateListedAttributeValue(builtCotation.getCotationValueByAttribute(delegate.getSingleAttribute()).get());
        return builtCotation;
    }

    protected void validateListedAttributeValue(CotationValue<T> value) {
        if(value.getValue().isPresent()) {
            T unwrappedValue = value.getValue().get();

            Preconditions.checkArgument(restrictedValues.contains(unwrappedValue), "Current value '%s' is not in listed possible values %s", unwrappedValue, restrictedValues);
        }
    }

    @Override
    public CotationAttribute<T> getSingleAttribute() {
        return delegate.getSingleAttribute();
    }
}
