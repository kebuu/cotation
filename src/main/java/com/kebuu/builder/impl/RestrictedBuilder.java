package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.ListedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;

import java.util.Set;

public class RestrictedBuilder<T> extends AbstractBuilder {

    private final AbstractBuilder delegate;
    private final ListedNominalCotationAttribute<T> listedAttribute;

    public RestrictedBuilder(AbstractBuilder delegate, ListedNominalCotationAttribute<T> listedAttribute) {
        this.delegate = delegate;
        this.listedAttribute = listedAttribute;
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        BuiltCotation builtCotation = delegate.build(cotation, cotations, builtCotations, alreadyBuiltCotations);
        validateListedAttributeValue(builtCotation.getValueByAttribute(listedAttribute).get());
        return builtCotation;
    }

    protected void validateListedAttributeValue(CotationValue<T> value) {
        if(value.getValue().isPresent()) {
            T unwrappedValue = value.getValue().get();
            Set<T> possibleValues = listedAttribute.getPossibleValues();

            Preconditions.checkArgument(possibleValues.contains(unwrappedValue), "Current value '%s' is not in listed possible values %s", unwrappedValue, possibleValues);
        }
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(listedAttribute);
    }
}
