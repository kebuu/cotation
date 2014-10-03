package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.value.CotationValue;

public abstract class AbstractSingleAttributeBuilder<T> extends AbstractBuilder {

    public abstract CotationAttribute<T> getSingleAttribute();

    public abstract CotationValue calculateSingleValue(CotationBuilderInfo cotationBuilderInfo);

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(getSingleAttribute());
    }

    @Override
    public BuiltCotation build(CotationBuilderInfo cotationBuilderInfo) {
        return new BuiltCotation(cotationBuilderInfo.getCotation()).withAdditionalValues(calculateSingleValue(cotationBuilderInfo));
    }

}
