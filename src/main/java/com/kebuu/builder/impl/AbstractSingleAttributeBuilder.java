package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.value.CotationValue;

public abstract class AbstractSingleAttributeBuilder<T> extends AbstractBuilder {

    public abstract CotationAttribute<T> getSingleAttribute();

    public abstract CotationValue calculateSingleValue(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations);

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(getSingleAttribute());
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        return new BuiltCotation(cotation).withAdditionalValues(calculateSingleValue(cotation, cotations, builtCotations, alreadyBuiltCotations));
    }

}
